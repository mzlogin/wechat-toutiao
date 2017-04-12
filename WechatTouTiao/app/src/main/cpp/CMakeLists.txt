cmake_minimum_required(VERSION 3.4.1)

add_library(jni-encrypt
    SHARED
    main.cpp md5.cpp)

target_link_libraries(jni-encrypt
    android
    log)
