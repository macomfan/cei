#include "utils/rapidjson/document.h"

int main(int argc, char** argv) {
    std::string data = "{\n"
            "  \"status\": \"ok\",\n"
            "  \"ch\": \"market.btcusdt.kline.1day\",\n"
            "  \"ts\": 1550197964381,\n"
            "  \"data\": [\n"
            "    {\n"
            "      \"id\": 1550160000,\n"
            "      \"open\": 3600.770000000000000000,\n"
            "      \"close\": 3602.380000000000000000,\n"
            "      \"low\": 3575.000000000000000000,\n"
            "      \"high\": 3612.190000000000000000,\n"
            "      \"amount\": 4562.766744240224615720,\n"
            "      \"vol\": 16424799.084153959200406053550000000000000000,\n"
            "      \"count\": 28891\n"
            "    },\n"
            "    {\n"
            "      \"id\": 1550073600,\n"
            "      \"open\": 3594.850000000000000000,\n"
            "      \"close\": 3600.790000000000000000,\n"
            "      \"low\": 3570.000000000000000000,\n"
            "      \"high\": 3624.300000000000000000,\n"
            "      \"amount\": 14514.049885396099061006,\n"
            "      \"vol\": 52311364.004324447892964650980000000000000000,\n"
            "      \"count\": 99003\n"
            "    }\n"
            "  ]\n"
            "}";
    
    rapidjson::Document doc;
    rapidjson::Value& object = doc.Parse<rapidjson::kParseNumbersAsStringsFlag>(data.c_str());
    rapidjson::Value& object1 = object["aaa"];
    
    
    if (doc.IsObject()) {
        printf("hello = %s\n", object["status"].GetString());
    }
    rapidjson::Value& array = doc["data"];
    if (array.IsArray()) {
        printf("array");
    }
   //doc["status"].GetArray
}