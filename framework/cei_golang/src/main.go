package main

import (
	"encoding/json"
	"fmt"
	"impl/jsonwrapper"
	//"impl/restful"
	//"strconv"
	"exchanges/testws"
)

func JsonToMapDemo() {
	jsonStr := `
	{
			"name": "jqw",
			"age": 18,
			"data": [
				{"aaa": "aaa"}
			]
	}
	`
	var mapResult map[string]interface{}
	err := json.Unmarshal([]byte(jsonStr), &mapResult)
	if err != nil {
		fmt.Println("JsonToMapDemo err: ", err)
	}
	//fmt.Println(mapResult)
}

func main() {

	testws := testws.NewTestWS()
	testws.Start()
	testws.Send()

	JsonToMapDemo()
	jsonStr := `
	{
			"name": "jqw",
			"age": 18,
			"data": {"aaa": "aaa"},
			"object_array": [{"d":1}, {"d":2}],
			"array":["1","2"]
			
	}`
	json := jsonwrapper.ParseFromString(jsonStr)
	fmt.Println(json.GetInt64("age"))
	data := json.GetObject("data")
	fmt.Println(data.GetString("aaa"))
	obj := json.GetObjectArray("object_array")
	fmt.Println(obj[0].GetInt64("d"))
	strarr := json.GetStringArray("array")
	fmt.Println(strarr[1])
	// testClient := cei.NewTestClient(nil)
	// timestamp := testClient.GetTimestamp()
	// fmt.Println(timestamp.Ts)

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
