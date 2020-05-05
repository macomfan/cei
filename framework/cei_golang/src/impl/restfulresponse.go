package impl

import (
	"fmt"
	"io/ioutil"
	"net/http"
)

type RestfulResponse struct {
	response *http.Response
}

func newRestfulResponse(response *http.Response) *RestfulResponse {
	instance := new(RestfulResponse)
	instance.response = response
	return instance
}


func (inst *RestfulResponse) GetString() string {
	var body []byte
	defer inst.response.Body.Close()
	body, err := ioutil.ReadAll(inst.response.Body)
	if err != nil {
		fmt.Println(err)
		return ""
	}
	json := ParseFromString(string(body))
	return ""
}
