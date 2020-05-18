/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
#include "JsonWrapper.h"
#include <regex>
#include <functional>
#include "rapidjson/stringbuffer.h"
#include "rapidjson/writer.h"
#include "TypeConverter.h"

namespace cei {

    enum Index {
        NULL_INDEX = -2
    };

    template<typename VALUE_TYPE>
    class JsonValueTo {
        typedef std::function<Nullable<VALUE_TYPE>(const std::string&) > CONVERTER;

        VALUE_TYPE defaultValue_;
        CONVERTER converter_;

    public:

        JsonValueTo(CONVERTER fun, VALUE_TYPE defaultValue) : defaultValue_(defaultValue), converter_(fun) {
        }

        const Nullable<VALUE_TYPE> to(const rapidjson::Value& value) const {
            if (!value.IsNull()) {
                return Nullable<VALUE_TYPE>(converter_(value.GetString()));
            }
            return Nullable<VALUE_TYPE>::NULL_ENTITY;
        }

        const Nullable<VALUE_TYPE> toOrDefault(const rapidjson::Value& value) const {
            auto res = to(value);
            if (res.isNull()) {
                const static Nullable<VALUE_TYPE> DEFAULT_VALUE(defaultValue_);
                return DEFAULT_VALUE;
            }
            return res;
        }
    };

    static JsonValueTo<std::string> jsonValueToString(static_cast<Nullable<std::string>(*)(const std::string&)> (&castToString), std::string(""));
    static JsonValueTo<long> jsonValueToLong(castToLong, 0);
    static JsonValueTo<Decimal> jsonValueToDecimal(castToDecimal, Decimal());
    static JsonValueTo<bool> jsonValueToBool(castToBool, false);

    template<typename VALUE_TYPE>
    VALUE_TYPE checkMandatoryField(const std::string& key, const Nullable<VALUE_TYPE>& value) {
        if (!value.isNull()) {
            return value.getValue();
        } else {
            // TODO
        }
    }

    template<typename VALUE_TYPE>
    std::vector<VALUE_TYPE> checkMandatoryArray(const std::string& key, const std::vector<VALUE_TYPE>& value) {
        if (!value.empty()) {
            return std::move(value);
        } else {
            // TODO
        }
    }

    JsonWrapper::JsonWrapper(rapidjson::Document&& doc) : doc_(std::move(doc)) {
        if (doc_.IsObject()) {
            this->object_ = doc_.GetObject();
        } else if (doc_.IsArray()) {
            this->array_ = doc_.GetArray();
        }
    }

    JsonWrapper::JsonWrapper(const rapidjson::Value& object) {
        if (object.IsObject()) {
            this->object_.CopyFrom(object, doc_.GetAllocator());
        } else if (object.IsArray()) {
            this->array_.CopyFrom(object, doc_.GetAllocator());
        }
    }

    JsonWrapper::JsonWrapper(JsonWrapper&& jsonWrapper) {
        doc_ = std::move(jsonWrapper.doc_);
        object_ = std::move(jsonWrapper.object_);
        array_ = std::move(jsonWrapper.array_);
    }

    JsonWrapper& JsonWrapper::operator=(JsonWrapper&& jsonWrapper) {
        doc_ = std::move(jsonWrapper.doc_);
        object_ = std::move(jsonWrapper.object_);
        array_ = std::move(jsonWrapper.array_);
        return *this;
    }

    JsonWrapper& JsonWrapper::checkMandatoryObject(const std::string& key, JsonWrapper& value) {
        if (value.object_.IsNull() && value.array_.IsNull()) {
            // TODO "[Json] Get json object: %s, does not exist"
        } else if (!value.object_.IsNull() && !value.array_.IsNull()) {
            // TODO "[Json] The JsonWrapper is invalid"
        }
        return value;
    }

    JsonWrapper JsonWrapper::parseFromString(const std::string& text) {
        rapidjson::Document doc;
        doc.Parse<rapidjson::kParseNumbersAsStringsFlag>(text.c_str());
        return JsonWrapper(std::move(doc));
    }

    int JsonWrapper::getIndexKey(const std::string& key) {
        if (key == "[]") {
            return -1;
        }
        std::regex re("^\\[[0-9]*\\]$");
        std::smatch res;
        if (std::regex_match(key, res, re)) {
            return std::stoi(res.str().substr(1, res.str().size() - 1));
        }
        return NULL_INDEX;
    }

