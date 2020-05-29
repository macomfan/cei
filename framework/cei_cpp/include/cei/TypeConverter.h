/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   TypeConverter.h
 * Author: Ma
 *
 * Created on 2020年5月18日, 上午6:49
 */

#ifndef TYPECONVERTER_H
#define TYPECONVERTER_H

#include <string>

#include "cei/Types.h"

namespace cei {

    CEIString castToString(const std::string& value);
    
    CEIString castToString(long value);

    CEIString castToString(bool value);

    CEIInt castToLong(const std::string& value);
    
    CEIDecimal castToDecimal(const std::string& value);
    
    CEIBool castToBool(const std::string& value);
}

#endif /* TYPECONVERTER_H */

