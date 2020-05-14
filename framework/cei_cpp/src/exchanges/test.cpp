#include "exchanges/test.hpp"
namespace test {
    SimpleInfo GetClient::getSimpleInfo() {
        RestfulRequest request{this->option};
        request.setTarget("/restful/get/simpleInfo");
        request.setMethod(RestfulRequest.Method.GET);
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        SimpleInfo simpleInfoVar{};
        simpleInfoVar.name = rootObj.getString("Name");
        simpleInfoVar.number = rootObj.getLong("Number");
        simpleInfoVar.price = rootObj.getDecimal("Price");
        simpleInfoVar.status = rootObj.getBoolean("Status");
    }
    ModelInfo GetClient::getModelInfo() {
        RestfulRequest request{this->option};
        request.setTarget("/restful/get/modelInfo");
        request.setMethod(RestfulRequest.Method.GET);
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        ModelInfo modelInfoVar{};
        modelInfoVar.name = rootObj.getString("Name");
        JsonWrapper obj = rootObj.getObject("DataL1");
        JsonWrapper obj0 = obj.getObject("DataL2");
        ModelValue modelValueVar{};
        JsonWrapper obj1 = obj0.getObject("Value");
        modelValueVar.name = obj1.getString("Name");
        modelValueVar.value = obj1.getLong("Value");
        modelInfoVar.value = modelValueVar;
    }
    PriceList GetClient::getPriceList() {
        RestfulRequest request{this->option};
        request.setTarget("/restful/get/priceList");
        request.setMethod(RestfulRequest.Method.GET);
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        PriceList priceListVar{};
        priceListVar.name = rootObj.getString("Name");
        JsonWrapper obj = rootObj.getArray("Prices");
        for (item : obj.Array()) {
            PriceEntity priceEntityVar{};
            priceEntityVar.price = item.getDecimal("[0]");
            priceEntityVar.volume = item.getDecimal("[1]");
            priceListVar.prices = priceListVar.prices.push_back(priceEntityVar);
        }
    }
    InfoList GetClient::getInfoList() {
        RestfulRequest request{this->option};
        request.setTarget("/restful/get/infoList");
        request.setMethod(RestfulRequest.Method.GET);
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        InfoList infoListVar{};
        infoListVar.name = rootObj.getString("Name");
        JsonWrapper obj = rootObj.getArray("Values");
        for (item : obj.Array()) {
            InfoEntity infoEntityVar{};
            infoEntityVar.name = item.getString("Name");
            infoEntityVar.infoValue = item.getString("InfoValue");
            infoListVar.values = infoListVar.values.push_back(infoEntityVar);
        }
    }
    SimpleInfo GetClient::getTestArray() {
        RestfulRequest request{this->option};
        request.setTarget("/restful/get/testArray");
        request.setMethod(RestfulRequest.Method.GET);
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        SimpleInfo simpleInfoVar{};
        simpleInfoVar.name = rootObj.getString("[0]");
        simpleInfoVar.number = rootObj.getLong("[1]");
        simpleInfoVar.price = rootObj.getDecimal("[2]");
        simpleInfoVar.status = rootObj.getBoolean("[3]");
    }
    SimpleInfo GetClient::inputsByGet(const std::string& name, const long& number, const Decimal& price, const bool& status) {
        RestfulRequest request{this->option};
        request.setTarget("/restful/get/inputsByGet");
        request.setMethod(RestfulRequest.Method.GET);
        request.addQueryString("Name", name);
        request.addQueryString("Number", Long.toString(number));
        request.addQueryString("Price", Long.toString(price));
        request.addQueryString("Status", Long.toString(status));
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        SimpleInfo simpleInfoVar{};
        simpleInfoVar.name = rootObj.getString("Name");
        simpleInfoVar.number = rootObj.getLong("Number");
        simpleInfoVar.price = rootObj.getDecimal("Price");
        simpleInfoVar.status = rootObj.getBoolean("Status");
    }
    std::string GetClient::url(const std::string& input) {
        RestfulRequest request{this->option};
        request.setTarget(CEIUtils.stringReplace("/restful/get/url/%s", input));
        request.setMethod(RestfulRequest.Method.GET);
        RestfulResponse response = request.query();
    }

    SimpleInfo PostClient::postInputs(const std::string& thisU, const Decimal& price, const long& number, const bool& status) {
        RestfulRequest request{this->option};
        request.setTarget("/restful/post/echo");
        request.setMethod(RestfulRequest.Method.POST);
        request.setPostBody(postMsg.toJsonString());
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        SimpleInfo simpleInfoVar{};
        simpleInfoVar.name = rootObj.getString("Name");
        simpleInfoVar.number = rootObj.getLong("Number");
        simpleInfoVar.price = rootObj.getDecimal("Price");
        simpleInfoVar.status = rootObj.getBoolean("Status_1");
    }
    SimpleInfo PostClient::authentication(const std::string& name, const long& number) {
        RestfulRequest request{this->option};
        request.setTarget("/restful/post/authentication");
        request.setMethod(RestfulRequest.Method.POST);
        request.addQueryString("Name", name);
        request.setPostBody(postMsg.toJsonString());
        RestfulResponse response = request.query();
        JsonWrapper rootObj = JsonWrapper.parseFromString(response.getString());
        SimpleInfo simpleInfoVar{};
        simpleInfoVar.name = rootObj.getString("Name");
        simpleInfoVar.number = rootObj.getLong("Number");
        simpleInfoVar.price = rootObj.getDecimal("Price");
    }


}
}
