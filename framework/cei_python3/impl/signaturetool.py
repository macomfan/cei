import hmac
import hashlib
import base64


class SignatureTool:
    class Constant:
        ASC = "asc",
        DSC = "dsc",
        HOST = "host",
        METHOD = "method",
        TARGET = "target",
        UPPERCASE = "uppercase",
        LOWERCASE = "lowercase",
        NONE = "none"

    @staticmethod
    def combine_string_array(string_list, separator):
        res = ""
        for item in string_list:
            if len(res) == 0:
                res += item
            else:
                res += separator
                res += item
        return res

    @staticmethod
    def hmacsha256(input_value, key):
        return hmac.new(key.encode('utf-8'), msg=input_value.encode('utf-8'), digestmod=hashlib.sha256).digest()

    @staticmethod
    def base64(input_value):
        return base64.b64encode(input_value).decode()
