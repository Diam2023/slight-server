#include "MainWindow.h"

#include <QApplication>
#include <QLocale>
#include <QTranslator>
#include <QJsonDocument>
#include <QStandardPaths>
#include <QDir>
#include <QFile>

#include "WebConfig.h"
#include "RuleConfig.h"
#include "SlightServer.h"

using namespace slight_server::config;
using namespace slight_server::core;

int main(int argc, char *argv[])
{
    bool development = true;

    QApplication a(argc, argv);

    QTranslator translator;

    /*****************************************************
     * 
     * load language files
     * 
     *****************************************************/
    const QStringList uiLanguages = QLocale::system().uiLanguages();

    for (const QString &locale : uiLanguages)
    {

        const QString baseName = "slight-server_" + QLocale(locale).name();
        if (translator.load(":/i18n/" + baseName))
        {
            a.installTranslator(&translator);
            break;
        }
    }
    /*****************************************************
     * 
     * load qss files
     * 
     *****************************************************/
    QString qssFile = "";
    QDir dir(QString(":source/style"));
    QFileInfoList fileList = dir.entryInfoList();
    foreach (QFileInfo fileItem, fileList)
    {
        if (fileItem.isFile() && fileItem.suffix() == QString("qss"))
        {
            QFile specifyStylefile(fileItem.absoluteFilePath());
            specifyStylefile.open(QFile::ReadOnly);
            qssFile += QString::fromUtf8(specifyStylefile.readAll());
            specifyStylefile.close();
        }
    }
    qApp->setStyleSheet(qssFile);

    // SlightServer::init(QString(""));
    // QString jreName = "jre1.8.0_202";
    // QString javaName = "java.exe";

    // QString runTimePath = QCoreApplication::applicationDirPath();
    // QDir dir(runTimePath);
    // dir.cdUp(); // TODO
    // dir.cd(jreName);
    // dir.cd(QString("bin"));

    MainWindow w;
    w.show();
    return a.exec();
}
