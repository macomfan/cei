from impl.ceiutils import CEIUtils
from impl.jsonchecker import JsonChecker
from impl.jsonwrapper import JsonWrapper
from impl.websocketconnection import WebSocketConnection
from impl.websocketevent import WebSocketEvent
from impl.websocketoptions import WebSocketOptions


class SimpleInfo:
    def __init__(self):
        self.name = None
        self.number = None
        self.price = None
        self.status = None


class WSClient:
    def __init__(self, option=None):
        self.__option = WebSocketOptions()
        self.__option.url = "http://127.0.0.1:8888"
        if option is not None:
            self.__option.set_from(option)
        self.__connection = WebSocketConnection(self.__option)

    def open(self, channel, name):
    
        def on_connect_event(connection):
            login = JsonWrapper()
            login.add_json_string("op", "echo")
            obj = JsonWrapper()
            obj.add_json_string("Name", name)
            login.add_json_object("param", obj)
            connection.send("login")
        self.__connection.set_on_connect(on_connect_event)
        self.__connection.connect(CEIUtils.string_replace("/websocket/{0}", channel))

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
            simple_info_var.name = root_obj.get_string("Name")
            simple_info_var.number = root_obj.get_int("Number")
            simple_info_var.price = root_obj.get_decimal("Price")
            simple_info_var.status = root_obj.get_bool("Status")
            on_echo(simple_info_var)
        on_echo_event.set_event(on_echo_event_event)
        self.__connection.register_event(on_echo_event)
        json = JsonWrapper()
        json.add_json_string("op", "echo")
        obj = JsonWrapper()
        obj.add_json_string("Name", name)
        obj.add_json_number("Price", price)
        obj.add_json_number("Number", number)
        obj.add_json_boolean("Status", status)
        json.add_json_object("param", obj)
        self.__connection.send(json.to_json_string())


class Procedures:
    def __init__(self):
        pass


