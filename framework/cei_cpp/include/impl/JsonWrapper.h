/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   JsonWrapper.h
 * Author: U0151316
 *
 * Created on May 15, 2020, 9:26 PM
 */

#ifndef JSONWRAPPER_H
#define JSONWRAPPER_H

#include <string>
#include <vector>
#include <iostream>
#include "rapidjson/document.h"
#include "rapidjson/stringbuffer.h"
#include "rapidjson/writer.h"
#include "Decimal.h"
#include "Nullable.h"


namespace cei {

    template<typename VALUE_TYPE>
    class JsonValueTo;

    class JsonWrapper {
    public:
        
        static JsonWrapper parseFromString(const std::string& text);

        JsonWrapper() {}
        JsonWrapper(rapidjson::Document&& doc);
        JsonWrapper(const rapidjson::Value& object);
        JsonWrapper(const JsonWrapper& jsonWrapper) = delete;
        JsonWrapper(JsonWrapper&& jsonWrapper);
        JsonWrapper& operator=(JsonWrapper&& jsonWrapper);
        ~JsonWrapper() {}

        bool contains(const std::string& key);

        std::string getString(const std::string& key);
        long getLong(const std::string& key);
        Decimal getDecimal(const std::string& key);
        bool getBoolean(const std::string& key);
        JsonWrapper getObject(const std::string& key);
        JsonWrapper getArray(const std::string& key);

        std::string getStringOrDefault(const std::string& key);
        long getLongOrDefault(const std::string& key);
        Decimal getDecimalOrDefault(const std::string& key);
        bool getBooleanOrDefault(const std::string& key);
        JsonWrapper getObjectOrDefault(const std::string& key);
        JsonWrapper getArrayOrDefault(const std::string& key);

        std::vector<std::string> getStringArray(const std::string& key);
        std::vector<long> getLongArray(const std::string& key);
        std::vector<Decimal> getDecimalArray(const std::string& key);
        std::vector<bool> getBooleanArray(const std::string& key);

        std::vector<std::string> getStringArrayOrEmpty(const std::string& key);
        std::vector<long> getLongArrayOrEmpty(const std::string& key);
        std::vector<Decimal> getDecimalArrayOrEmpty(const std::string& key);
        std::vector<bool> getBooleanArrayOrEmpty(const std::string& key);

        void addJsonString(const std::string& key, const std::string& value);
        void addJsonDecimal(const std::string& key, const Decimal& value);
        void addJsonLong(const std::string& key, long value);
        void addJsonBool(const std::string& key, bool value);
        void addJsonObject(const std::string& key, JsonWrapper& jsonWrapper);

        std::string toJsonString();

    private:
        int getIndexKey(const std::string& key);
        const rapidjson::Value& getByKey(const std::string& key);


        void initializeAsObject();
        void initializeAsArray();
        void addJsonValue(const std::string& key, rapidjson::Value& value);
        JsonWrapper& checkMandatoryObject(const std::string& key, JsonWrapper& value);


    private:
        rapidjson::Document doc_;
        rapidjson::Value object_;
        rapidjson::Value array_;
    };
}

#endif /* JSONWRAPPER_H */

