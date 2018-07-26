package xyz.luan.spark.decorator;

import spark.ModelAndView;
import spark.TemplateViewRoute;

public class TemplateViewRouteDecorator extends BaseDecorator {

    protected TemplateViewRoute before() {
        return (req, resp) -> null;
    }

    protected TemplateViewRoute after() {
        return (req, resp) -> null;
    }

    @Override
    protected TemplateViewRoute enrich(TemplateViewRoute route) {
        return (req, resp) -> {
            before().handle(req, resp);
            ModelAndView handle = route.handle(req, resp);
            after().handle(req, resp);
            return handle;
        };
    }
}
