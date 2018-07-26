package xyz.luan.spark.decorator;

import spark.Route;

public class RouteDecorator extends BaseDecorator {

    protected Route before() {
        return (req, resp) -> null;
    }

    protected Route after() {
        return (req, resp) -> null;
    }

    @Override
    protected Route enrich(Route route) {
        return (req, resp) -> {
            before().handle(req, resp);
            Object handle = route.handle(req, resp);
            after().handle(req, resp);
            return handle;
        };
    }
}
