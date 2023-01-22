/**
 * @file WebConfig.cpp
 * @author monoliths (monoliths-uni@outlook.com)
 * @brief 
 * @version 1.0
 * @date 2021-10-31
 * 
 * @copyright Copyright (c) 2021
 * 
 */

#include "WebConfig.h"

slight_server::config::WebConfig::WebConfig()
{
}

slight_server::config::WebConfig::WebConfig(QString filePath)
{
    initialize(readData(filePath));
}

QByteArray slight_server::config::WebConfig::toJson()
{
    QByteArray result;

    QJsonDocument jsonDocument;

    QJsonObject jsonObject;

    jsonObject.insert(localFeild, local);

    jsonObject.insert(portFeild, port);

    jsonObject.insert(configPathFeild, configPath);

    jsonObject.insert(homeFeild, home);

    jsonObject.insert(debugFeild, debug);

    jsonDocument.setObject(jsonObject);

    result = jsonDocument.toJson(QJsonDocument::Indented);

    return result;
}

slight_server::config::WebConfig::~WebConfig()
{
}

void slight_server::config::WebConfig::initialize(QJsonDocument jsonDocument)
{

    if (jsonDocument.isObject())
    {
        /**
         * @brief json object data.
         * 
         */
        QJsonObject jsonObject = jsonDocument.object();

        if (jsonObject.contains(this->localFeild))
        {
            QJsonValue localValue = jsonObject.value(this->localFeild);
            this->setLocal(localValue.toString());
        }

        /**
         * @brief port value.
         * 
         */
        if (jsonObject.contains(this->portFeild))
        {
            QJsonValue portValue = jsonObject.value(this->portFeild);
            this->setPort(portValue.toInt());
        }

        /**
         * @brief config value.
         * 
         */
        if (jsonObject.contains(this->configPathFeild))
        {
            QJsonValue configPathValue = jsonObject.value(this->configPathFeild);
            this->setConfigPath(configPathValue.toString());
        }

        /**
         * @brief home value.
         * 
         */
        if (jsonObject.contains(this->homeFeild))
        {
            QJsonValue homeValue = jsonObject.value(this->homeFeild);
            this->setHome(homeValue.toString());
        }

        /**
         * @brief debug value.
         * 
         */
        if (jsonObject.contains(this->debugFeild))
        {
            QJsonValue debugValue = jsonObject.value(this->debugFeild);
            this->setDebug(debugValue.toBool());
        }
    }
}
