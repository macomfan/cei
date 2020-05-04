import hmac
import hashlib
import base64
import urllib.parse

from exception.ceiexception import CEIException
from impl.restfulrequest import RestfulRequest


class SignatureTool:


    @staticmethod
    def combine_string_array(string_list: list, separator: str):
        res = ""
        for item in string_list:
            if len(res) == 0:
                res += item
            else:
                res += separator
                res += item
        return res

    @staticmethod
    def add_string_array(input_list: list, value: str):
        input_list.append(value)
        return input_list

    @staticmethod
    def get_now(format: str):
        return "2019-12-31T02%3A23%3A25"



    @staticmethod
    def append_to_string(obj: str, value: str):
        return obj + value

