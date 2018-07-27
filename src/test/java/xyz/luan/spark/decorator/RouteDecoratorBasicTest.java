package xyz.luan.spark.decorator;

import org.junit.Test;
import spark.Route;
import spark.servlet.SparkApplication;
import xyz.luan.facade.Response;
import xyz.luan.spark.decorator.decorators.LoggerDecorator;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static spark.Spark.get;
import static xyz.luan.spark.decorator.RouteDecoratorBasicTest.AfterDecorator.secretPanda;
import static xyz.luan.spark.decorator.decorators.LoggerDecorator.withLogger;


public class RouteDecoratorBasicTest extends BaseTest {

    @Test
    public void testNoDecorator() throws IOException {
        String result = server.get("/foo").req().content();
        assertThat(result).isEqualTo("bar");
    }

    @Test
    public void testBeforeGet() throws IOException {
        String result = server.get("/hello").req().content();
        assertThat(result).isEqualTo("world");
        assertThat(LoggerDecorator.logs).containsExactly("logging: /hello");
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
        assertThat(LoggerDecorator.logs).containsExactly("logging: /star", "logging: /star");
    }

    @Override
    protected Class<ApplicationTest> application() {
        return ApplicationTest.class;
    }

    public static class AfterDecorator extends RouteDecorator {
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

    public static class ApplicationTest implements SparkApplication {
        @Override
        public void init() {
            get("foo", (req, resp) -> "bar");
            withLogger.get("/hello", (req, resp) -> "world");
            secretPanda.post("/say/:what", (req, resp) -> req.params("what"));
            withLogger.put("/star", (req, resp) -> req.body().equals("trek") ? "wars" : "trek");
        }
    }
}
