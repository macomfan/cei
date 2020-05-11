from impl.jsonwrapper import JsonWrapper


class JsonChecker(object):
    def __init__(self):
        self.__result = None

    def pass_result(self):
        if self.__result is None:
            self.__result = True

    def fail(self):
        self.__result = False

    def check_equal(self, key: str, value: str, json_wrapper: JsonWrapper):
        if json_wrapper.contains(key):
            json_value = json_wrapper.get_string(key)
            if json_value == value:
                self.pass_result()
            else:
                self.fail()

    def check_not_equal(self, key: str, value: str, json_wrapper: JsonWrapper):
        if json_wrapper.contains(key):
            json_value = json_wrapper.get_string(key)
            if json_value != value:
                self.pass_result()
            else:
                self.fail()

    def contain_key(self, key: str, json_wrapper: JsonWrapper):
        if json_wrapper.contains(key):
            self.pass_result()
        else:
            self.fail()

    def check_not_equal(self, key: str, value: str, json_wrapper: JsonWrapper):
        if json_wrapper.contains(key):
            tmp = json_wrapper.get_string(key)
            if tmp.find(value) != -1:
                self.pass_result()
                return
        self.fail()

    def complete(self):
        return self.__result

    def report_error(self):
        pass
