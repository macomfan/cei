class StringWrapper:
    def __init__(self):
        self.__items = list()
        self.__result = str()

    def add_string_array(self, string_array, trim):
        if string_array is None:
            return
        if trim:
            for item in list:
                self.__items.append(item.strip())
        else:
            self.__items.append(string_array)

    def append_string_item(self, value):
        self.__items.append(value)

    def combine_string_items(self, prefix, suffix, separator):
        self.__result = str()
        for value in self.__items:
            if len(self.__result) != 0:
                self.__result += separator
            self.__result += prefix + value + suffix

    def to_string(self):
        return self.__result
