class WebSocketConnection:

    # system event
    # persistent event
    # disposable event

    def __init__(self):
        self.__event_list = list()
        pass

    def connect(self):
        def inner_func(str):
            pass
        i = inner_func
        i("aaa")

    def on_connect(self):
        pass

    def register_persistent_event(self, event):
        self.__event_list.append(event)

    def register_disposable_event(self, event):
        self.__event_list.append(event)

    def on_message(self, msg):
        for event in self.__event_list:
            if event.check(msg):
                event.invoke(msg)
        pass
