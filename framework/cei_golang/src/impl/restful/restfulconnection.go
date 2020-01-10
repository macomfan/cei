package restful

import (
	"net/http"
	"fmt"
)

// Query the resful
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