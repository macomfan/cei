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

#include "Nullable.h"
#include "Decimal.h"

namespace cei {

    Nullable<std::string> castToString(const std::string& value);
    
    Nullable<std::string> castToString(long value);

    Nullable<std::string> castToString(bool value);

    Nullable<long> castToLong(const std::string& value);
    
    Nullable<Decimal> castToDecimal(const std::string& value);
    
    Nullable<bool> castToBool(const std::string& value);
}

#endif /* TYPECONVERTER_H */

