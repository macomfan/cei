import urllib.parse
from impl.restfuloptions import RestfulOptions


class RestfulRequest:
    class Method:
        GET = "get"
        POST = "post"

    def __init__(self, option: RestfulOptions):
        self.__method = None
        self.__url = option.url
        self.__target = ""
        self.__post_body = ""
        self.__header = dict()
        self.__queryString = dict()

    def build_query_string(self):
        if len(self.__queryString) == 0:
            return ""
        encoded_param = urllib.parse.urlencode(self.__queryString)
        return "?" + encoded_param

    def get_header(self):
        return self.__header

    def get_url(self):
        return self.__url

    def get_target(self):
        return self.__target

    def get_method(self):
        return self.__method

    def set_url(self, url: str):
        self.__url = url

    def set_target(self, target: str):
        self.__target = target

    def set_method(self, method: Method):
        self.__method = method

    def add_query_string(self, key: str, value):
        if value is None:
            # TODO
            pass
        self.__queryString[key] = str(value)

    def add_header(self, key: str, value):
        if value is None:
            # TODO
            pass
        self.__header.append[key], str(value)
