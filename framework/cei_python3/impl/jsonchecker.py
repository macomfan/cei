from impl.jsonwrapper import JsonWrapper


class JsonChecker:
    def __init__(self):
        self.result = -1

    def pass_result(self):
        if self.result == -1:
            self.result = 1

    def fail(self):
        self.result = 0

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

    def complete(self):
        return self.result

    def report_error(self):
        pass
