import json
from exception.ceiexception import CEIException


class JsonWrapper:

    @staticmethod
    def parse_from_string(text: str):
        return JsonWrapper(json.loads(text))

    def __init__(self, json_object):
        if isinstance(json_object, list):
            self.__json_array = json_object
        else:
            self.__json_object = json_object

    def __get_index_key(self, key: str):
        pass

    def contains(self, name):
        if name in self.__json_object:
            return True
        else:
            return False

    def __check_mandatory_field(self, key):
        if not self.contains(key):
            raise CEIException()

    def get_string(self, key):
        self.__check_mandatory_field(key)
        return str(self.__json_object[key])

    def get_long(self, key):
        self.__check_mandatory_field(key)
        return int(self.__json_object[key])

    def get_decimal(self, key):
        self.__check_mandatory_field(key)
        return float(self.__json_object[key])

    def get_object(self, key):
        self.__check_mandatory_field(key)
        return JsonWrapper(self.__json_object[key])

    def get_object_array(self, key):
        self.__check_mandatory_field(key)
        json_array = self.__json_object[key]
        if not isinstance(json_array, list):
            raise CEIException(key + " is not an array")
        items = list()
        for item in json_array:
            items.append(JsonWrapper(item))
        return items
