from enum import Enum

import websocket
import threading
from impl.websocketoptions import WebSocketOptions
from impl.websocketmessage import WebSocketMessage


class ConnectionState(Enum):
    IDLE = 0
    CONNECTED = 1
    CLOSED_ON_ERROR = 2


class WebSocketConnection:
    static_id = 0

    def __init__(self, option: WebSocketOptions):
        self.__target = None
        self.__option = option
        self.__websocket = None
        self.__event = list()
        self.__on_connect = None
        self.__on_close = None
        self.__thread = None
        self.__client_id = WebSocketConnection.static_id
        self.__status = ConnectionState.IDLE
        self.__connected_notification = threading.Condition()
        WebSocketConnection.static_id = WebSocketConnection.static_id + 1

    def connect(self, target: str):
        if self.__status == ConnectionState.CONNECTED:
            return
        self.__target = target
        self.__websocket = websocket.WebSocketApp(
            self.__option.url + self.__target,
            on_open=self.on_open,
            on_message=self.on_message,
            on_error=self.on_failure,
            on_close=self.on_close,
        )
        self.__thread = threading.Thread(target=self.__websocket.run_forever, name=str(self.__client_id))
        self.__thread.start()
        print("Before wait")
        self.__connected_notification.acquire()
        self.__connected_notification.wait(self.__option.connection_timeout)
        self.__connected_notification.release()
        if self.__status != ConnectionState.CONNECTED:
            print("Connect failed")

    def on_open(self):
        if self.__on_connect is not None:
            self.__on_connect(self)
        self.__status = ConnectionState.CONNECTED
        self.__connected_notification.acquire()
        self.__connected_notification.notify()
        self.__connected_notification.release()

    def on_failure(self):
        pass

    def on_close(self):
        pass

    def close(self):
        pass

    def set_on_close(self, on_close):
        self.__on_close = on_close

    def set_on_connect(self, on_connect):
        self.__on_connect = on_connect

    def register_event(self, event):
        self.__event.append(event)

    def on_message(self, data):
        print("Receive " + data)
        msg = None
        if isinstance(data, str):
            msg = WebSocketMessage(text=data)
        elif isinstance(data, bytes):
            msg = WebSocketMessage(byte_s=data)
        new_event = list()
        for event in self.__event:
            if event.check(msg):
                event.invoke(self, msg)
                if event.is_persistence():
                    new_event.append(event)
            else:
                new_event.append(event)
        self.__event = new_event
        print("Receive end")

    def send(self, what):
        print("Send: " + what)
        self.__websocket.send(what)
