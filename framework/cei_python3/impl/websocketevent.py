class WebSocketEvent:

    def __init__(self):
        self.__user_callback = None
        self.__checker = None
        pass

    def register_user(self, when, callback):
        self.__checker = when
        self.__user_callback = callback
        pass

    def register_system(self, when, callback):
        self.__checker = when
        self.__user_callback = callback
        pass

    def check(self, msg):
        return self.__checker(msg)

    def invoke(self, msg):
        return self.__user_callback(msg)