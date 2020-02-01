import hmac
import hashlib
import base64
import urllib.parse

from exception.ceiexception import CEIException
from impl.restfulrequest import RestfulRequest


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
    def hmacsha256(input_value, key: str):
        if key is None:
            raise CEIException("key is None")
        return hmac.new(key.encode('utf-8'), msg=input_value.encode('utf-8'), digestmod=hashlib.sha256).digest()

    @staticmethod
    def base64(input_value):
        return base64.b64encode(input_value).decode()

    @staticmethod
    def add_string_array(input_list: list, value: str):
        input_list.append(value)
        return input_list

    @staticmethod
    def get_now(format: str):
        return "2019-12-31T02%3A23%3A25"

    @staticmethod
    def combine_query_string(request: RestfulRequest, sort: Constant, separator: str):
        query_string = list()
        query_string.extend(request.get_query_string())

        def get_key(tmp):
            return tmp[0]

        if sort is not None:
            if sort == SignatureTool.Constant.ASC:
                query_string = sorted(query_string, key=lambda tmp: tmp[0])
            elif sort == SignatureTool.Constant.DSC:
                query_string = sorted(query_string, key=lambda tmp: tmp[0], reverse=True)
            elif sort == SignatureTool.Constant.NONE:
                pass
            else:
                # TODO
                pass

        result = ""
        for item in query_string:
            if len(result) != 0:
                if separator is not None:
                    result += separator
            result += item[0]
            result += "="
            result += urllib.parse.quote(item[1], "utf-8")
        return result

    @staticmethod
    def append_to_string(obj: str, value: str):
        return obj + value

    @staticmethod
    def get_request_info(request: RestfulRequest, method: Constant, convert: Constant):
        result = ""
        if method is None:
            # TODO
            return result
        if method == SignatureTool.Constant.HOST:
            result = urllib.parse.urlparse(request.get_url()).hostname
        elif method == SignatureTool.Constant.TARGET:
            result = request.get_target()
        elif method == SignatureTool.Constant.METHOD:
            result = request.get_method()
        else:
            # TODO
            pass
        if convert is None:
            return result
        if convert == SignatureTool.Constant.UPPERCASE:
            return result.upper()
        elif convert == SignatureTool.Constant.LOWERCASE:
            return result.lower()
        elif convert == SignatureTool.Constant.NONE:
            return result
        else:
            # TODO
            return result
