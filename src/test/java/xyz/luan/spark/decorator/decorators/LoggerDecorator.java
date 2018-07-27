package xyz.luan.spark.decorator.decorators;

import spark.Route;
import xyz.luan.spark.decorator.RouteDecorator;

import java.util.ArrayList;
import java.util.List;

public class LoggerDecorator extends RouteDecorator {

    public static List<String> logs = new ArrayList<>();

    public static final LoggerDecorator withLogger = new LoggerDecorator();

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