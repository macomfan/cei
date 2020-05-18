/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

#include "TypeConverter.h"

namespace cei {

    Nullable<std::string> castToString(const std::string& value) {
        return Nullable<std::string>(value);
    }

    Nullable<std::string> castToString(long value) {

    }

    Nullable<std::string> castToString(bool value) {

    }

    Nullable<long> castToLong(const std::string& value) {

    }
    
    Nullable<Decimal> castToDecimal(const std::string& value) {
        try {
            return Nullable<Decimal>(Decimal(value.c_str()));
        } catch (...) {
            return Nullable<Decimal>::NULL_ENTITY;
        }
    }
    
    Nullable<bool> castToBool(const std::string& value) {
        const static std::string TRUE = "true";
        const static std::string FALSE = "false";
        const static Nullable<bool> TRUE_VALUE = Nullable<bool>(true);
        const static Nullable<bool> FALSE_VALUE = Nullable<bool>(true);
        if (value == TRUE) {
            return TRUE_VALUE;
        } else if (value == FALSE) {
            return FALSE_VALUE;
        } else {
            return Nullable<bool>::NULL_ENTITY;
        }
    }
}
