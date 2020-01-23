package restful

import (
	"strconv"
)

type Method string

const (
	GET  Method = "GET"
	POST Method = "POST"
)

type RestfulRequest struct {
	url          string
	target       string
	method       Method
	headers      []string
	queryStrings []string
}

func NewRestfulRequest(options *RestfulOptions) *RestfulRequest {
	instance := new(RestfulRequest)
	return instance
}

func (inst *RestfulRequest) SetTarget(target string) {
	inst.target = target
}

func (inst *RestfulRequest) GetTarget() string {
	return inst.target
}

func (inst *RestfulRequest) SetMethod(method Method) {
	inst.method = method
}

func (inst *RestfulRequest) GetMethod() Method {
	return inst.method
}

func (inst *RestfulRequest) AddHeader(key string, value interface{}) {
	inst.headers = append(inst.headers, key, toString(value))
}

func (inst *RestfulRequest) AddQueryString(key string, value interface{}) {
	inst.queryStrings = append(inst.queryStrings, key, toString(value))
}

func toString(value interface{}) string {
	switch v := value.(type) {
	case string:
		return string(v)
	case bool:
		return strconv.FormatBool(v)
	case uint8:
		return strconv.FormatUint(uint64(v), 10)
	case uint16:
		return strconv.FormatUint(uint64(v), 10)
	case uint32:
		return strconv.FormatUint(uint64(v), 10)
	case uint64:
		return strconv.FormatUint(uint64(v), 10)
	case uint:
		return strconv.FormatUint(uint64(v), 10)
	case int8:
		return strconv.FormatInt(int64(v), 10)
	case int16:
		return strconv.FormatInt(int64(v), 10)
	case int32:
		return strconv.FormatInt(int64(v), 10)
	case int64:
		return strconv.FormatInt(int64(v), 10)
	case int:
		return strconv.FormatInt(int64(v), 10)
	case float32:
		return strconv.FormatFloat(float64(v), 'f', -1, 64)
	case float64:
		return strconv.FormatFloat(float64(v), 'f', -1, 64)
	default:
		// TODO not supported
		return ""
	}
}

func (inst *RestfulRequest) GetHeaders() []string {
	return inst.headers
}

func (inst *RestfulRequest) SetURL(value string) {
	inst.url = value
}

func (inst *RestfulRequest) GetURL() string {
	return inst.url
}

func (inst *RestfulRequest) BuildQueryString() string {
	result := ""
	for index := 0; index < len(inst.queryStrings); index += 2 {
		if len(result) != 0 {
			result += "&"
		}
		result += inst.queryStrings[index]
		result += "="
		result += inst.queryStrings[index+1]
	}
	return "?" + result
}
