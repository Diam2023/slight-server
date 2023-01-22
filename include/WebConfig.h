/**
 * @file WebConfig.hpp
 * @author monoliths (monoliths-uni@outlook.com)
 * @brief This is a web config class file.
 * @version 1.0
 * @date 2021-10-25
 * 
 * @copyright Copyright (c) 2021
 * 
 */

#pragma once

#include <QString>
#include <QFile>
#include <QJsonObject>
#include <QJsonDocument>
#include <QJsonParseError>
#include <QDebug>

#include "Config.h"
// #ifndef Config


// #endif

/**
 * @brief slight server namespace a core of slight server
 * 
 */
namespace slight_server
{
    /**
     * @brief config namespace
     * 
     */
    namespace config
    {
        class WebConfig : public Config
        {
        public:
            WebConfig();

            WebConfig(QString filePath);
            
            WebConfig(WebConfig &&) = default;

            WebConfig(const WebConfig &) = default;
            WebConfig &operator=(WebConfig &&) = default;
            WebConfig &operator=(const WebConfig &) = default;
            ~WebConfig();

            void initialize(QJsonDocument jsonDocument) override;

            /**
             * @brief to QByteArray
             * 
             * @return QByteArray 
             */
            QByteArray toJson() override;

            QString getLocal() const { return local; }
            void setLocal(const QString &local_) { local = local_; }

            int getPort() const { return port; }
            void setPort(int port_) { port = port_; }

            QString getConfigPath() const { return configPath; }
            void setConfigPath(const QString &configPath_) { configPath = configPath_; }

            QString getHome() const { return home; }
            void setHome(const QString &home_) { home = home_; }

            bool getDebug() const { return debug; }
            void setDebug(bool debug_) { debug = debug_; }

            QString getLocalFeild() const { return localFeild; }

            QString getPortFeild() const { return portFeild; }

            QString getConfigPathFeild() const { return configPathFeild; }

            QString getHomeFeild() const { return homeFeild; }

            QString getDebugFeild() const { return debugFeild; }
            
        private:
            /**
             * @brief local feild of the config file.
             * 
             */
            const QString localFeild = "local";

            /**
             * @brief server port of config.
             * 
             */
            const QString portFeild = "port";

            /**
             * @brief rules config file path of the web config file.
             * 
             */
            const QString configPathFeild = "configPath";

            /**
             * @brief home feild of the web server.
             * 
             */
            const QString homeFeild = "home";

            /**
             * @brief debug mode if it is true.
             * 
             */
            const QString debugFeild = "debug";

            /**
             * @brief the local data.
             * 
             */
            QString local;

            /**
             * @brief the port data.
             * 
             */
            int port;

            /**
             * @brief the config path data.
             * 
             */
            QString configPath;

            /**
             * @brief the home data.
             * 
             */
            QString home;

            /**
             * @brief the debug data.
             * 
             */
            bool debug;
        };
    }
}