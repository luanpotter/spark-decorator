package xyz.luan.spark.decorator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spark.Route;
import spark.servlet.SparkApplication;
import xyz.luan.facade.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static spark.Spark.get;
import static xyz.luan.spark.decorator.RouteDecoratorTest.AfterDecorator.secretPanda;
import static xyz.luan.spark.decorator.RouteDecoratorTest.LoggerDecorator.withLogger;

public class RouteDecoratorTest {

    private static SparkServer<ApplicationTest> server = new SparkServer<>(ApplicationTest.class, 4567);
    private static List<String> logs = new ArrayList<>();

    @Before
    public void before() throws IllegalAccessException, InstantiationException {
        server.before();
    }

    @After
    public void after() {
        server.after();
        logs.clear();
    }

    @Test
    public void testNoDecorator() throws IOException {
        String result = server.get("/foo").req().content();
        assertThat(result).isEqualTo("bar");
    }

    @Test
    public void testBeforeGet() throws IOException {
        String result = server.get("/hello").req().content();
        assertThat(result).isEqualTo("world");
        assertThat(logs).containsExactly("logging: /hello");
    }

    @Test
    public void testAfterPost() throws IOException {
        Response result = server.post("/say/cat", "").req();
        assertThat(result.content()).isEqualTo("cat");
        assertThat(result.header("secret")).isEqualTo("panda");
    }

    @Test
    public void testBeforePut() throws IOException {
        String result1 = server.put("/star", "trek").req().content();
        String result2 = server.put("/star", "wars").req().content();
        assertThat(result1).isEqualTo("wars");
        assertThat(result2).isEqualTo("trek");
        assertThat(logs).containsExactly("logging: /star", "logging: /star");
    }

    static class ApplicationTest implements SparkApplication {
        @Override
        public void init() {
            get("foo", (req, resp) -> "bar");
            withLogger.get("/hello", (req, resp) -> "world");
            secretPanda.post("/say/:what", (req, resp) -> req.params("what"));
            withLogger.put("/star", (req, resp) -> req.body().equals("trek") ? "wars" : "trek");
        }
    }

    static class LoggerDecorator extends RouteDecorator {

        static final LoggerDecorator withLogger = new LoggerDecorator();

        private LoggerDecorator() {
        }

        @Override
        protected Route before() {
            return (req, resp) -> {
                logs.add("logging: " + req.pathInfo());
                return null;
            };
        }
    }

    static class AfterDecorator extends RouteDecorator {
        static final AfterDecorator secretPanda = new AfterDecorator();

        private AfterDecorator() {
        }

        @Override
        protected Route after() {
            return (req, resp) -> {
                resp.header("secret", "panda");
                return null;
            };
        }
    }
}
