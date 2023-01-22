/**
 * @file Config.hpp
 * @author monoliths (monoliths-uni@outlook.com)
 * @brief this is base of the config data.
 * @version 1.0
 * @date 2021-10-31
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
#include <QIODevice>

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
        class Config
        {
        public:
            Config();
            Config(Config &&) = default;
            Config(const Config &) = default;
            Config &operator=(Config &&) = default;
            Config &operator=(const Config &) = default;

            ~Config();

            /**
             * @brief tojson data.
             * 
             * @return QByteArray data.
             */
            virtual QByteArray toJson() = 0;

            virtual void initialize(QJsonDocument jsonDocument) = 0;

            void initializeJsonText(QString jsonText);

            /**
             * @brief write data to file.
             * 
             * @param data 
             * @return true 
             * @return false 
             */
            bool writeBack(QByteArray data);

            /**
             * @brief this function will call toJson function.
             * 
             * @return true successful action
             * @return false error action.
             */
            bool writeBack();

            /**
             * @brief write data to filePath
             * 
             * @param data data
             * @param filePath file path
             * @return true success.
             * @return false err.
             */
            bool writeTo(QByteArray data, QString filePath);

            /**
             * @brief write data to file path
             * 
             * @param filePath json file path
             * @return true 
             * @return false 
             */
            bool writeTo(QString filePath);

            QString getFilePath() const { return filePath; }
            void setFilePath(const QString &filePath_) { filePath = filePath_; }

        protected:
            /**
             * @brief read data and return the QJsonDocument object.
             * 
             * @param filePath config file path.
             * @return QJsonDocument result.
             */
            QJsonDocument readData(QString filePath);

            QJsonDocument readText(QString jsonText);

        private:
            // file path
            QString filePath;
        };
    }
}
