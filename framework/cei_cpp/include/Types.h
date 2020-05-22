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

#ifndef TYPES_H
#define TYPES_H

#include <string>
#include "Nullable.h"
#include "Decimal.h"
#include "Number.h"

namespace cei {
    using CEIInt = Nullable<Number>;
    using CEIDecimal = Nullable<Decimal>;
    using CEIBool = Nullable<bool>;
    using CEIString = Nullable<std::string>;
}

#endif /* TYPES_H */

