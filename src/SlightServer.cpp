/**
 * @file SlightServer.cpp
 * @author monoliths (monoliths-uni@outlook.com)
 * @brief 
 * @version 1.0
 * @date 2021-10-25
 * 
 * @copyright Copyright (c) 2021
 * 
 */

#include "SlightServer.h"

bool slight_server::core::SlightServer::init()
{
    QDir config(webPath);
    config.cd(tr("config"));

    configPath = config.absoluteFilePath(tr("web-config.json"));
    rulePath = config.absoluteFilePath(tr("response-rule.json"));

    QDir applicationDir = QApplication::applicationDirPath();
    applicationDir.cd(tr("server"));
    JARPath = applicationDir.absoluteFilePath(tr("slight-server-1.0-jar-with-dependencies.jar"));
    applicationDir.cdUp();
    // java dir
    applicationDir.cd(tr("jre1.8.0_202"));
    applicationDir.cd(tr("bin"));
    // java path
    javaPath = applicationDir.absoluteFilePath(tr("java.exe"));

    // jvm argument.
    JVMArgument = "-Dfile.encoding=utf-8";
    argument.append(JVMArgument);
    // jar file.
    argument.append(QString("-jar"));
    argument.append(JARPath);
    // config file.
    argument.append(QString("-c"));
    argument.append(configPath);
    // rule file.
    argument.append(QString("-r"));
    argument.append(rulePath);

    return true;
}

void slight_server::core::SlightServer::run()
{
    init();

    QProcess javaProccess;
    javaProccess.setWorkingDirectory(webPath);
    javaProccess.start(javaPath, argument);

    if (!javaProccess.isOpen())
    {
        javaProccess.errorString();
    }

    while (javaProccess.waitForFinished(100) == false)
    {
        QByteArray output = javaProccess.readAllStandardOutput();
        if (!output.isEmpty())
        {
            qDebug() << output;
            emit outputMessage(QString(output));
        }
    }
}
