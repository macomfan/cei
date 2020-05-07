from impl.websocketconnection import WebSocketConnection
from impl.websocketmessage import WebSocketMessage


class WebSocketEvent:

    def __init__(self, persistence):
        self.__checker = None
        self.__callback = None
        self.__persistence = persistence
        pass

    def set_trigger(self, check):
        self.__checker = check

    def set_event(self, callback):
        self.__callback = callback

    def is_persistence(self):
        return self.__persistence

    def check(self, msg):
        return self.__checker(msg)

    def invoke(self, connection: WebSocketConnection, msg: WebSocketMessage):
        self.__callback(connection, msg)
