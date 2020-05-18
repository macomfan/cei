package impl

import (
	"./utils"
	"encoding/json"
	"fmt"
	"regexp"
	"strconv"
)

type JsonWrapper struct {
	object map[string]interface{}
	array  []interface{}
}

func ParseJsonFromString(data string) *JsonWrapper {
	instance := new(JsonWrapper)
	var jsonObject interface{}
	_ = json.Unmarshal([]byte(data), &jsonObject)
	if obj, ok := jsonObject.(map[string]interface{}); ok {
		return newObjectJsonWrapper(obj)
	} else if obj, ok := jsonObject.([]interface{}); ok {
		return newArrayJsonWrapper(obj)
	} else {
		// TODO
		panic("TODO")
	}
	return instance
}

func NewJsonWrapper() *JsonWrapper {
	instance := new(JsonWrapper)
	return instance
}

func newObjectJsonWrapper(data map[string]interface{}) *JsonWrapper {
	instance := new(JsonWrapper)
	instance.object = data
	return instance
}

func newArrayJsonWrapper(data []interface{}) *JsonWrapper {
	instance := new(JsonWrapper)
	instance.array = data
	return instance
}

func (inst *JsonWrapper) checkMandatoryField(key string, value interface{}) interface{} {
	if value == nil {
		panic(fmt.Sprintf("[Json] Get json item field: %v, does not exist", key))
	}
	return value
}

func (inst *JsonWrapper) checkMandatoryObject(key string, value *JsonWrapper) *JsonWrapper {
	if value == nil {
		panic(fmt.Sprintf("[Json] The JsonWrapper is null"))
	} else if value.array == nil && value.object == nil {
		panic(fmt.Sprintf("[Json] Get json object: %v, does not exist", key))
	} else if value.object != nil && value.array != nil {
		panic(fmt.Sprintf("[Json] The JsonWrapper is invalid"))
	}
	return value
}

func (inst *JsonWrapper) getIndexByKey(key string) interface{} {
	if key == "[]" {
		return -1
	}
	reg := regexp.MustCompile(`^\[[0-9]*]$`)
	m := reg.FindStringIndex(key)
	if m != nil {
		res, _ := strconv.Atoi(key[m[0]+1 : m[1]-1])
		return res
	}
	return nil
}

func (inst *JsonWrapper) getByKey(key string) interface{} {
	index := inst.getIndexByKey(key)
	if index == nil {
		if inst.object != nil {
			if res, err := inst.object[key]; err == true {
				return res
			} else {
				return nil
			}
		}
	} else {
		if inst.array != nil && 0 <= index.(int) && index.(int) < len(inst.array) {
			return inst.array[index.(int)]
		}
	}
	return nil
}

func (inst *JsonWrapper) initializeAsObject() {
	if inst.object == nil && inst.array == nil {
		inst.object = make(map[string]interface{})
	} else if inst.array != nil {
		panic("[Json] Cannot create json object")
	}
}

func (inst *JsonWrapper) initializeAsArray() {
	if inst.object == nil && inst.array == nil {
		inst.array = make([]interface{}, 0)
	} else if inst.object != nil {
		panic("[Json] Cannot create json array")
	}
}

func (inst *JsonWrapper) addJsonValue(key string, value interface{}) {
	index := inst.getIndexByKey(key)
	if index == nil {
		inst.initializeAsObject()
		inst.object[key] = value
	} else if index == -1 {
		inst.initializeAsArray()
		inst.array = append(inst.array, value)
	} else {
		panic("[Json] Cannot specify thd detail index to insert a item to array")
	}
}

func (inst* JsonWrapper) Contains(key string) bool{
	index := inst.getIndexByKey(key)
	if index != nil && inst.array != nil {
		return index.(int) < len(inst.array) && index.(int) > 0
	} else if inst.object != nil {
		_, ok := inst.object[key]
		return ok
	}
	return false
}

func (inst *JsonWrapper) AddJsonString(key string, value string) {
	inst.addJsonValue(key, value)
}

func (inst *JsonWrapper) AddJsonInt64(key string, value int64) {
	inst.addJsonValue(key, value)
}

func (inst *JsonWrapper) AddJsonFloat64(key string, value float64) {
	inst.addJsonValue(key, value)
}

func (inst *JsonWrapper) AddJsonBool(key string, value bool) {
	inst.addJsonValue(key, value)
}

func (inst *JsonWrapper) AddJsonObject(key string, jsonWrapper *JsonWrapper) {
	if jsonWrapper.object != nil {
		inst.addJsonValue(key, jsonWrapper.object)
	} else if jsonWrapper.array != nil {
		inst.addJsonValue(key, jsonWrapper.array)
	} else {
		panic("[Json] Cannot add a null object to json object")
	}

}

func (inst *JsonWrapper) GetString(key string) string {
	value := inst.getByKey(key)
	return inst.checkMandatoryField(key, utils.ToString(value)).(string)
}

func (inst *JsonWrapper) GetStringOrDefault(key string) string {
	value := inst.getByKey(key)
	return ToStringOrDefault(value)
}

