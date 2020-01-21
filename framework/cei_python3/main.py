from impl.restfulrequest import RestfulRequest
from impl.restfulconnection import RestfulConnection
from impl.signaturetool import SignatureTool
from exchanges.cei import *

import requests
import asyncio

def place_order(account_id, price):
    print(account_id + price)


async def call(name):
    print("call ", name)
    await asyncio.sleep(2)
    print("complate ", name)


async def my_task(name):
    print("start ", name)
    await asyncio.sleep(2)
    return name + " done"


async def main():
    # await my_task("t1")
    # await my_task("t2")
    # await my_task("t3")
    await asyncio.gather(
        asyncio.create_task(my_task("t1")),
        asyncio.create_task(my_task("t2")),
        asyncio.create_task(my_task("t3"))
    )

if __name__ == '__main__':

    sites = ["Baidu", "Google", "Runoob", "Taobao"]
    for site in sites:
        a = 20
        if site == "Runoob":
            print("菜鸟教程!")
            break
        print("循环数据 " + site)
    else:
        print("没有循环数据!")
    print("完成循环!")
    print(a)
    for site in sites:
        a = 10
        if site == "Runoob":
            print("菜鸟教程!")
            break
        print("循环数据 " + site)
    else:
        print("没有循环数据!")
    print("完成循环!")
    print(a)

    # asyncio.run(main())
    # place_order(None, None)
    # lll = list()
    # lll.append()
    # SignatureTool.Constant.ASC = "ccc"
    #
    #
    # request = RestfulRequest()
    # request.set_method(RestfulRequest.Method.GET)
    # response = RestfulConnection.query(request)
    # json_wrapper = response.get_json()
    # exchange_name = json_wrapper.get_string("exchangeName")
    #
    # request.add_query_string("aaa", 1)
    # request.add_query_string("bbb", 2)
    # request.add_query_string("ccc", 3)
    # print(request.build_query_string())
    # #RestfulConnection.query(request)
    # client = Test.MarketClient()
    # options = RestfulOptions()
    # options.api_key = "ABC"
    # options.secret_key = "abc"
    # testClient = TestClient(options)
    # timestamp = testClient.get_timestamp()
    # hClient = HuobiProClient()
    # bClient = BinanceClient()
    #
    # print('Test ts: ' + str(timestamp.ts))
    # print('H ts: ' + str(hClient.get_timestamp().ts))
    # print('B ts: ' + str(bClient.get_timestamp().ts))
    pass
