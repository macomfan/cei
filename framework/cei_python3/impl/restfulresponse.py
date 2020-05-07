from impl.jsonwrapper import JsonWrapper


class RestfulResponse:
    def __init__(self, response):
        self.__response = response

    def get_string(self):
        return self.__response.text
