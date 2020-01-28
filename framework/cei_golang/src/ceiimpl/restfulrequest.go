package ceiimpl

import (
	"ceiimpl/utils"
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
	headers      map[string]string
	queryStrings [][]string
	requestBody  []byte
}

func NewRestfulRequest(options *RestfulOptions) *RestfulRequest {
	instance := new(RestfulRequest)
	return instance
}

func (inst *RestfulRequest) SetTarget(target string, args ...interface{}) {
	inst.target, _ = utils.FillString(target, args)
}

func (inst *RestfulRequest) SetPostBody(json *JsonWrapper) {
	inst.requestBody = json.ToBytes()
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

func (inst *RestfulRequest) GetRequestBody() []byte {
	return inst.requestBody
}

func (inst *RestfulRequest) GetQueryString() [][]string {
	return inst.queryStrings
}

func (inst *RestfulRequest) AddHeader(key string, value interface{}) {
	inst.headers[key] = utils.ToString(value)
}

func (inst *RestfulRequest) AddQueryString(key string, value interface{}) {
	item := []string{key, utils.ToString(value)}
	inst.queryStrings = append(inst.queryStrings, item)
}

func (inst *RestfulRequest) GetHeaders() map[string]string {
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
	for _, value := range inst.queryStrings {
		if result != "" {
			result += "&"
		}
		result += value[0]
		result += "="
		result += value[1]
	}
	return "?" + result
}
