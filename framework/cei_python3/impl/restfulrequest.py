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
        self.__queryString = list()

    def build_query_string(self):
        if len(self.__queryString) == 0:
            return ""
        tmp = dict()
        for item in self.__queryString:
            tmp[item[0]]= item[1]
        encoded_param = urllib.parse.urlencode(tmp)
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
        if value is not None:
            self.__queryString.append([key, value])

    def get_query_string(self):
        return self.__queryString

    def add_header(self, key: str, value):
        if value is None:
            # TODO
            pass
        self.__header[key] = str(value)