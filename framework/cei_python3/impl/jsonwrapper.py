import json
from exception.ceiexception import CEIException

class JsonWrapper:

    @staticmethod
    def parse_from_string(text: str):
        return JsonWrapper(json.loads(text))

    def __init__(self, json_object):
        self.__json_object = json_object

    def contains_key(self, name):
        if name in self.__json_object:
            return True
        else:
            return False

    def __check_mandatory_field(self, key):
        if not self.contains_key(key):
            raise CEIException()

    def get_string(self, key: str):
        self.__check_mandatory_field(key)
        return str(self.__json_object[key])

    def get_long(self, key: str):
        self.__check_mandatory_field(key)
        return int(self.__json_object[key])
