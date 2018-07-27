package xyz.luan.spark.decorator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spark.servlet.SparkApplication;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static spark.Spark.get;
import static xyz.luan.spark.decorator.AuthDecorator.requiresRole;

public class AuthDecoratorTest {

    private static SparkServer<ApplicationTest> server = new SparkServer<>(ApplicationTest.class, 4567);

    @Before
    public void before() throws IllegalAccessException, InstantiationException {
        server.before();
    }

    @After
    public void after() {
        server.after();
    }

    static class ApplicationTest implements SparkApplication {
        private String value = "bar";

        @Override
        public void init() {
            get("/foo", (req, resp) -> value);
            requiresRole("user").post("/foo", (req, resp) -> value = req.body());
            requiresRole("admin").delete("/foo", (req, resp) -> {
                value = "null";
                return "deleted";
            });
        }
    }

    @Test
    public void testNotLogged() throws IOException {
        assertThat(server.get("/foo").req().content()).isEqualTo("bar");
        assertThat(server.post("/foo", "new").req().status()).isEqualTo(401);
        assertThat(server.delete("/foo").req().status()).isEqualTo(401);
    }

    @Test
    public void testUser() throws IOException {
        assertThat(server.get("/foo").header("role", "user").req().content()).isEqualTo("bar");
        assertThat(server.post("/foo", "new").header("role", "user").req().content()).isEqualTo("new");
        assertThat(server.get("/foo").header("role", "user").req().content()).isEqualTo("new");
        assertThat(server.delete("/foo").header("role", "user").req().status()).isEqualTo(403);
    }

    @Test
    public void testAdmin() throws IOException {
        assertThat(server.get("/foo").header("role", "admin").req().content()).isEqualTo("bar");
        assertThat(server.post("/foo", "new").header("role", "user").req().content()).isEqualTo("new");
        assertThat(server.get("/foo").header("role", "admin").req().content()).isEqualTo("new");
        assertThat(server.delete("/foo").header("role", "admin").req().status()).isEqualTo(200);
        assertThat(server.get("/foo").header("role", "admin").req().content()).isEqualTo("null");
    }
}
