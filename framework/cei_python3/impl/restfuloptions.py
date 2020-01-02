

class RestfulOptions(object):
    url = None
    connectionTimeout = None
    apiKey = None
    secretKey = None

    def set_from(self, options):
        if options.url is not None:
            self.url = options.url
        if options.connectionTimeout is not None:
            self.connectionTimeout = options.connectionTimeout
        if options.apiKey is not None:
            self.apiKey = options.apiKey
        if options.secretKey is not None:
            self.secretKey = options.secretKey
