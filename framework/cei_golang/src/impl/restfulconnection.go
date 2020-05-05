package impl

import (
	"bytes"
	"fmt"
	"net/http"
)

// Query the restful
func RestfulQuery(request *RestfulRequest) *RestfulResponse {
	var resp *http.Response
	var err error
	if request.GetMethod() == GET {
		resp, err = http.Get(request.GetURL() + request.GetTarget() + request.BuildQueryString())
		if err != nil {
			fmt.Println(err)
			return nil
		}
	} else if request.GetMethod() == POST {
		resp, err = http.Post(request.GetURL() + request.GetTarget() + request.BuildQueryString(), "", bytes.NewReader(request.GetRequestBody()))
		if err != nil {
			fmt.Println(err)
			return nil
		}
	}
	response := newRestfulResponse(resp)
	return response
}