/**
 * @file RuleConfig.cpp
 * @author monoliths (monoliths-uni@outlook.com)
 * @brief 
 * @version 1.0
 * @date 2021-10-25
 * 
 * @copyright Copyright (c) 2021
 * 
 */

#include "RuleConfig.h"

slight_server::config::RuleConfig::RuleConfig()
{
}

slight_server::config::RuleConfig::~RuleConfig()
{
}

void slight_server::config::RuleConfig::initialize(QJsonDocument jsonDocument) {
    if (jsonDocument.isArray())
    {
        QJsonArray jsonArray = jsonDocument.array();

        for (auto item : jsonArray)
        {
            if (item.isObject())
            {
                auto object = item.toObject();

                RuleItem ruleItem(object);

                rulesData->append(ruleItem);
            }
        }
    }
}

QByteArray slight_server::config::RuleConfig::toJson()
{
    QByteArray result;

    QJsonDocument jsonDocument;

    QJsonArray jsonArray;

    for (auto rule : *rulesData)
    {
        jsonArray.append(QJsonValue(rule.toJsonObject()));
    }

    jsonDocument.setArray(jsonArray);

    result = jsonDocument.toJson(QJsonDocument::Indented);

    return result;
}

slight_server::config::RuleConfig::RuleConfig(QString filePath)
{
    rulesData = std::make_shared<QList<RuleItem>>();

    QJsonDocument jsonDocument = this->readData(filePath);
    
    initialize(jsonDocument);
}
