#pragma once

#include <string>
#include "impl/decimal.h"


namespace debug {
    struct SimpleInfo {
        std::string name;
        long number;
        Decimal price;
        bool status;
    };

    class WSClient {
    };

