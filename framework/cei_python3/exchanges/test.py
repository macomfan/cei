from impl.ceiutils import CEIUtils
from impl.jsonchecker import JsonChecker
from impl.jsonwrapper import JsonWrapper
from impl.restfulconnection import RestfulConnection
from impl.restfuloptions import RestfulOptions
from impl.restfulrequest import RestfulRequest
from impl.stringwrapper import StringWrapper
from impl.websocketconnection import WebSocketConnection
from impl.websocketevent import WebSocketEvent
from impl.websocketoptions import WebSocketOptions


class Order:
    def __init__(self):
        self.order_id = None


class PriceEntity:
    def __init__(self):
        self.price = None
        self.volume = None


class InfoEntity:
    def __init__(self):
        self.name = None
        self.info_value = None


class SimpleInfo:
    def __init__(self):
        self.name = None
        self.number = None
        self.price = None
        self.status = None


class LastTrade:
    def __init__(self):
        self.price = None
        self.volume = None
        self.timestamp = None
        self.symbol = None


class ModelValue:
    def __init__(self):
        self.name = None
        self.value = None


class ModelInfo:
    def __init__(self):
        self.name = None
        self.value = None


class PriceList:
    def __init__(self):
        self.name = None
        self.prices = None


class InfoList:
    def __init__(self):
        self.name = None
        self.values = None


class TradeEntity:
    def __init__(self):
        self.price = None
        self.volume = None


class HistoricalTrade:
    def __init__(self):
        self.data = None


class GetClient:
    def __init__(self, option=None):
        self.__option = RestfulOptions()
        self.__option.url = "http://127.0.0.1:8888"
        if option is not None:
            self.__option.set_from(option)

    def get_simple_info(self):
        request = RestfulRequest(self.__option)
        request.set_target("/restful/get/simpleInfo")
        request.set_method(RestfulRequest.Method.GET)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        simple_info_var = SimpleInfo()
        simple_info_var.name = root_obj.get_string("Name")
        simple_info_var.number = root_obj.get_int("Number")
        simple_info_var.price = root_obj.get_decimal("Price")
        simple_info_var.status = root_obj.get_bool("Status")
        return simple_info_var

    def get_model_info(self):
        request = RestfulRequest(self.__option)
        request.set_target("/restful/get/modelInfo")
        request.set_method(RestfulRequest.Method.GET)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        json_checker = JsonChecker()
        json_checker.check_equal("aaa", "aa", root_obj)
        json_checker.report_error()
        model_info_var = ModelInfo()
        model_info_var.name = root_obj.get_string("Name")
        obj = root_obj.get_object("DataL1")
        obj0 = obj.get_object("DataL2")
        model_value_var = ModelValue()
        obj1 = obj0.get_object("Value")
        model_value_var.name = obj1.get_string("Name")
        model_value_var.value = obj1.get_int("Value")
        model_info_var.value = model_value_var
        return model_info_var

    def get_price_list(self):
        request = RestfulRequest(self.__option)
        request.set_target("/restful/get/priceList")
        request.set_method(RestfulRequest.Method.GET)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        price_list_var = PriceList()
        price_list_var.name = root_obj.get_string("Name")
        obj = root_obj.get_array("Prices")
        for item in obj.array():
            price_entity_var = PriceEntity()
            price_entity_var.price = item.get_decimal("[0]")
            price_entity_var.volume = item.get_decimal("[1]")
            if price_list_var.prices is None:
                price_list_var.prices = list()
            price_list_var.prices.append(price_entity_var)
        return price_list_var

    def get_info_list(self):
        request = RestfulRequest(self.__option)
        request.set_target("/restful/get/infoList")
        request.set_method(RestfulRequest.Method.GET)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        info_list_var = InfoList()
        info_list_var.name = root_obj.get_string("Name")
        obj = root_obj.get_array("Values")
        for item in obj.array():
            info_entity_var = InfoEntity()
            info_entity_var.name = item.get_string("Name")
            info_entity_var.info_value = item.get_string("InfoValue")
            if info_list_var.values is None:
                info_list_var.values = list()
            info_list_var.values.append(info_entity_var)
        return info_list_var

    def get_test_array(self):
        request = RestfulRequest(self.__option)
        request.set_target("/restful/get/testArray")
        request.set_method(RestfulRequest.Method.GET)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        simple_info_var = SimpleInfo()
        simple_info_var.name = root_obj.get_string("[0]")
        simple_info_var.number = root_obj.get_int("[1]")
        simple_info_var.price = root_obj.get_decimal("[2]")
        simple_info_var.status = root_obj.get_bool("[3]")
        return simple_info_var

    def inputs_by_get(self, name, number, price, status):
        request = RestfulRequest(self.__option)
        request.set_target("/restful/get/inputsByGet")
        request.set_method(RestfulRequest.Method.GET)
        request.add_query_string("Name", name)
        request.add_query_string("Number", str(number))
        request.add_query_string("Price", str(price))
        request.add_query_string("Status", str(status))
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        simple_info_var = SimpleInfo()
        simple_info_var.name = root_obj.get_string("Name")
        simple_info_var.number = root_obj.get_int("Number")
        simple_info_var.price = root_obj.get_decimal("Price")
        simple_info_var.status = root_obj.get_bool("Status")
        return simple_info_var

    def url(self, input_u):
        request = RestfulRequest(self.__option)
        request.set_target(CEIUtils.string_replace("/restful/get/url/{0}", input_u))
        request.set_method(RestfulRequest.Method.GET)
        response = RestfulConnection.query(request)
        return response.get_string()


