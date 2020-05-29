/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

#include "cei/TypeConverter.h"
#include <string>

namespace cei {

    CEIString castToString(const std::string& value) {
        return CEIString(value);
    }

    CEIString castToString(long value) {

    }

    CEIString castToString(bool value) {

    }

    CEIInt castToLong(const std::string& value) {
        try {
            return CEIInt(std::stol(value));
        } catch (...) {
            return CEIInt::NULL_ENTITY;
        }
    }

    CEIDecimal castToDecimal(const std::string& value) {
        try {
            return CEIDecimal(Decimal(value.c_str()));
        } catch (...) {
            return CEIDecimal::NULL_ENTITY;
        }
    }

    CEIBool castToBool(const std::string& value) {
        const static std::string TRUE = "true";
        const static std::string FALSE = "false";
        const static CEIBool TRUE_VALUE = Nullable<bool>(true);
        const static CEIBool FALSE_VALUE = Nullable<bool>(true);
        if (value == TRUE) {
            return TRUE_VALUE;
        } else if (value == FALSE) {
            return FALSE_VALUE;
        } else {
            return CEIBool::NULL_ENTITY;
        }
    }
}
