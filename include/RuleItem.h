/**
 * @file RuleItem.hpp
 * @author monoliths (monoliths-uni@outlook.com)
 * @brief This is a rule item compose.
 * @version 1.0
 * @date 2021-10-25
 * 
 * @copyright Copyright (c) 2021
 * 
 */

#pragma once
// #ifndef RuleItem

#include <QString>
#include <QJsonObject>

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
          /**
           * @brief This is RuleItem class.
           * 
           */
          class RuleItem
          {
          public:
               /**
                * @brief Construct a new Rule Item object
                * 
                */
               RuleItem();
               
               /**
                * @brief Construct a new Rule Item object
                */
               RuleItem(QJsonObject jsonObject);

               /**
                * @brief Construct a new Rule Item object
                * 
                */
               RuleItem(QString extensionName, bool binary, QString charset, QString head);

               /**
                * @brief Construct a new Rule Item object
                * 
                */
               RuleItem(RuleItem &&) = default;

               /**
                * @brief Construct a new Rule Item object
                * 
                */
               RuleItem(const RuleItem &) = default;

               RuleItem &operator=(RuleItem &&) = default;
               RuleItem &operator=(const RuleItem &) = default;

               /**
                * @brief Destroy the Rule Item object
                * 
                */
               ~RuleItem();

               /**
                * @brief toJson function
                * 
                */
               QJsonObject toJsonObject();

               QString getExtensionName() const { return extensionName; }
               void setExtensionName(const QString &extensionName_) { extensionName = extensionName_; }

               bool getBinary() const { return binary; }
               void setBinary(bool binary_) { binary = binary_; }

               QString getCharset() const { return charset; }
               void setCharset(const QString &charset_) { charset = charset_; }

               QString getHead() const { return head; }
               void setHead(const QString &head_) { head = head_; }

          private:
               QString extensionNameFeild = "extensionName";
               QString binaryFeild = "binary";
               QString charsetFeild = "charset";
               QString headFeild = "head";

               /**
                * @brief extension name.
                * 
                */
               QString extensionName;

               /**
                * @brief binary mode.
                * 
                */
               bool binary;

               /**
                * @brief charset of response.
                * 
                */
               QString charset;

               /**
                * @brief head of response.
                * 
                */
               QString head;
          };

     } // namespace config

} // namespace slight_server

// #endif // !RuleItem