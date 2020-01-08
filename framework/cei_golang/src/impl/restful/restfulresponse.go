package restful

import (
	"io/ioutil"
	"impl/json"
	"net/http"
	"fmt"
)

type RestfulResponse struct {
	response *http.Response
}

func newRestfulResponse(response *http.Response) *RestfulResponse {
	instance := new(RestfulResponse)
	instance.response = response
	return instance
}


func (this *RestfulResponse) GetJson() *json.JsonWrapper {
	var body []byte
	defer this.response.Body.Close()
	body, err := ioutil.ReadAll(this.response.Body)
	if err != nil {
		fmt.Println(err)
		return nil
	}
	json := json.NewJsonWrapper(body)
	return json
}
