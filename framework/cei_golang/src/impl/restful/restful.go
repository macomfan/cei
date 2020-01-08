package restful

import (
	"fmt"
	"net/http"
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

func (this *RestfulRequest) SetTarget(target string) {
	this.target = target
}

func (this *RestfulRequest) GetTarget() string {
	return this.target
}

func (this *RestfulRequest) SetMethod(method Method) {
	this.method = method
}

func (this *RestfulRequest) GetMethod() Method {
	return this.method
}

func (this *RestfulRequest) AddHeader(key string, value interface{}) {
	this.headers = append(this.headers, key, toString(value))
}

func (this *RestfulRequest) AddQueryString(key string, value interface{}) {
	this.queryStrings = append(this.queryStrings, key, toString(value))
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

func (this *RestfulRequest) GetHeaders() []string {
	return this.headers
}

func (this *RestfulRequest) SetURL(value string) {
	this.url = value
}

func (this *RestfulRequest) GetURL() string {
	return this.url
}

func (this *RestfulRequest) BuildQueryString() string {
	result := ""
	for index := 0; index < len(this.queryStrings); index += 2 {
		if len(result) != 0 {
			result += "&"
		}
		result += this.queryStrings[index]
		result += "="
		result += this.queryStrings[index+1]
	}
	return "?" + result
}

func Query(request *RestfulRequest) *RestfulResponse {
	var resp *http.Response
	var err error
	if request.GetMethod() == GET {
		resp, err = http.Get(request.GetURL() + request.GetTarget() + request.BuildQueryString())
		if err != nil {
			fmt.Println(err)
			return nil
		}
	} else if request.GetMethod() == POST {
		fmt.Println("POST")
	}
	response := newRestfulResponse(resp)
	return response
}
