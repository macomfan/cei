cmake_minimum_required(VERSION 3.0)

#设置编译器
IF (CMAKE_COMPILER_TYPE MATCHES "GCC")
    SET(CMAKE_C_COMPILER "gcc")
    SET(CMAKE_CXX_COMPILER "g++")
ELSEIF (CMAKE_COMPILER_TYPE MATCHES "CLANG")
    SET(CMAKE_C_COMPILER "clang")
    SET(CMAKE_CXX_COMPILER "clang++")
ENDIF ()

SET(CMAKE_C_FLAGS "-Wall -std=c99")
SET(CMAKE_C_FLAGS_DEBUG "-g")
SET(CMAKE_C_FLAGS_MINSIZEREL "-Os -DNDEBUG")
SET(CMAKE_C_FLAGS_RELEASE "-O3 -DNDEBUG")
SET(CMAKE_C_FLAGS_RELWITHDEBINFO "-O2 -g")

SET(CMAKE_CXX_FLAGS "-Wall -std=c++11")
SET(CMAKE_CXX_FLAGS_DEBUG "-g")
SET(CMAKE_CXX_FLAGS_MINSIZEREL "-Os -DNDEBUG")
SET(CMAKE_CXX_FLAGS_RELEASE "-O3 -DNDEBUG")
SET(CMAKE_CXX_FLAGS_RELWITHDEBINFO "-O2 -g")


#判断操作系统
IF (WIN32)
    MESSAGE(STATUS "Now is windows")
ELSEIF (APPLE)
    MESSAGE(STATUS "Now is Apple systens.")
ELSEIF (UNIX)
    MESSAGE(STATUS "Now is UNIX-like OS's. Including aPPLE os x  and CygWin")
ENDIF ()

if (UNIX AND NOT APPLE)
    set(platform x64)
    set(LINUX TRUE)
endif ()

if (APPLE)
endif ()


if (LINUX)
    message(STATUS "当前操作系统: Linux")
endif ()

if (APPLE)
    message(STATUS "当前操作系统: MacOS")
endif ()

MESSAGE(STATUS "operation system is ${CMAKE_SYSTEM}")


PROJECT(cei_cpp)

include_directories("${PROJECT_BINARY_DIR}")

IF (CMAKE_BUILD_TYPE MATCHES "Release")
    SET(CMAKE_BUILD_POSTFIX "Release")
ELSEIF (CMAKE_BUILD_TYPE MATCHES "Debug")
    SET(CMAKE_BUILD_POSTFIX "Debug")
ELSE ()
    MESSAGE(FATAL_ERROR "The CMAKE_BUILD_TYPE only supports Release or Debug")
ENDIF ()


include_directories(./include)
include_directories(/usr/local/include)


IF (ENABLE_UT)
    add_subdirectory(unittest)
ENDIF ()


AUX_SOURCE_DIRECTORY(./src DIR_SRCS)
AUX_SOURCE_DIRECTORY(./src/Utils DIR_SRCS)

ADD_LIBRARY(${PROJECT_NAME} STATIC ${DIR_SRCS})

find_package(Boost 1.70)
find_package(OpenSSL)
find_package(ZLIB)

add_definitions(-std=c++11)

install(TARGETS ${PROJECT_NAME}
        LIBRARY DESTINATION lib
        ARCHIVE DESTINATION lib)

install(DIRECTORY include/ DESTINATION include
        FILES_MATCHING PATTERN "*.h")

install(FILES ${CMAKE_CURRENT_SOURCE_DIR}/cert/cert.pem
        DESTINATION "/etc/huobi_cert/"
        )