func (inst *JsonWrapper) GetInt64(key string) int64 {
	value := inst.getByKey(key)
	return inst.checkMandatoryField(key, ToInt64(value)).(int64)
}

func (inst *JsonWrapper) GetInt64OrDefault(key string) int64 {
	value := inst.getByKey(key)
	return ToInt64OrDefault(value)
}

func (inst *JsonWrapper) GetFloat64(key string) float64 {
	value := inst.getByKey(key)
	return inst.checkMandatoryField(key, ToFloat64(value)).(float64)
}

func (inst *JsonWrapper) GetFloat64OrDefault(key string) float64 {
	value := inst.getByKey(key)
	return ToFloat64OrDefault(value)
}

func (inst *JsonWrapper) GetBool(key string) bool {
	value := inst.getByKey(key)
	return inst.checkMandatoryField(key, ToBool(value)).(bool)
}

func (inst *JsonWrapper) GetBoolOrDefault(key string) bool {
	value := inst.getByKey(key)
	return ToBoolOrDefault(value)
}

func (inst *JsonWrapper) GetObject(key string) *JsonWrapper {
	value := inst.GetObjectOrNil(key)
	return inst.checkMandatoryObject(key, value)
}

func (inst *JsonWrapper) GetObjectOrNil(key string) *JsonWrapper {
	value := inst.getByKey(key)
	if value == nil {
		return NewJsonWrapper()
	}
	if obj, ok := value.(map[string]interface{}); ok {
		return newObjectJsonWrapper(obj)
	} else {
		// TODO
		panic("TODO")
	}
	return NewJsonWrapper()
}

func (inst *JsonWrapper) GetArray(key string) *JsonWrapper {
	value := inst.GetArrayOrNil(key)
	return inst.checkMandatoryObject(key, value)
}

func (inst *JsonWrapper) GetArrayOrNil(key string) *JsonWrapper {
	value := inst.getByKey(key)
	if obj, ok := value.([]interface{}); ok {
		return newArrayJsonWrapper(obj)
	}
	return NewJsonWrapper()
}

func (inst *JsonWrapper) Array() []*JsonWrapper {
	res := make([]*JsonWrapper, 0)
	if inst.array != nil {
		for _, item := range inst.array {
			if arr, ok := item.([]interface{}); ok {
				res = append(res, newArrayJsonWrapper(arr))
			} else if obj, ok := item.(map[string]interface{}); ok {
				res = append(res, newObjectJsonWrapper(obj))
			} else {
				res = append(res, NewJsonWrapper())
			}
		}
	}
	return res
}

func (inst *JsonWrapper) GetStringArray(key string) []string {
	value := inst.GetStringArrayOrNil(key)
	return inst.checkMandatoryField(key, value).([]string)
}

func (inst *JsonWrapper) GetStringArrayOrNil(key string) []string {
	value := inst.getByKey(key)
	if obj, ok := value.([]interface{}); ok {
		res := make([]string, 0)
		for _, item := range obj {
			res = append(res, ToStringOrDefault(item))
		}
		return res
	} else {
		return nil
	}
}

func (inst *JsonWrapper) GetInt64Array(key string) []int64 {
	value := inst.GetInt64ArrayOrNil(key)
	return inst.checkMandatoryField(key, value).([]int64)
}

func (inst *JsonWrapper) GetInt64ArrayOrNil(key string) []int64 {
	value := inst.getByKey(key)
	if obj, ok := value.([]interface{}); ok {
		res := make([]int64, 0)
		for _, item := range obj {
			res = append(res, ToInt64OrDefault(item))
		}
		return res
	} else {
		return nil
	}
}

func (inst *JsonWrapper) GetFloat64Array(key string) []float64 {
	value := inst.GetFloat64ArrayOrNil(key)
	return inst.checkMandatoryField(key, value).([]float64)
}

func (inst *JsonWrapper) GetFloat64ArrayOrNil(key string) []float64 {
	value := inst.getByKey(key)
	if obj, ok := value.([]interface{}); ok {
		res := make([]float64, 0)
		for _, item := range obj {
			res = append(res, ToFloat64OrDefault(item))
		}
		return res
	} else {
		return nil
	}
}

func (inst *JsonWrapper) GetBool64Array(key string) []bool {
	value := inst.GetBool64ArrayOrNil(key)
	return inst.checkMandatoryField(key, value).([]bool)
}

func (inst *JsonWrapper) GetBool64ArrayOrNil(key string) []bool {
	value := inst.getByKey(key)
	if obj, ok := value.([]interface{}); ok {
		res := make([]bool, 0)
		for _, item := range obj {
			res = append(res, ToBoolOrDefault(item))
		}
		return res
	} else {
		return nil
	}
}

func (inst *JsonWrapper) ToJsonString() string {
	if inst.object != nil {
		bytes, _ := json.Marshal(&inst.object)
		return string(bytes)
	} else if inst.array != nil {
		bytes, _ := json.Marshal(&inst.array)
		return string(bytes)
	}
	return ""
}
