/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   Nullable.h
 * Author: U0151316
 *
 * Created on May 29, 2020, 9:37 AM
 */

#ifndef NULLABLE_H
#define NULLABLE_H

#include <stdexcept>


namespace cei {

    template <typename VALUE_TYPE>
    class Nullable {
    public:
        const static Nullable<VALUE_TYPE> NULL_ENTITY;
        using value_type = VALUE_TYPE;
        
        Nullable() noexcept {

        }

        Nullable(const VALUE_TYPE& value) {
            isNull_ = false;
            value_ = value;
        }

        Nullable(const Nullable<VALUE_TYPE>& obj) {
            isNull_ = obj.isNull_;
            value_ = obj.value_;
        }

        Nullable(Nullable<VALUE_TYPE>&& obj) {
            isNull_ = obj.isNull_;
            value_ = obj.value_;
            obj.isNull_ = true;
            obj.value_ = VALUE_TYPE{};
        }

        Nullable& operator=(const Nullable<VALUE_TYPE>& obj) noexcept {
            isNull_ = obj.isNull_;
            value_ = obj.value_;
            return *this;
        }
        
        bool isNull() const noexcept {
            return isNull_;
        }

        void value(const VALUE_TYPE& value) {
            isNull_ = false;
            value_ = value;
        }

        VALUE_TYPE value() const {
            if (isNull_) {
                throw std::runtime_error("Cannot get null value");
            }
            return value_;
        }

//        const VALUE_TYPE& value() const {
//            if (isNull_) {
//                throw std::runtime_error("Cannot get null value");
//            }
//            return value_;
//        }

        operator VALUE_TYPE() const {
            if (isNull_) {
                throw std::runtime_error("Cannot get null value");
            }
            return value_;
        }

    protected:
        bool isNull_ = true;
        VALUE_TYPE value_;
    };
    
    template <typename VALUE_TYPE>
    const Nullable<VALUE_TYPE> Nullable<VALUE_TYPE>::NULL_ENTITY = Nullable<VALUE_TYPE>{};
}

#endif /* NULLABLE_H */

