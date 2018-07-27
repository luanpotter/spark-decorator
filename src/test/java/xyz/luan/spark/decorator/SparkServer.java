package xyz.luan.spark.decorator;

import org.junit.rules.ExternalResource;
import spark.Service;
import spark.Spark;
import spark.servlet.SparkApplication;
import xyz.luan.facade.HttpFacade;

import java.net.MalformedURLException;
import java.util.function.Supplier;

public class SparkServer<T extends SparkApplication> extends ExternalResource {

    private Class<T> sparkApplicationClass;
    private T sparkApplication;
    private String domain;

    public SparkServer(Class<T> sparkApplicationClass) {
        this(sparkApplicationClass, Service.SPARK_DEFAULT_PORT);
    }

    public SparkServer(Class<T> sparkApplicationClass, int port) {
        this.sparkApplicationClass = sparkApplicationClass;
        this.domain = "http://localhost:" + port;

        Spark.port(port);
    }

    @Override
    protected void before() throws InstantiationException, IllegalAccessException {
        this.sparkApplication = this.sparkApplicationClass.newInstance();
        this.sparkApplication.init();
        Spark.awaitInitialization();
    }

    @Override
    protected void after() {
        this.sparkApplication.destroy();
        this.sparkApplication = null;
        Spark.stop();
        waitFor(() -> !isSparkInitialized());
    }

    public boolean isSparkInitialized() {
        try {
            Spark.port();
            return true;
        } catch (IllegalStateException ignored) {
            return false;
        }
    }

    public void waitFor(Supplier<Boolean> condition) {
        while (!condition.get()) {
            sleep(100);
        }
    }

    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpFacade get(String path) {
        return request(path).method("GET");
    }

    public HttpFacade post(String path, String body) {
        return request(path).method("POST").body(body);
    }

    public HttpFacade put(String path, String body) {
        return request(path).method("PUT").body(body);
    }

    public HttpFacade patch(String path, String body) {
        return request(path).method("PATCH").body(body);
    }

    public HttpFacade delete(String path) {
        return request(path).method("DELETE");
    }

    public HttpFacade options(String path) {
        return request(path).method("OPTIONS");
    }

    public HttpFacade head(String path) {
        return request(path).method("HEAD");
    }

    public HttpFacade request(String path) {
        try {
            return new HttpFacade(domain + path);
        } catch (MalformedURLException e) {
            throw new RuntimeException("invalid url", e);
        }
    }
}
