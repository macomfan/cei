package cei.simulator.server.restful.get.url;

import cei.simulator.server.main;

public class Url {
    public static void register() {

        main.routeGet("/restful/get/url/*", request -> {
            System.out.println(request.uri());
            return request.uri().replace("/restful/get/url/","");
        });
    }
}
