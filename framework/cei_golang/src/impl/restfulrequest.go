package impl

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

func (inst *RestfulRequest) SetTarget(target string) {
	inst.target = target
}

func (inst *RestfulRequest) SetPostBody(value string) {
	inst.requestBody = []byte(value)
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

func (inst *RestfulRequest) AddHeaderString(key string, value interface{}) {
	inst.headers[key] = ToStringOrDefault(value)
}

func (inst *RestfulRequest) AddQueryString(key string, value interface{}) {
	item := []string{key, ToStringOrDefault(value)}
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
