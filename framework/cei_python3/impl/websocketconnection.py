class WebSocketConnection:

    # system event
    # persistent event
    # disposable event

    def __init__(self):
        self.__event_list = list()
        pass

    def connect(self, url, option):
        print(option.a)

    def on_connect(self):
        pass

    def register_persistent_action(self, event):
        self.__event_list.append(event)

    def register_disposable_action(self, event):
        self.__event_list.append(event)

    def on_message(self, msg):
        for event in self.__event_list:
            if event.check(msg):
                event.invoke(msg)
        pass

    def send(self, what):
        pass
