import json
import re
from exception.ceilog import CEILog


class JsonWrapper(object):

    @staticmethod
    def parse_from_string(text: str):
        return JsonWrapper(json.loads(text))

    @staticmethod
    def __get_index_key(key: str):
        if key == "[]":
            return -1
        m = re.search("^\\[[0-9]*]$", key)
        if m is not None:
            return int(key[m.pos + 1:m.endpos - 1])
        return None

    @staticmethod
    def __cast_to(value, cast):
        if value is None:
            return None
        else:
            try:
                return cast(value)
            except BaseException as e:
                CEILog.show_warning("Cannot convert %s" % e)

    @staticmethod
    def __check_mandatory_field(key, value):
        if value is None:
            CEILog.show_failure("[Json] Get json item field: %s, does not exist" % key)
            return None
        return value

    @staticmethod
    def __check_mandatory_object(key, value):
        if value is None:
            CEILog.show_failure("[Json] The JsonWrapper is null")
        elif (value.__json_object is None) and (value.__json_array is None):
            CEILog.show_failure("[Json] Get json object: %s, does not exist" % key)
        elif (value.__json_object is not None) and (value.__json_array is not None):
            CEILog.show_failure("[Json] The JsonWrapper is invalid" % key)
        return value

    def __init__(self, json_object=None):
        if isinstance(json_object, list):
            self.__json_array = json_object
            self.__json_object = None
        elif isinstance(json_object, dict):
            self.__json_object = json_object
            self.__json_array = None
        else:
            self.__json_object = None
            self.__json_array = None

    def contains(self, key):
        index = self.__get_index_key(key)
        if (index is not None) and (self.__json_array is not None):
            return len(self.__json_array) > index > 0
        elif self.__json_object is not None:
            return key in self.__json_object
        return False

    def __initialize_as_object(self):
        if (self.__json_object is None) and (self.__json_array is None):
            self.__json_object = dict()
        elif self.__json_array is not None:
            CEILog.show_failure("[Json] Cannot create json object")

    def __initialize_as_array(self):
        if (self.__json_object is None) and (self.__json_array is None):
            self.__json_array = list()
        elif self.__json_object is not None:
            CEILog.show_failure("[Json] Cannot create json array")

    def __add_json_value(self, key, obj):
        index = self.__get_index_key(key)
        if index is None:
            self.__initialize_as_object()
            self.__json_object[key] = obj
        elif index == -1:
            self.__initialize_as_array()
            self.__json_array.append(obj)
        else:
            CEILog.show_failure("[Json] Cannot specify thd detail index to insert a item to array")

    def add_json_string(self, key, value: str):
        self.__add_json_value(key, value)

    def add_json_number(self, key, value):
        self.__add_json_value(key, value)

    def add_json_boolean(self, key, value: bool):
        self.__add_json_value(key, value)

    def add_json_object(self, key, value: "JsonWrapper"):
        if value.__json_object is not None:
            self.__add_json_value(key, value.__json_object)
        elif value.__json_array is not None:
            self.__add_json_value(key, value.__json_array)
        else:
            CEILog.show_failure("[Json] Cannot add a null object to json object")

    def __get_by_key(self, key):
        obj = None
        index = self.__get_index_key(key)
        if index is None:
            if self.__json_object is not None and key in self.__json_object:
                obj = self.__json_object[key]
        else:
            if (self.__json_array is not None) and 0 < index < len(self.__json_array):
                obj = self.__json_array[index]
        return obj

    def get_string(self, key):
        value = self.get_string_or_none(key)
        return self.__check_mandatory_field(key, value)

    def get_string_or_none(self, key):
        value = self.__get_by_key(key)
        return self.__cast_to(value, lambda v: str(v))

    def get_int(self, key):
        value = self.get_int_or_none(key)
        return self.__check_mandatory_field(key, value)

    def get_int_or_none(self, key):
        value = self.__get_by_key(key)
        return self.__cast_to(value, lambda v: int(v))

    def get_decimal(self, key):
        value = self.get_decimal_or_none(key)
        return self.__check_mandatory_field(key, value)

    def get_decimal_or_none(self, key):
        value = self.__get_by_key(key)
        return self.__cast_to(value, lambda v: float(v))

    def get_bool(self, key):
        value = self.get_bool_or_none(key)
        return self.__check_mandatory_field(key, value)

    def get_bool_or_none(self, key):
        value = self.__get_by_key(key)
        return self.__cast_to(value, lambda v: bool(v))

    def get_object(self, key):
        value = self.get_object_or_none(key)
        return self.__check_mandatory_object(key, value)

    def get_object_or_none(self, key):
        obj = self.__get_by_key(key)
        if obj is None:
            return JsonWrapper()
        if isinstance(obj, dict) or isinstance(obj, list):
            return JsonWrapper(obj)
        else:
            # TODO
            pass

    def get_array(self, key):
        obj = self.get_array_or_none(key)
        return self.__check_mandatory_object(key, obj)

    def get_array_or_none(self, key):
        obj = self.__get_by_key(key)
        if isinstance(obj, list):
            return JsonWrapper(obj)
        return JsonWrapper()

    def __get_array_by_key(self, key, cast_func):
        obj = self.__get_by_key(key)
        if not isinstance(obj, list):
            return None
        res = list()
        for item in obj:
            res.append(self.__cast_to(item, cast_func))
        return res

    def get_string_array(self, key):
        value = self.get_string_array_or_none(key)
        return self.__check_mandatory_field(key, value)

    def get_string_array_or_none(self, key):
        return self.__get_array_by_key(key, lambda v: str(v))

    def get_int_array(self, key):
        value = self.get_int_array_or_none(key)
        return self.__check_mandatory_field(key, value)

    def get_int_array_or_none(self, key):
        return self.__get_array_by_key(key, lambda v: int(v))

    def get_decimal_array(self, key):
        value = self.get_decimal_array_or_none(key)
        return self.__check_mandatory_field(key, value)

    def get_decimal_array_or_none(self, key):
        return self.__get_array_by_key(key, lambda v: float(v))

    def get_bool_array(self, key):
        value = self.get_bool_array_or_none(key)
        return self.__check_mandatory_field(key, value)

    def get_bool_array_or_none(self, key):
        return self.__get_array_by_key(key, lambda v: bool(v))

    def to_json_string(self):
        if self.__json_object is not None:
            return json.dumps(self.__json_object)
        elif self.__json_array is not None:
            return json.dumps(self.__json_array)
        else:
            return None
