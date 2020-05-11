package cei.impl;

import okhttp3.Response;

public class RestfulResponse {
    private final Response response;
    
    public RestfulResponse(Response response) {
        this.response = response;
    }
    
    public String getString() {
        String res = null;
        try {
            res = response.body().string();
            System.out.println(res);
        } catch (Exception e) {
            throw new CEIException("Cannot get string");
        }
        return res;
    }
}
