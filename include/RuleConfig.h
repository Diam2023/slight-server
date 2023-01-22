/**
 * @file RuleConfig.hpp
 * @author monoliths (monoliths-uni@outlook.com)
 * @brief 
 * @version 1.0
 * @date 2021-10-25
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#ifndef SLIGHT_SERVER_INCLUDE_RULECONFIG_H_
#define SLIGHT_SERVER_INCLUDE_RULECONFIG_H_

#include <iostream>

#include <QJsonObject>
#include <QJsonArray>
#include <QList>

#include "Config.h"
#include "RuleItem.h"

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
        class RuleConfig : public Config
        {
        public:
            /**
             * @brief Construct a new Rule Config object
             * 
             */
            RuleConfig();

            /**
             * @brief Construct a new Rule Config object
             * 
             * @param filePath 
             */
            RuleConfig(QString filePath);

            /**
             * @brief Construct a new Rule Config object
             * 
             */
            RuleConfig(RuleConfig &&) = default;

            /**
             * @brief Construct a new Rule Config object
             * 
             */
            RuleConfig(const RuleConfig &) = default;
            RuleConfig &operator=(RuleConfig &&) = default;
            RuleConfig &operator=(const RuleConfig &) = default;

            /**
             * @brief Destroy the Rule Config object
             * 
             */
            ~RuleConfig();
            
            void initialize(QJsonDocument jsonDocument) override;

            /**
             * @brief to binary array
             * 
             * @return QByteArray 
             */
            QByteArray toJson() override;

            std::shared_ptr<QList<RuleItem>> getRulesData() const { return rulesData; }
            void setRulesData(const std::shared_ptr<QList<RuleItem>> &rulesData_) { rulesData = rulesData_; }

        private:
        
            /**
             * @brief results data.
             * 
             */
            std::shared_ptr<QList<RuleItem>> rulesData;
        };

    }
}

#endif // SLIGHT_SERVER_INCLUDE_RULECONFIG_H_
