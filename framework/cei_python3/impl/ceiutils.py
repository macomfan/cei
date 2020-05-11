import time
import hmac
import gzip
import hashlib
import base64
import urllib.parse

from impl.restfulrequest import RestfulRequest


class CEIUtils:
    class Constant:
        ASC = "asc",
        DSC = "dsc",
        HOST = "host",
        METHOD = "method",
        TARGET = "target",
        POSTBODY = "postbody"
        UPPERCASE = "uppercase",
        LOWERCASE = "lowercase",
        NONE = "none"

    @staticmethod
    def get_request_info(request: RestfulRequest, method: Constant, convert: Constant):
        result = ""
        if method is None:
            # TODO
            return result
        if method == CEIUtils.Constant.HOST:
            result = urllib.parse.urlparse(request.get_url()).hostname
        elif method == CEIUtils.Constant.TARGET:
            result = request.get_target()
        elif method == CEIUtils.Constant.METHOD:
            result = request.get_method()
        elif method == CEIUtils.Constant.POSTBODY:
            result = str(request.get_post_body(), encoding="utf-8")
        else:
            # TODO
            pass
        if convert is None:
            return result
        if convert == CEIUtils.Constant.UPPERCASE:
            return result.upper()
        elif convert == CEIUtils.Constant.LOWERCASE:
            return result.lower()
        elif convert == CEIUtils.Constant.NONE:
            return result
        else:
            # TODO
            return result

    @staticmethod
    def combine_query_string(request: RestfulRequest, sort: Constant, separator: str):
        query_string = list()
        query_string.extend(request.get_query_string())

        def get_key(tmp):
            return tmp[0]

        if sort is not None:
            if sort == CEIUtils.Constant.ASC:
                query_string = sorted(query_string, key=lambda tmp: tmp[0])
            elif sort == CEIUtils.Constant.DSC:
                query_string = sorted(query_string, key=lambda tmp: tmp[0], reverse=True)
            elif sort == CEIUtils.Constant.NONE:
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
    def base64(input_value):
        return base64.b64encode(input_value).decode()

    @staticmethod
    def hmacsha256(input_value, key: str):
        if key is None:
            raise CEIException("key is None")
        return hmac.new(key.encode('utf-8'), msg=input_value.encode('utf-8'), digestmod=hashlib.sha256).digest()

    @staticmethod
    def string_replace(format_string: str, *args):
        return format_string.format(*args)

    @staticmethod
    def gzip(message: bytes):
        return gzip.decompress(message).decode("utf-8")

    @staticmethod
    def get_now(time_format: 'str'):
        """
        Convert current date/time in UTC to string

        :param time_format:
        :param format The format of the output string, following below rules:
        %Y The 4 characters year code. like 2019.
        %M The 2 characters month in year. range is 01 - 12.
        %D The 2 characters day in month. range is 01 - 31.
        %h The 2 characters hour (24H) in day. range is 00 - 23.
        %m The 2 characters minute in hour. range is 00- 59.
        %s The 2 characters second in minute. range is 00 - 59.
        %ms The 3 characters millisecond. range is 000 - 999.
        Unix_s The unix format timestamp based on second.
        Unix_ms The unix format timestamp based on millisecond. (default)

        :return The time string.
        """
        if time_format is None or time_format == "":
            return str(round(time.time() * 1000))
        if time_format == "Unix_s":
            return str(round(time.time()))
        elif time_format == "Unix_ms":
            return str(round(time.time() * 1000))
        else:
            items = time_format.split("%")
            formatString = ""
            for i in range(0, len(items)):
                if (i == 0):
                    formatString = items[i]

        pass
