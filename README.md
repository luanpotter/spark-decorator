# spark-decorator

Simple and pretty decorators for your routes.

Want some routes to have auth, but some don't? Or base it off a parameter?

Spark's `before` and `after` work great if the distinction your are making is by path. But what if it's not? Maybe some users can GET but cannot POST on the same route.

This libs helps you to decorate your routes in order to add custom behaviour to a selection of them, without duplication and hassles.

You can hook `before` and `after` filters and do anything you want! 

## Install

You must have spark...

maven...
gradle...

## Usage

You can do a plethora of things using this, basically anything you would do in a before or after filter.

For example, let's add a secret header to some of our routes; create a `RouteDecorator` instance with a singleton instance (this is to follow Spark's convention and is optional).

```java
    public class AfterDecorator extends RouteDecorator {
        public static final AfterDecorator secretPanda = new AfterDecorator();

        private AfterDecorator() {
        }

        @Override
        protected Route after() {
            return (req, resp) -> {
                resp.header("secret", "panda");
                return null;
            };
        }
    }
```

Now, in your controller/application, instead of
```java
    get("foo", (req, resp) -> "bar");
```

You do:

```java
    withLogger.get("/foo", (req, resp) -> "bar");
```

And your request has been enriched. You'd of course statically import `get` from `Spark`, and now also statically import `withLogger` from your class.

Pretty neat, hum? Let's see a more complex example now!

## Auth Example

Assume each route might or might not require auth, and the ones that require auth require a specific role to access. You could create a decorator like so:

public class SparkAuth extends RouteDecorator {

```java
public class AuthDecorator extends RouteDecorator {

    public static AuthDecorator requiresRole(String role) {
        return new AuthDecorator(role);
    }
    
    private String requiredRole;

    private AuthDecorator(String requiredRole) {
        this.requiredRole = requiredRole;
    }

    @Override
    protected Route before() {
        return (req, resp) -> {
            String auth = req.header("Auth");
            if (auth == null) {
                if (requiredRole == null) {
                    return null; // ok
                }
                throw halt(401, "You must be authenticated to access.");
            }
            String role = getProfile(auth).getRole();
            if (!role.equals(requiredRole)) {
                throw halt(403, "You must be role " + requiredRole + " to access.");
            }
            return null;
        };
    }
}
```

Then, you can use it like so:

```java
    get("/foo", (req, resp) -> "bar");
    requiresRole("user").post("/foo", (req, resp) -> "ok");
    requiresRole("admin").delete("/foo", (req, resp) -> "deleted");
```

## Contributing

Want to contribute? Please do so! Leave a star, open PR and issues! To submit a PR, it's really simple:

* Fork it!
* Create a feature branch.
* Make your modifications
* Open a Pull Request :)