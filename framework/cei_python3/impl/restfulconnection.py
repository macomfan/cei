import requests
from impl.restfulrequest import RestfulRequest
from impl.restfulresponse import RestfulResponse


class RestfulConnection:

    @staticmethod
    def query(request: RestfulRequest):
        if request.get_method() == RestfulRequest.Method.GET:
            # response = requests.get(request.get_url() + request.get_target() + request.build_query_string(),
            #                         headers=request.get_header())
            response = requests.get(request.get_url() + request.get_target(),
                                    headers=request.get_header())
            return RestfulResponse(response)
        elif request.get_method() == RestfulRequest.Method.POST:
            pass
        else:
            pass
        pass
