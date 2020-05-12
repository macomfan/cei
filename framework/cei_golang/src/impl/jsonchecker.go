package impl

import "strings"

type JsonChecker struct {
	result int
}

func NewJsonChecker() *JsonChecker {
	inst := new(JsonChecker)
	return inst
}

func (inst *JsonChecker) pass() {
	if inst.result == -1 {
		inst.result = 1
	}
}

func (inst *JsonChecker) fail() {
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
		jsonValue := jsonWrapper.GetString(key)
		if jsonValue != value {
			inst.pass()
		} else {
			inst.fail()
		}
	}
}

func (inst *JsonChecker) ContainKey(key string, jsonWrapper *JsonWrapper) {
	if jsonWrapper.Contains(key) {
		inst.pass()
	} else {
		inst.fail()
	}
}

func (inst *JsonChecker) ValueInclude(key, value string, jsonWrapper *JsonWrapper) {
	if jsonWrapper.Contains(key) {
		tmp := jsonWrapper.GetString(key)
		if strings.Index(tmp, value) != -1 {
			inst.pass()
		}
	}
	inst.fail()
}

func (inst *JsonChecker) Complete() bool {
	return inst.result == 1
}
