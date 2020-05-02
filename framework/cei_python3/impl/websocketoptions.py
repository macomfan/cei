class WebSocketOptions:
    url = None
    connection_timeout = None
    api_key = None
    secret_key = None

    def set_from(self, option):
        if option.url is not None:
            self.url = option.url
        if option.connection_timeout is not None:
            self.connection_timeout = option.connection_timeout
        if option.api_key is not None:
            self.api_key = option.api_key
        if option.secret_key is not None:
            self.secret_key = option.secret_key
