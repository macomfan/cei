

class RestfulOptions(object):
    url = None
    connection_timeout = None
    api_key = None
    secret_key = None

    def set_from(self, options):
        if options.url is not None:
            self.url = options.url
        if options.connection_timeout is not None:
            self.connection_timeout = options.connection_timeout
        if options.api_key is not None:
            self.api_key = options.api_key
        if options.secret_key is not None:
            self.secret_key = options.secret_key
