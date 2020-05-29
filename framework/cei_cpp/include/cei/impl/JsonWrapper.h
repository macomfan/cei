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
#include "cei/Types.h"


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

        CEIString getString(const std::string& key);
        CEIInt getInt(const std::string& key);
        CEIDecimal getDecimal(const std::string& key);
        CEIBool getBoolean(const std::string& key);
        JsonWrapper getObject(const std::string& key);
        JsonWrapper getArray(const std::string& key);

        CEIString getStringOrNull(const std::string& key);
        CEIInt getIntOrNull(const std::string& key);
        CEIDecimal getDecimalOrNull(const std::string& key);
        CEIBool getBooleanOrNull(const std::string& key);
        JsonWrapper getObjectOrDefault(const std::string& key);
        JsonWrapper getArrayOrDefault(const std::string& key);

        std::vector<CEIString> getStringArray(const std::string& key);
        std::vector<CEIInt> getIntArray(const std::string& key);
        std::vector<CEIDecimal> getDecimalArray(const std::string& key);
        std::vector<CEIBool> getBooleanArray(const std::string& key);

        std::vector<CEIString> getStringArrayOrEmpty(const std::string& key);
        std::vector<CEIInt> getIntArrayOrEmpty(const std::string& key);
        std::vector<CEIDecimal> getDecimalArrayOrEmpty(const std::string& key);
        std::vector<CEIBool> getBooleanArrayOrEmpty(const std::string& key);

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

