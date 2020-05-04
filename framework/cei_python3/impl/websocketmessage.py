class WebSocketMessage:

    def __init__(self, text: str = str(), byte_s: bytes = bytes()):
        self.__text = text
        self.__bytes = byte_s

    def get_string(self):
        return self.__text

    def get_bytes(self):
        return self.__bytes

    def upgrade(self, value: str):
        self.__text = value
