package main

import (
	"fmt"
	"impl/restful"
)

type TestClient struct {
	options string
}

func (this *TestClient) GetLastTrade(symbol string) {
	res := new(restful.RestfulRequest)
	res.SetURL("http://127.0.0.1:8081")
	res.SetTarget("/api/v1/lastTrade")
	res.SetMethod(restful.GET)
	res.AddQueryStringByString("symbol", symbol)
	fmt.Println(res.BuildQueryString())
	response := restful.Query(res)
	json := response.GetJson()
	fmt.Println(json.GetString("Symbol"))
}

func main() {
	testClient := new(TestClient)
	testClient.GetLastTrade("ethusdt")

	// j := new(json.JsonWrapper)
	// j.Test()

	// tmp := make([]string, 3)
	// fmt.Println(len(tmp))
	// tmp[0] = "123"
	// fmt.Println(tmp[1])

	// tmp1 := make([]int, 0, 3)
	// tmp1 = append(tmp1, 1)
	// tmp1 = append(tmp1, 2)
	// tmp1 = append(tmp1, 3)
	// tmp1 = append(tmp1, 4)
	// fmt.Println(len(tmp1))
	// fmt.Println(cap(tmp1))



}
