package xyz.luan.spark.decorator.decorators;

import spark.TemplateViewRoute;
import xyz.luan.spark.decorator.TemplateViewRouteDecorator;

import java.util.ArrayList;
import java.util.List;

public class LoggerTemplateViewDecorator extends TemplateViewRouteDecorator {

    public static List<String> logs = new ArrayList<>();

    public static final LoggerTemplateViewDecorator templateWithLogger = new LoggerTemplateViewDecorator();

    private LoggerTemplateViewDecorator() {
    }

    @Override
    protected TemplateViewRoute before() {
        return (req, resp) -> {
            logs.add("logging: " + req.pathInfo());
            return null;
        };
    }
}