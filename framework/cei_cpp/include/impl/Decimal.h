/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   Decimal.h
 * Author: U0151316
 *
 * Created on May 18, 2020, 12:02 PM
 */

#ifndef DECIMAL_H
#define DECIMAL_H

#include <boost/multiprecision/cpp_dec_float.hpp>

namespace cei {

    class Decimal {
    public:

        Decimal() {
        }

        explicit Decimal(double value) : data_(boost::multiprecision::cpp_dec_float_50(std::to_string(value))) {
        }

        explicit Decimal(const char* value) : data_(boost::multiprecision::cpp_dec_float_50(value)) {
        }

        explicit Decimal(int value) : data_(value) {
        }

        Decimal(const Decimal& obj) : data_(obj.data_) {
        }

        Decimal operator+(const Decimal& obj) {
            boost::multiprecision::cpp_dec_float_50 tmp = data_;
            tmp += obj.data_;
            return Decimal(tmp);
        }

        Decimal operator-(const Decimal& obj) {
            boost::multiprecision::cpp_dec_float_50 tmp = data_;
            tmp -= obj.data_;
            return Decimal(tmp);
        }

        Decimal operator*(const Decimal& obj) {
            boost::multiprecision::cpp_dec_float_50 tmp = data_;
            tmp *= obj.data_;
            return Decimal(tmp);
        }

        Decimal operator/(const Decimal& obj) {
            boost::multiprecision::cpp_dec_float_50 tmp = data_;
            tmp /= obj.data_;
            return Decimal(tmp);
        }

        Decimal& operator+=(const Decimal& obj) {
            data_ += obj.data_;
            return *this;
        }

        Decimal& operator-=(const Decimal& obj) {
            data_ -= obj.data_;
            return *this;
        }

        Decimal& operator*=(const Decimal& obj) {
            data_ *= obj.data_;
            return *this;
        }

        Decimal& operator/=(const Decimal& obj) {
            data_ /= obj.data_;
            return *this;
        }

        Decimal operator%(const Decimal& obj) {
            boost::multiprecision::cpp_dec_float_50 tmp = boost::multiprecision::fmod(data_, obj.data_);
            return Decimal(tmp);
        }

        Decimal& operator=(const Decimal& obj) {
            data_ = obj.data_;
            return *this;
        }

        bool operator==(const Decimal& obj) const {
            return data_ == obj.data_;
        }

        bool operator!=(const Decimal& obj) const {
            return !(this->operator==(obj));
        }

        bool isZero() {
            return data_.is_zero();
        }

        bool operator<(const Decimal & obj) const {
            return data_ < obj.data_;
        }

        bool operator<=(const Decimal & obj) const {
            return data_ <= obj.data_;
        }

        bool operator>(const Decimal & obj) const {
            return data_ > obj.data_;
        }

        bool operator>=(const Decimal & obj) const {
            return data_ >= obj.data_;
        }

        Decimal abs(const Decimal & obj) const {
            boost::multiprecision::cpp_dec_float_50 tmp = boost::multiprecision::abs(data_);
            return Decimal(tmp);
        }

        double toDouble() const {
            return data_.convert_to<double>();
        }

        std::string toString() const {
            //            static std::stringstream ss;
            //            ss.clear();
            //            ss.precision(std::numeric_limits<boost::multiprecision::cpp_dec_float_50>::digits10);
            //            ss << data_;
            //            return ss.str();
            return data_.convert_to<std::string>();
        }

        static Decimal NaN() {
            static Decimal NaN = Decimal(1) / Decimal(0);
            return NaN;
        }

    private:

        Decimal(const boost::multiprecision::cpp_dec_float_50& value) : data_(value) {
        }

        //        Decimal(decDouble * value) : data(*value) {
        //        }

        boost::multiprecision::cpp_dec_float_50 data_;
    };

    std::ostream & operator<<(std::ostream &out, const Decimal& obj);

    std::istream & operator>>(std::istream &in, Decimal& obj);
}


#endif /* DECIMAL_H */

