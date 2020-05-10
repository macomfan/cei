package impl

type JsonChecker struct {
	result int
}

func (inst *JsonChecker) pass() {
	if inst.result == -1 {
		inst.result = 1
	}
}

func (inst *JsonChecker) fail()  {
	inst.result = 0
}

func (inst *JsonChecker) CheckEqual(key, value string, jsonWrapper *JsonWrapper) {
	if jsonWrapper.Contains(key) {
		jsonValue := jsonWrapper.GetString(key)
		if jsonValue == value {
			inst.pass()
		} else {
			inst.fail()
		}
	}
}

func (inst *JsonChecker) CheckNotEqual(key, value string, jsonWrapper *JsonWrapper) {
	if jsonWrapper.Contains(key) {
		jsonValue:= jsonWrapper.GetString(key)
		if jsonValue != value {
			inst.pass()
		} else {
			inst.fail()
		}
	}
}

func (inst *JsonChecker) ContainKey(key string, jsonWrapper *JsonWrapper)  {
	if jsonWrapper.Contains(key) {
		inst.pass()
	} else {
		inst.fail()
	}
}

func (inst *JsonChecker) Complete() bool {
	return inst.result == 1
}