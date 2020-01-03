class CEIException(Exception):
    def __init__(self, error_message):
        self.error_message = error_message
