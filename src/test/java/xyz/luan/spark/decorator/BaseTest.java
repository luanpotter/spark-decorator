package xyz.luan.spark.decorator;

import org.junit.After;
import org.junit.Before;
import spark.servlet.SparkApplication;
import xyz.luan.spark.decorator.decorators.LoggerDecorator;
import xyz.luan.spark.decorator.decorators.LoggerTemplateViewDecorator;

public abstract class BaseTest {

    protected SparkServer<? extends SparkApplication> server = new SparkServer<>(application(), 4567);

    protected abstract Class<? extends SparkApplication> application();

    @Before
    public void before() throws IllegalAccessException, InstantiationException {
        server.before();
    }

    @After
    public void after() {
        server.after();
        LoggerDecorator.logs.clear();
        LoggerTemplateViewDecorator.logs.clear();
    }
}