class PostClient:
    def __init__(self, option=None):
        self.__option = RestfulOptions()
        self.__option.url = "http://127.0.0.1:8888"
        if option is not None:
            self.__option.set_from(option)

    def post_inputs(self, this, price, number, status):
        request = RestfulRequest(self.__option)
        post_msg = JsonWrapper()
        post_msg.add_json_string("Name", this)
        post_msg.add_json_number("Price", price)
        post_msg.add_json_number("Number", number)
        post_msg.add_json_boolean("Status_1", status)
        request.set_target("/restful/post/echo")
        request.set_method(RestfulRequest.Method.POST)
        request.set_post_body(post_msg.to_json_string())
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        simple_info_var = SimpleInfo()
        simple_info_var.name = root_obj.get_string("Name")
        simple_info_var.number = root_obj.get_int("Number")
        simple_info_var.price = root_obj.get_decimal("Price")
        simple_info_var.status = root_obj.get_bool("Status_1")
        return simple_info_var

    def authentication(self, name, number):
        request = RestfulRequest(self.__option)
        post_msg = JsonWrapper()
        post_msg.add_json_number("Number", number)
        request.set_target("/restful/post/authentication")
        request.set_method(RestfulRequest.Method.POST)
        request.add_query_string("Name", name)
        request.set_post_body(post_msg.to_json_string())
        Procedures.restful_auth(request, self.__option)
        response = RestfulConnection.query(request)
        root_obj = JsonWrapper.parse_from_string(response.get_string())
        simple_info_var = SimpleInfo()
        simple_info_var.name = root_obj.get_string("Name")
        simple_info_var.number = root_obj.get_int("Number")
        simple_info_var.price = root_obj.get_decimal("Price")
        return simple_info_var


