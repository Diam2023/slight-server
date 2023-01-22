cmake_minimum_required(VERSION 3.5)

project(slight-server VERSION 0.1 LANGUAGES CXX)

set(CMAKE_INCLUDE_CURRENT_DIR ON)

set(CMAKE_AUTOUIC ON)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)

# c++ 17 standard
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_STANDARD 17)

find_package(QT NAMES Qt6 COMPONENTS COMPONENTS Core Widgets LinguistTools REQUIRED)
find_package(Qt${QT_VERSION_MAJOR} COMPONENTS Widgets LinguistTools REQUIRED)

message(QT Version: ${QT_VERSION_MAJOR})

set(SRC_PATH "./src")
set(HEADER_PATH "./include")
set(SOURCE_PATH "./source")

set(QRC_FILES
        ${SOURCE_PATH}/source.qrc
        ${SOURCE_PATH}/style/style.qss
)

set(SRC_FILES
        ${SRC_PATH}/Config.cpp
        ${SRC_PATH}/RuleConfig.cpp
        ${SRC_PATH}/RuleItem.cpp
        ${SRC_PATH}/WebConfig.cpp
        ${SRC_PATH}/SlightServer.cpp
)

set(TS_FILES slight-server_zh_CN.ts)

set(PROJECT_SOURCES
        main.cpp
        mainwindow.cpp
        mainwindow.h
        mainwindow.ui
        ${QRC_FILES}
        ${SRC_FILES}
        ${TS_FILES}
)

aux_source_directory(./src PROJECT_SOURCES)

#启用当前头文件目录
SET(CMAKE_INCLUDE_CURRENT_DIR ON)

INCLUDE_DIRECTORIES(${HEADER_PATH})

# moc file
qt_wrap_cpp(MOC_FILE ${SRC_PATH}/SlightServer.cpp ${HEADER_PATH}/SlightServer.h)


# Specify MSVC UTF-8 encoding
# add_compile_options("$<$<C_COMPILER_ID:MSVC>:/utf-8>")
# add_compile_options("$<$<CXX_COMPILER_ID:MSVC>:/utf-8>")

if(${QT_VERSION_MAJOR} GREATER_EQUAL 6)
    qt_add_executable(slight-server
        WIN32
        MANUAL_FINALIZATION
        ${PROJECT_SOURCES}
        ${MOC_FILE}
    )
# Define target properties for Android with Qt 6 as:
#    set_property(TARGET slight-server APPEND PROPERTY QT_ANDROID_PACKAGE_SOURCE_DIR
#                 ${CMAKE_CURRENT_SOURCE_DIR}/android)
# For more information, see https://doc.qt.io/qt-6/qt-add-executable.html#target-creation

    qt_create_translation(QM_FILES ${CMAKE_SOURCE_DIR} ${TS_FILES})
else()
    if(ANDROID)
        add_library(slight-server SHARED
            ${PROJECT_SOURCES}
        )
# Define properties for Android with Qt 5 after find_package() calls as:
#    set(ANDROID_PACKAGE_SOURCE_DIR "${CMAKE_CURRENT_SOURCE_DIR}/android")
    else()
        add_executable(slight-server
            WIN32
            ${PROJECT_SOURCES}
            ${MOC_FILE}
        )
    endif()

    qt5_create_translation(QM_FILES ${CMAKE_SOURCE_DIR} ${TS_FILES})
endif()

target_link_libraries(slight-server PRIVATE Qt${QT_VERSION_MAJOR}::Widgets)

set_target_properties(slight-server PROPERTIES
    MACOSX_BUNDLE_GUI_IDENTIFIER my.example.com
    MACOSX_BUNDLE_BUNDLE_VERSION ${PROJECT_VERSION}
    MACOSX_BUNDLE_SHORT_VERSION_STRING ${PROJECT_VERSION_MAJOR}.${PROJECT_VERSION_MINOR}
)

if(QT_VERSION_MAJOR EQUAL 6)
    qt_finalize_executable(slight-server)
endif()
