package jsonwrapper

import (
	"encoding/json"
)

type JsonWrapper struct {
	data map[string]interface{}
}

func ParseFromString(data string) *JsonWrapper {
	instance := new(JsonWrapper)
	json.Unmarshal([]byte(data), &instance.data)
	return instance
}

func NewJsonWrapper(data map[string]interface{}) *JsonWrapper {
	instance := new(JsonWrapper)
	instance.data = data
	return instance
}

func (inst *JsonWrapper) checkMandatoryField(key string) interface{} {
	value, res := inst.data[key]
	if res == false {
		// TODO
	}
	return value
}

func (inst *JsonWrapper) GetString(key string) string {
	value := inst.checkMandatoryField(key)
	return value.(string)
}

func (inst *JsonWrapper) GetInt64(key string) int64 {
	value := inst.checkMandatoryField(key)
	return int64(value.(float64))
}

func (inst *JsonWrapper) GetFloat64(key string) float64 {
	value := inst.checkMandatoryField(key)
	return value.(float64)
}

func (inst *JsonWrapper) GetObject(key string) *JsonWrapper {
	value := inst.checkMandatoryField(key)
	return NewJsonWrapper(value.(map[string]interface{}))
}

func (inst *JsonWrapper) GetObjectArray(key string) []*JsonWrapper {
	value := inst.checkMandatoryField(key)
	res := []*JsonWrapper{}
	for _, value := range value.([]interface{}) {
		res = append(res, NewJsonWrapper(value.(map[string]interface{})))
	 }
	return res
}

func (inst *JsonWrapper) GetStringArray(key string) []string {
	value := inst.checkMandatoryField(key)
	res := []string{}
	for _, value := range value.([]interface{}) {
		res = append(res, value.(string))
	 }
	return res
}
