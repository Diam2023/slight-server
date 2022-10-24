/**
 * @file Config.cpp
 * @author monoliths (monoliths-uni@outlook.com)
 * @brief config file.
 * @version 1.0
 * @date 2021-10-31
 * 
 * @copyright Copyright (c) 2021
 * 
 */

#include "Config.h"
#include <QIODevice>

slight_server::config::Config::Config()
{
}

slight_server::config::Config::~Config()
{
}

void slight_server::config::Config::initializeJsonText(QString jsonText) {
    initialize(readText(jsonText));
}

QJsonDocument slight_server::config::Config::readData(QString filePath)
{
    setFilePath(filePath);

    QFile file(filePath);

    // file.open(QIODeviceBase::ReadWrite);

    auto jsonText = file.readAll();

    file.close();

    return readText(jsonText);
}

QJsonDocument slight_server::config::Config::readText(QString jsonText) {
    QJsonParseError jsonParseError;

    QJsonDocument jsonDocument = QJsonDocument::fromJson(jsonText.toUtf8(), &jsonParseError);

    if (jsonDocument.isNull() || jsonParseError.error != QJsonParseError::NoError)
    {
        qDebug() << QString("error of jsonDocument");
    }

    return jsonDocument;
}



bool slight_server::config::Config::writeTo(QByteArray data, QString filePath)
{
    bool err;

    QFile file(filePath);

    // err = file.open(QIODeviceBase::ReadWrite);

    if (err == true) {
        file.write(data);
    }

    file.close();

    return err;
}

bool slight_server::config::Config::writeBack(QByteArray data)
{
    filePath = getFilePath();

    return writeTo(data, filePath);
}

bool slight_server::config::Config::writeBack()
{
    return writeBack(toJson());
}

bool slight_server::config::Config::writeTo(QString filePath) {
    return writeTo(toJson(), filePath);
}

