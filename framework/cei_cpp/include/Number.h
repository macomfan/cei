/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   Number.h
 * Author: U0151316
 *
 * Created on May 22, 2020, 8:41 PM
 */

#ifndef NUMBER_H
#define NUMBER_H

#include <string>
#include "Nullable.h"

namespace cei {

    class Number {
    public:
        using VALUE_TYPE = long;

        Number(VALUE_TYPE value) : value_(value) {
        }

        Number(const Number& number) : value_(number.value_) {
        }

        Number(const char* value) {
            this->Number::Number(std::string(value));
        }

        Number(const std::string& value) {
            value_ = std::stol(value);
        }

        Number& operator=(const Number& number) {
            value_ = number.value_;
        }

        Number& operator=(VALUE_TYPE value) {
            value_ = value;
        }

        Number operator+(const Number& obj) {
            return Number(value_ + obj.value_);
        }

        Number operator-(const Number& obj) {
            return Number(value_ - obj.value_);
        }

        Number operator*(const Number& obj) {
            return Number(value_ * obj.value_);
        }

        Number operator/(const Number& obj) {
            return Number(value_ / obj.value_);
        }

        Number& operator+=(const Number& obj) {
            value_ += obj.value_;
            return *this;
        }

        Number& operator-=(const Number& obj) {
            value_ -= obj.value_;
            return *this;
        }

        Number& operator*=(const Number& obj) {
            value_ *= obj.value_;
            return *this;
        }

        Number& operator/=(const Number& obj) {
            value_ /= obj.value_;
            return *this;
        }

        Number operator%(const Number& obj) {
            return Number(value_ % obj.value_);
        }

        Number& operator=(const Number& obj) {
            value_ = obj.value_;
            return *this;
        }

        bool operator==(const Number& obj) const {
            return value_ == obj.value_;
        }

        bool operator!=(const Number& obj) const {
            return !(this->operator==(obj));
        }

        bool operator<(const Number & obj) const {
            return value_ < obj.value_;
        }

        bool operator<=(const Number & obj) const {
            return value_ <= obj.value_;
        }

        bool operator>(const Number & obj) const {
            return value_ > obj.value_;
        }

        bool operator>=(const Number & obj) const {
            return value_ >= obj.value_;
        }

        Number abs(const Number & obj) const {
            return Number(abs(obj.value_));
        }

    private:
        VALUE_TYPE value_;
    };


}
#endif /* NUMBER_H */

