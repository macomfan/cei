package main

import (
	"fmt"
	"impl/restful"
	//"strconv"
	//"exchanges"
)

func TTT(a, b interface{}) []string {
	fmt.Println(a)
	fmt.Println(b)
	var strlist []string
	strlist = append(strlist, "AAA")
	return strlist
}

func main() {
	for index, value := range TTT(3.141, "aaa") {
		fmt.Printf("index: %d value: %s\n", index, value)
	}
	strlist := TTT(3.141, "aaa")
	for index := 0; index < len(strlist); index += 1 {
		fmt.Printf("value: %s\n", strlist[index])
	}

	options := new(restful.RestfulOptions)
	request := restful.NewRestfulRequest(options)
	request.AddQueryString("1", "A")
	request.AddQueryString("2", "B")
	request.AddQueryString("3", 12345.67890)
	fmt.Println(request.BuildQueryString())
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
