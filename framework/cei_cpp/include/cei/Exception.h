/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   CEIException.h
 * Author: U0151316
 *
 * Created on May 29, 2020, 9:32 PM
 */

#ifndef CEIEXCEPTION_H
#define CEIEXCEPTION_H

#include <stdexcept>
#include <iostream>
#include <sstream>


namespace cei {

    class Exception : public std::runtime_error {
    public:

        Exception(const std::string& str) : std::runtime_error(str){

        }

    public:

        template <typename ...Args>
        static Exception new_exception(Args... rest) {
            return Exception(message(rest...));
        }

    private:

        static std::string message() {
            static std::string str;
            return str;
        }

        template <typename T, typename ...Args>
        static std::string message(T head, Args... rest) {
            std::stringstream ss;
            ss << head << message(rest...);
            return ss.str();
        }


        //        template<typename ...ARGS>
        //        static Exception new_exception(ARGS... args) {
        //            invoke(args...);
        //        }
        //        
        //        template<typename T>
        //        static void invoke(T t) {
        //            std::cout << t << std::endl;
        //        }

        //        template<typename FIRST, typename ...PACK>
        //        static void cppprintf(FIRST first, PACK... params) {
        //            std::cout << first;
        //            cppprintf(params...);
        //        }
        //
        //        template<typename T>
        //        static void cppprintf(T end) {
        //            std::cout << end << std::endl;
        //        }

    private:

    };
}

#endif /* CEIEXCEPTION_H */

