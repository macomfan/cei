class WebSocketEvent:

    def __init__(self):
        self.__callback_list = list()
        pass

    def add_callback(self, when, callback):
        self.__callback_list.append([when, callback])
        pass

    def invoke(self):
        for item in self.__callback_list:
            if item[0] is not None:
                if item[0].check():
                    item[1]()