class WebSocketConnection:

    # system event
    # persistent event
    # disposable event

    def __init__(self):
        pass

    def connect(self):
        def inner_func(str):
            pass
        i = inner_func
        i("aaa")

    def on_connect(self):
        pass

    def register_persistent_event(self, event):
        pass

    def register_disposable_event(self, event):
        pass

    def on_message(self):
        # for all event
        # if (event.test_hit())  event.invoke() and remove single event
        pass