    const rapidjson::Value& JsonWrapper::getByKey(const std::string& key) {
        static rapidjson::Value nullValue;
        int index = getIndexKey(key);
        if (index == NULL_INDEX) {
            if (!object_.IsNull() && object_.HasMember(key.c_str())) {
                return object_[key.c_str()];
            } else {
                return nullValue;
            }
        } else {
            if (!array_.IsNull() && 0 < index && index < array_.Size()) {
                return array_[index];
            }
        }
        return nullValue;
    }

    template<typename VALUE_TYPE>
    std::vector<VALUE_TYPE> getArrayByKey(const std::string& key, const rapidjson::Value& value, const JsonValueTo<VALUE_TYPE>& converter) {
        std::vector<VALUE_TYPE> res;
        if (!value.IsArray()) {
            return std::move(res);
        }
        auto array = value.GetArray();
        for (auto it = array.Begin(); it != array.End(); it++) {
            res.push_back(converter.toOrDefault(*it).getValue());
        }
        return std::move(res);
    }

    bool JsonWrapper::contains(const std::string& key) {
        int index = getIndexKey(key);
        if (index != NULL_INDEX && !array_.IsNull()) {
            return index < array_.Size() && index > 0;
        } else if (!object_.IsNull()) {
            return object_.HasMember(key.c_str());
        }
        return false;
    }

    std::string JsonWrapper::getStringOrDefault(const std::string& key) {
        const rapidjson::Value& value = getByKey(key);
        return jsonValueToString.toOrDefault(value).getValue();
    }

    std::string JsonWrapper::getString(const std::string& key) {
        const rapidjson::Value& value = getByKey(key);
        return checkMandatoryField(key, jsonValueToString.to(value));
    }

    long JsonWrapper::getLongOrDefault(const std::string& key) {
        const rapidjson::Value& value = getByKey(key);
        return jsonValueToLong.toOrDefault(value).getValue();
    }

    long JsonWrapper::getLong(const std::string& key) {
        const rapidjson::Value& value = getByKey(key);
        return checkMandatoryField(key, jsonValueToLong.to(value));
    }

    Decimal JsonWrapper::getDecimalOrDefault(const std::string& key) {
        const rapidjson::Value& value = getByKey(key);
        return jsonValueToDecimal.toOrDefault(value).getValue();
    }

    Decimal JsonWrapper::getDecimal(const std::string& key) {
        const rapidjson::Value& value = getByKey(key);
        return checkMandatoryField(key, jsonValueToDecimal.to(value));
    }

    bool JsonWrapper::getBooleanOrDefault(const std::string& key) {
        const rapidjson::Value& value = getByKey(key);
        return jsonValueToBool.toOrDefault(value).getValue();
    }

    bool JsonWrapper::getBoolean(const std::string& key) {
        const rapidjson::Value& value = getByKey(key);
        return jsonValueToBool.toOrDefault(value).getValue();
    }

    JsonWrapper JsonWrapper::getObjectOrDefault(const std::string& key) {
        const rapidjson::Value& value = getByKey(key);
        if (value.IsNull()) {
            return std::move(JsonWrapper());
        } else if (value.IsObject()) {
            return std::move(JsonWrapper(value));
        } else {
            // TODO
            return std::move(JsonWrapper());
        }
    }

    JsonWrapper JsonWrapper::getObject(const std::string& key) {
        JsonWrapper&& jsonWrapper = getObjectOrDefault(key);
        return std::move(checkMandatoryObject(key, jsonWrapper));
    }

    JsonWrapper JsonWrapper::getArrayOrDefault(const std::string& key) {
        const rapidjson::Value& value = getByKey(key);
        if (!value.IsNull() && value.IsArray()) {
            return std::move(JsonWrapper(value));
        } else {
            // TODO
            return std::move(JsonWrapper());
        }
    }

    JsonWrapper JsonWrapper::getArray(const std::string& key) {
        JsonWrapper&& jsonWrapper = getArrayOrDefault(key);
        return std::move(checkMandatoryObject(key, jsonWrapper));
    }

    void JsonWrapper::initializeAsObject() {
        if (object_.IsNull() && array_.IsNull()) {
            object_ = rapidjson::Value(rapidjson::kObjectType);
        } else if (!array_ .IsNull()) {
            // TODO  CEILog.showFailure("[Json] Cannot create json object");
        }
    }

