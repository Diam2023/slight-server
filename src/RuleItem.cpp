/**
 * @file RuleItem.cpp
 * @author monoliths (monoliths-uni@outlook.com)
 * @brief 
 * @version 1.0
 * @date 2021-10-25
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#include "RuleItem.h"

slight_server::config::RuleItem::~RuleItem()
{
}

QJsonObject slight_server::config::RuleItem::toJsonObject()
{
    QJsonObject result;

    result.insert(this->extensionNameFeild, QJsonValue(getExtensionName()));

    result.insert(this->binaryFeild, QJsonValue(getBinary()));

    if (!getCharset().isEmpty())
    {
        result.insert(this->charsetFeild, QJsonValue(getCharset()));
    }
    else
    {
        result.insert(this->charsetFeild, QJsonValue());
    }

    result.insert(this->headFeild, QJsonValue(getHead()));

    return result;
}

slight_server::config::RuleItem::RuleItem(QJsonObject jsonObject)
{
    if (!jsonObject.isEmpty())
    {
        if (jsonObject.contains(this->extensionNameFeild))
        {
            QJsonValue extensionNameValue = jsonObject.value(this->extensionNameFeild);
            this->setExtensionName(extensionNameValue.toString());
        }

        if (jsonObject.contains(this->binaryFeild))
        {
            QJsonValue binaryValue = jsonObject.value(this->binaryFeild);
            this->setBinary(binaryValue.toBool());
        }

        if (jsonObject.contains(this->charsetFeild))
        {
            QJsonValue charsetValue = jsonObject.value(this->charsetFeild);
            this->setCharset(charsetValue.toString());
        }

        if (jsonObject.contains(this->headFeild))
        {
            QJsonValue headValue = jsonObject.value(this->headFeild);
            this->setHead(headValue.toString());
        }
    }
}

slight_server::config::RuleItem::RuleItem(QString extensionName, bool binary, QString charset, QString head)
{
    this->extensionName = extensionName;
    this->binary = binary;
    this->charset = charset;
    this->head = head;
}
