package cei.simulator.server.restful.post;

import cei.simulator.server.main;

import java.util.Map;

public class Authentication {
    public static void register() {
        main.routePost("/restful/post/authentication", (request, body)  -> {
            System.out.println(request.uri());
            for (Map.Entry<String, String> entry : request.params().entries()) {

            }
            System.out.println(body.toJsonObject().toString());
            return "";
        });
    }
}