    void JsonWrapper::initializeAsArray() {
        if (object_.IsNull() && array_.IsNull()) {
            array_ = rapidjson::Value(rapidjson::kArrayType);
        } else if (!object_ .IsNull()) {
            // TODO  CEILog.showFailure("[Json] Cannot create json array");
        }
    }

    void JsonWrapper::addJsonValue(const std::string& key, rapidjson::Value& value) {
        int index = getIndexKey(key);
        if (index == NULL_INDEX) {
            initializeAsObject();
            rapidjson::Value tmp(rapidjson::kStringType);
            tmp.SetString(key.c_str(), doc_.GetAllocator());
            object_.AddMember(tmp, value, doc_.GetAllocator());
        } else if (index == -1) {
            initializeAsArray();
            array_.PushBack(value, doc_.GetAllocator());
        } else {
            // TODO CEILog.showFailure("[Json] Cannot specify thd detail index to insert a item to array");
        }
    }

    void JsonWrapper::addJsonObject(const std::string& key, JsonWrapper& jsonWrapper) {
        if (!jsonWrapper.object_.IsNull()) {
            addJsonValue(key.c_str(), jsonWrapper.object_);
        } else if (!jsonWrapper.array_.IsNull()) {
            addJsonValue(key.c_str(), jsonWrapper.array_);
        } else {
            // TODO CEILog.showFailure("[Json] Cannot add a null object to json object");
        }
    }

    void JsonWrapper::addJsonString(const std::string& key, const std::string& value) {
        rapidjson::Value tmp;
        tmp.SetString(value.c_str(), doc_.GetAllocator());
        addJsonValue(key, tmp);
    }

    void JsonWrapper::addJsonDecimal(const std::string& key, const Decimal& value) {
        rapidjson::Value tmp;
        tmp.SetDouble(value.toDouble());
        addJsonValue(key, tmp);
    }

    void JsonWrapper::addJsonLong(const std::string& key, long value) {
        rapidjson::Value tmp;
        tmp.SetInt64(static_cast<int64_t> (value));
        addJsonValue(key, tmp);
    }

    void JsonWrapper::addJsonBool(const std::string& key, bool value) {
        rapidjson::Value tmp;
        tmp.SetBool(value);
        addJsonValue(key, tmp);
    }

    std::vector<std::string> JsonWrapper::getStringArray(const std::string& key) {
        std::vector<std::string>&& value = getArrayByKey(key, getByKey(key), jsonValueToString);
        return std::move(checkMandatoryArray(key, value));
    }

    std::vector<long> JsonWrapper::getLongArray(const std::string& key) {
        std::vector<long>&& value = getArrayByKey(key, getByKey(key), jsonValueToLong);
        return std::move(checkMandatoryArray(key, value));
    }

    std::vector<Decimal> JsonWrapper::getDecimalArray(const std::string& key) {
        std::vector<Decimal>&& value = getArrayByKey(key, getByKey(key), jsonValueToDecimal);
        return std::move(checkMandatoryArray(key, value));
    }

    std::vector<bool> JsonWrapper::getBooleanArray(const std::string& key) {
        std::vector<bool>&& value = getArrayByKey(key, getByKey(key), jsonValueToBool);
        return std::move(checkMandatoryArray(key, value));
    }

    std::vector<std::string> JsonWrapper::getStringArrayOrEmpty(const std::string& key) {
        return std::move(getArrayByKey(key, getByKey(key), jsonValueToString));
    }

    std::vector<long> JsonWrapper::getLongArrayOrEmpty(const std::string& key) {
        return std::move(getArrayByKey(key, getByKey(key), jsonValueToLong));
    }

    std::vector<Decimal> JsonWrapper::getDecimalArrayOrEmpty(const std::string& key) {
        return std::move(getArrayByKey(key, getByKey(key), jsonValueToDecimal));
    }

    std::vector<bool> JsonWrapper::getBooleanArrayOrEmpty(const std::string& key) {
        return std::move(getArrayByKey(key, getByKey(key), jsonValueToBool));
    }

    std::string JsonWrapper::toJsonString() {
        rapidjson::StringBuffer buffer;
        rapidjson::Writer<rapidjson::StringBuffer> writer(buffer);
        //doc_.SetObject() = this->object_;
        if (!object_.IsNull()) {
            doc_.SetObject() = object_;
        } else if (!array_.IsNull()) {
            doc_.SetArray() = array_;
        } else {
            return "";
        }
        doc_.Accept(writer);
        return std::string(buffer.GetString());
    }
}