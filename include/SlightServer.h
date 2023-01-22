
/**
 * @file SlightServer.h
 * @author monoliths (monoliths-uni@outlook.com)
 * @brief 
 * @version 1.0
 * @date 2021-10-25
 * 
 * @copyright Copyright (c) 2021
 * 
 */
#ifndef SLIGHT_SERVER_INCLUDE_SLIGHTSERVER_H_
#define SLIGHT_SERVER_INCLUDE_SLIGHTSERVER_H_

#include <QCoreApplication>
#include <QApplication>
#include <QObject>
#include <QString>
#include <QProcess>
#include <QDir>
#include <QThread>
#include <QDebug>

/**
 * @brief slight server namespace a core of slight server
 * 
 */
namespace slight_server
{
    namespace core
    {
        class SlightServer : public QThread
        {
            Q_OBJECT
        private:
            QString configPath;
            QString rulePath;
            QString javaPath;
            QString JARPath;
            QString JVMArgument;
            QStringList argument;
            QString webPath;

        signals:
            void outputMessage(QString message);

        public:
            SlightServer(QWidget *parent = nullptr);

            // Qt6 New Freature
            SlightServer()=delete;
            SlightServer(const SlightServer&)=delete;
            
            SlightServer(QString webPaths)
            {
                setWebPath(webPath);
            }

            ~SlightServer(){};

            // initialize
            bool init();

            QString getConfigPath() const { return configPath; }
            void setConfigPath(const QString &configPath_) { configPath = configPath_; }

            QString getRulePath() const { return rulePath; }
            void setRulePath(const QString &rulePath_) { rulePath = rulePath_; }

            QString getWebPath() const { return webPath; }
            void setWebPath(const QString &webPath_) { webPath = webPath_; }

            QString getJavaPath() const { return javaPath; }
            void setJavaPath(const QString &javaPath_) { javaPath = javaPath_; }

            QString jARPath() const { return JARPath; }
            void setJARPath(const QString &jARPath) { JARPath = jARPath; }

            QString jVMArgument() const { return JVMArgument; }
            void setJVMArgument(const QString &jVMArgument) { JVMArgument = jVMArgument; }

            QStringList getArgument() const { return argument; }
            void setArgument(const QStringList &argument_) { argument = argument_; }

        protected:
            void run();
        };

    }
}


#endif // SLIGHT_SERVER_INCLUDE_SLIGHTSERVER_H_
