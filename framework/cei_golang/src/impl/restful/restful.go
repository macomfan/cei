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

func NewRestfulRequest() *RestfulRequest {
	instance := new(RestfulRequest)
	instance.queryStrings = make([]string, 10)
	instance.headers = make([]string, 10)
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

func (this *RestfulRequest) AddHeader(key string, value string) {
	this.headers = append(this.headers, key, value)
}

func (this *RestfulRequest) AddQueryStringByString(key string, value string) {
	this.queryStrings = append(this.queryStrings, key, value)
}

func (this *RestfulRequest) AddQueryStringByInt(key string, value int) {
	this.queryStrings = append(this.queryStrings, key, strconv.Itoa(value))
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
