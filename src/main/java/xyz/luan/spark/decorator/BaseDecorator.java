package xyz.luan.spark.decorator;

import spark.*;

public class BaseDecorator {

    protected Route enrich(Route route) {
        return route;
    }

    protected TemplateViewRoute enrich(TemplateViewRoute route) {
        return route;
    }

    public void get(String path, Route route) {
        Spark.get(path, enrich(route));
    }

    public void post(String path, Route route) {
        Spark.post(path, enrich(route));
    }

    public void put(String path, Route route) {
        Spark.put(path, enrich(route));
    }

    public void patch(String path, Route route) {
        Spark.patch(path, enrich(route));
    }

    public void delete(String path, Route route) {
        Spark.delete(path, enrich(route));
    }

    public void head(String path, Route route) {
        Spark.head(path, enrich(route));
    }

    public void trace(String path, Route route) {
        Spark.trace(path, enrich(route));
    }

    public void connect(String path, Route route) {
        Spark.connect(path, enrich(route));
    }

    public void options(String path, Route route) {
        Spark.options(path, enrich(route));
    }

    public void get(String path, String acceptType, Route route) {
        Spark.get(path, acceptType, enrich(route));
    }

    public void post(String path, String acceptType, Route route) {
        Spark.post(path, acceptType, enrich(route));
    }

    public void put(String path, String acceptType, Route route) {
        Spark.put(path, acceptType, enrich(route));
    }

    public void patch(String path, String acceptType, Route route) {
        Spark.patch(path, acceptType, enrich(route));
    }

    public void delete(String path, String acceptType, Route route) {
        Spark.delete(path, acceptType, enrich(route));
    }

    public void head(String path, String acceptType, Route route) {
        Spark.head(path, acceptType, enrich(route));
    }

    public void trace(String path, String acceptType, Route route) {
        Spark.trace(path, acceptType, enrich(route));
    }

    public void connect(String path, String acceptType, Route route) {
        Spark.connect(path, acceptType, enrich(route));
    }

    public void options(String path, String acceptType, Route route) {
        Spark.options(path, acceptType, enrich(route));
    }

    public void get(String path, TemplateViewRoute route, TemplateEngine engine) {
        Spark.get(path, enrich(route), engine);
    }

    public void get(String path, String acceptType, TemplateViewRoute route, TemplateEngine engine) {
        Spark.get(path, acceptType, enrich(route), engine);
    }

    public void post(String path, TemplateViewRoute route, TemplateEngine engine) {
        Spark.post(path, enrich(route), engine);
    }

    public void post(String path, String acceptType, TemplateViewRoute route, TemplateEngine engine) {
        Spark.post(path, acceptType, enrich(route), engine);
    }

    public void put(String path, TemplateViewRoute route, TemplateEngine engine) {
        Spark.put(path, enrich(route), engine);
    }

    public void put(String path, String acceptType, TemplateViewRoute route, TemplateEngine engine) {
        Spark.put(path, acceptType, enrich(route), engine);
    }

    public void delete(String path, TemplateViewRoute route, TemplateEngine engine) {
        Spark.delete(path, enrich(route), engine);
    }

    public void delete(String path, String acceptType, TemplateViewRoute route, TemplateEngine engine) {
        Spark.delete(path, acceptType, enrich(route), engine);
    }

    public void patch(String path, TemplateViewRoute route, TemplateEngine engine) {
        Spark.patch(path, enrich(route), engine);
    }

    public void patch(String path, String acceptType, TemplateViewRoute route, TemplateEngine engine) {
        Spark.patch(path, acceptType, enrich(route), engine);
    }


    public void head(String path, TemplateViewRoute route, TemplateEngine engine) {
        Spark.head(path, enrich(route), engine);
    }

    public void head(String path, String acceptType, TemplateViewRoute route, TemplateEngine engine) {
        Spark.head(path, acceptType, enrich(route), engine);
    }

    public void trace(String path, TemplateViewRoute route, TemplateEngine engine) {
        Spark.trace(path, enrich(route), engine);
    }

    public void trace(String path, String acceptType, TemplateViewRoute route, TemplateEngine engine) {
        Spark.trace(path, acceptType, enrich(route), engine);
    }

    public void connect(String path, TemplateViewRoute route, TemplateEngine engine) {
        Spark.connect(path, enrich(route), engine);
    }

    public void connect(String path, String acceptType, TemplateViewRoute route, TemplateEngine engine) {
        Spark.connect(path, acceptType, enrich(route), engine);
    }

    public void options(String path, TemplateViewRoute route, TemplateEngine engine) {
        Spark.options(path, enrich(route), engine);
    }

    public void options(String path, String acceptType, TemplateViewRoute route, TemplateEngine engine) {
        Spark.options(path, acceptType, enrich(route), engine);
    }

    public void get(String path, Route route, ResponseTransformer transformer) {
        Spark.get(path, enrich(route), transformer);
    }

    public void get(String path, String acceptType, Route route, ResponseTransformer transformer) {
        Spark.get(path, acceptType, enrich(route), transformer);
    }

    public void post(String path, Route route, ResponseTransformer transformer) {
        Spark.post(path, enrich(route), transformer);
    }

    public void post(String path, String acceptType, Route route, ResponseTransformer transformer) {
        Spark.post(path, acceptType, enrich(route), transformer);
    }

    public void put(String path, Route route, ResponseTransformer transformer) {
        Spark.put(path, enrich(route), transformer);
    }

    public void put(String path, String acceptType, Route route, ResponseTransformer transformer) {
        Spark.put(path, acceptType, enrich(route), transformer);
    }

    public void delete(String path, Route route, ResponseTransformer transformer) {
        Spark.delete(path, enrich(route), transformer);
    }

    public void delete(String path, String acceptType, Route route, ResponseTransformer transformer) {
        Spark.delete(path, acceptType, enrich(route), transformer);
    }

    public void head(String path, Route route, ResponseTransformer transformer) {
        Spark.head(path, enrich(route), transformer);
    }

    public void head(String path, String acceptType, Route route, ResponseTransformer transformer) {
        Spark.head(path, acceptType, enrich(route), transformer);
    }

    public void connect(String path, Route route, ResponseTransformer transformer) {
        Spark.connect(path, enrich(route), transformer);
    }

    public void connect(String path, String acceptType, Route route, ResponseTransformer transformer) {
        Spark.connect(path, acceptType, enrich(route), transformer);
    }

    public void trace(String path, Route route, ResponseTransformer transformer) {
        Spark.trace(path, enrich(route), transformer);
    }

    public void trace(String path, String acceptType, Route route, ResponseTransformer transformer) {
        Spark.trace(path, acceptType, enrich(route), transformer);
    }

    public void options(String path, Route route, ResponseTransformer transformer) {
        Spark.options(path, enrich(route), transformer);
    }


    public void options(String path, String acceptType, Route route, ResponseTransformer transformer) {
        Spark.options(path, acceptType, enrich(route), transformer);
    }

    public void patch(String path, Route route, ResponseTransformer transformer) {
        Spark.patch(path, enrich(route), transformer);
    }

    public void patch(String path, String acceptType, Route route, ResponseTransformer transformer) {
        Spark.patch(path, acceptType, enrich(route), transformer);
    }
}
