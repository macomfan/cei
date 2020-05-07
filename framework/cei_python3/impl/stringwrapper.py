
class StringWrapper:
    def __init__(self):
        self.__items = list()
        self.__result = str()

    def append_string_item(self, value):
        self.__items.append(value)

    def combine_string_items(self, separator):
        self.__result = str()
        for value in self.__items:
            if len(self.__result) != 0:
                self.__result += separator
            self.__result += value

    def to_string(self):
        return self.__result
