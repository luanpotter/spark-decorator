package xyz.luan.spark.decorator;

import org.junit.Test;
import spark.ModelAndView;
import spark.ResponseTransformer;
import spark.TemplateEngine;
import spark.servlet.SparkApplication;
import xyz.luan.facade.HttpFacade;
import xyz.luan.facade.Response;
import xyz.luan.spark.decorator.decorators.LoggerTemplateViewDecorator;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static xyz.luan.spark.decorator.decorators.LoggerTemplateViewDecorator.templateWithLogger;

public class TemplateViewDecoratorMethodsTest extends BaseTest {

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
        assertThat(LoggerTemplateViewDecorator.logs).containsExactly("logging: /foo");
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
        assertThat(LoggerTemplateViewDecorator.logs).containsExactly("logging: /foo");
    }

    @Override
    protected Class<? extends SparkApplication> application() {
        return ApplicationTest.class;
    }

    static class ApplicationTest implements SparkApplication {
        @Override
        public void init() {
            String object = "bar";
            ModelAndView mnv = new ModelAndView(object, "foo");
            TemplateEngine engine = new TemplateEngine() {
                @Override
                public String render(ModelAndView modelAndView) {
                    return mnv.getModel().toString();
                }
            };
            templateWithLogger.get("/foo", (req, resp) -> mnv, engine);
            templateWithLogger.post("/foo", (req, resp) -> mnv, engine);
            templateWithLogger.put("/foo", (req, resp) -> mnv, engine);
            templateWithLogger.patch("/foo", (req, resp) -> mnv, engine);
            templateWithLogger.delete("/foo", (req, resp) -> mnv, engine);
            templateWithLogger.head("/foo", (req, resp) -> mnv, engine);
            templateWithLogger.options("/foo", (req, resp) -> mnv, engine);
            templateWithLogger.trace("/foo", (req, resp) -> mnv, engine);
            templateWithLogger.connect("/foo", (req, resp) -> mnv, engine);
        }
    }


}
