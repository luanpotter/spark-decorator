package xyz.luan.spark.decorator.decorators;

import spark.Route;
import xyz.luan.spark.decorator.RouteDecorator;

import static spark.Spark.halt;

public class AuthDecorator extends RouteDecorator {

    private String requiredRole;

    private AuthDecorator(String requiredRole) {
        this.requiredRole = requiredRole;
    }

    public static AuthDecorator requiresRole(String role) {
        return new AuthDecorator(role);
    }

    @Override
    protected Route before() {
        return (req, resp) -> {
            String role = req.headers("role");
            if (role == null) {
                if (requiredRole == null) {
                    return null; // ok
                }
                throw halt(401, "You must be authenticated to access.");
            }
            if (!role.equals(requiredRole)) {
                throw halt(403, "You must be role " + requiredRole + " to access.");
            }
            return null;
        };
    }
}