class WSClient:
    def __init__(self, option=None):
        self.__option = WebSocketOptions()
        self.__option.url = "ws://127.0.0.1:8888"
        if option is not None:
            self.__option.set_from(option)
        self.__connection = WebSocketConnection(self.__option)

    def open(self, channel, name, on_connect):
        on_ping_event = WebSocketEvent(True)
    
        def on_ping_event_trigger(msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            json_checker = JsonChecker()
            json_checker.check_equal("op", "ping", root_obj)
            return json_checker.complete()
        on_ping_event.set_trigger(on_ping_event_trigger)
    
        def on_ping_event_event(connection, msg):
            ts = CEIUtils.get_now("Unix_ms")
            json_result = JsonWrapper()
            json_result.add_json_string("op", "pong")
            json_result.add_json_string("ts", ts)
            connection.send(json_result.to_json_string())
        on_ping_event.set_event(on_ping_event_event)
        self.__connection.register_event(on_ping_event)
    
        def on_connect_event(connection):
            login = JsonWrapper()
            login.add_json_string("op", "login")
            obj = JsonWrapper()
            obj.add_json_string("Name", name)
            login.add_json_object("param", obj)
            obj0 = JsonWrapper()
            obj0.add_json_number("[]", float("1"))
            obj0.add_json_number("[]", float("2"))
            login.add_json_object("array", obj0)
            connection.send(login.to_json_string())
            on_connect(connection)
        self.__connection.set_on_connect(on_connect_event)
        self.__connection.connect(CEIUtils.string_replace("/websocket/{0}", channel))

    def close(self, on_close):
    
        def on_close_event(connection):
            on_close(connection)
        self.__connection.set_on_close(on_close_event)
        self.__connection.close()

    def request_echo(self, name, price, number, status, on_echo):
        on_echo_event = WebSocketEvent(False)
    
        def on_echo_event_trigger(msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            json_checker = JsonChecker()
            json_checker.check_equal("op", "echo", root_obj)
            obj = root_obj.get_object_or_none("param")
            json_checker.check_equal("Name", name, obj)
            return json_checker.complete()
        on_echo_event.set_trigger(on_echo_event_trigger)
    
        def on_echo_event_event(connection, msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            simple_info_var = SimpleInfo()
            obj = root_obj.get_object("param")
            simple_info_var.name = obj.get_string("Name")
            simple_info_var.number = obj.get_int("Number")
            simple_info_var.price = obj.get_decimal("Price")
            simple_info_var.status = obj.get_bool("Status")
            on_echo(simple_info_var)
        on_echo_event.set_event(on_echo_event_event)
        self.__connection.register_event(on_echo_event)
        json_result = JsonWrapper()
        json_result.add_json_string("op", "echo")
        obj = JsonWrapper()
        obj.add_json_string("Name", name)
        obj.add_json_number("Price", price)
        obj.add_json_number("Number", number)
        obj.add_json_boolean("Status", status)
        json_result.add_json_object("param", obj)
        self.__connection.send(json_result.to_json_string())

    def subscribe_second1(self, on_second1):
        on_second1event = WebSocketEvent(True)
    
        def on_second1event_trigger(msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            json_checker = JsonChecker()
            json_checker.check_equal("ch", "Second1", root_obj)
            return json_checker.complete()
        on_second1event.set_trigger(on_second1event_trigger)
    
        def on_second1event_event(connection, msg):
            root_obj = JsonWrapper.parse_from_string(msg.get_string())
            simple_info_var = SimpleInfo()
            simple_info_var.name = root_obj.get_string("Name")
            simple_info_var.number = root_obj.get_int("Number")
            simple_info_var.price = root_obj.get_decimal("Price")
            simple_info_var.status = root_obj.get_bool("Status")
            on_second1(simple_info_var)
        on_second1event.set_event(on_second1event_event)
        self.__connection.register_event(on_second1event)
        json_result = JsonWrapper()
        json_result.add_json_string("op", "sub")
        json_result.add_json_string("name", "Second1")
        self.__connection.send(json_result.to_json_string())


class Procedures:
    def __init__(self):
        pass

    @staticmethod
    def restful_auth(request, option):
        timestamp = CEIUtils.get_now("%Y':'%m':'%d'T'#H':'%M':'%S")
        request.add_query_string("AccessKeyId", option.api_key)
        request.add_query_string("SignatureMethod", "HmacSHA256")
        request.add_query_string("SignatureVersion", "2")
        request.add_query_string("Timestamp", timestamp)
        query_string = CEIUtils.combine_query_string(request, CEIUtils.Constant.ASC, "&")
        method = CEIUtils.get_request_info(request, CEIUtils.Constant.METHOD, CEIUtils.Constant.UPPERCASE)
        host = CEIUtils.get_request_info(request, CEIUtils.Constant.HOST, CEIUtils.Constant.NONE)
        target = CEIUtils.get_request_info(request, CEIUtils.Constant.TARGET, CEIUtils.Constant.NONE)
        buffer = StringWrapper()
        buffer.append_string_item(method)
        buffer.append_string_item(host)
        buffer.append_string_item(target)
        buffer.append_string_item(query_string)
        buffer.combine_string_items("", "", "\\n")
        hmacsha256 = CEIUtils.hmacsha256(buffer.to_string(), option.secret_key)
        result = CEIUtils.base64(hmacsha256)
        request.add_query_string("Signature", result)


