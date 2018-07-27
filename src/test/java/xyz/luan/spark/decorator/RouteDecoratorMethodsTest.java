package xyz.luan.spark.decorator;

import org.junit.Test;
import spark.servlet.SparkApplication;
import xyz.luan.facade.HttpFacade;
import xyz.luan.facade.Response;
import xyz.luan.spark.decorator.decorators.LoggerDecorator;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static xyz.luan.spark.decorator.decorators.LoggerDecorator.withLogger;

public class RouteDecoratorMethodsTest extends BaseTest {

    @Test
    public void testGet() throws IOException {
        itWorks(server.get("/foo"));
    }

    @Test
    public void testPost() throws IOException {
        itWorks(server.post("/foo", ""));
    }

    @Test
    public void testPut() throws IOException {
        itWorks(server.put("/foo", ""));
    }

    @Test
    public void testDelete() throws IOException {
        itWorks(server.delete("/foo"));
    }

    @Test
    public void testHead() throws IOException {
        HttpFacade req = server.head("/foo");
        assertThat(req.req().status()).isEqualTo(200);
        assertThat(LoggerDecorator.logs).containsExactly("logging: /foo");
    }

    @Test
    public void testOptions() throws IOException {
        itWorks(server.options("/foo"));
    }

    // TODO cannot test TRACE, CONNECT and PATCH (Java doesn't allow?)

    private void itWorks(HttpFacade req) throws IOException {
        Response resp = req.req();
        assertThat(resp.status()).isEqualTo(200);
        assertThat(resp.content()).isEqualTo("bar");
        assertThat(LoggerDecorator.logs).containsExactly("logging: /foo");
    }

    @Override
    protected Class<? extends SparkApplication> application() {
        return ApplicationTest.class;
    }

    static class ApplicationTest implements SparkApplication {
        @Override
        public void init() {
            withLogger.get("/foo", (req, resp) -> "bar");
            withLogger.post("/foo", (req, resp) -> "bar");
            withLogger.put("/foo", (req, resp) -> "bar");
            withLogger.patch("/foo", (req, resp) -> "bar");
            withLogger.delete("/foo", (req, resp) -> "bar");
            withLogger.head("/foo", (req, resp) -> "bar");
            withLogger.options("/foo", (req, resp) -> "bar");
            withLogger.trace("/foo", (req, resp) -> "bar");
            withLogger.connect("/foo", (req, resp) -> "bar");
        }
    }


}
