/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   types.h
 * Author: U0151316
 *
 * Created on May 22, 2020, 8:46 PM
 */

#ifndef CEITYPES_H
#define CEITYPES_H

#include <string>
#include "cei/Nullable.h"
#include "cei/Decimal.h"

namespace cei {
    using CEIInt = Nullable<long>;
    using CEIDecimal = Nullable<Decimal>;
    using CEIBool = Nullable<bool>;

    class CEIString : public Nullable<std::string> {
    public:

        CEIString(const Nullable<std::string>& obj) : Nullable<std::string>(obj) {
        }

        CEIString(const char* value) : Nullable<std::string>(std::string(value)) {
        }
        using Nullable<std::string>::Nullable;
        
        const char* c_str() {
            return value().c_str();
        }
    };
}

#endif /* CEITYPES_H */

