#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <iostream>
#include <QMainWindow>
#include <qdebug.h>
#include <QStandardItemModel>
#include <QFileDialog>
#include <QFileSystemWatcher>
#include <QDir>
#include <QMessageBox>
#include <QCoreApplication>

#include "WebConfig.h"
#include "RuleConfig.h"
#include "RuleItem.h"
#include "SlightServer.h"

using namespace slight_server::config;
using namespace slight_server::core;

QT_BEGIN_NAMESPACE
namespace Ui
{
    class MainWindow;
}
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

    QString getWebPath() const { return webPath; }
    void setWebPath(const QString &webPath_) { webPath = webPath_; }

private:
    Ui::MainWindow *ui;

    // config paneles.
    QStandardItemModel *panelModel;

    // web root path
    QString webPath;

    // server static
    bool serverStatus;

    SlightServer *slightServer;

    // web config
    std::unique_ptr<slight_server::config::WebConfig> webConfig;
    // rule config
    std::unique_ptr<slight_server::config::RuleConfig> ruleConfig;

    void init();

    void initConnect();

    void loadWebConfigFiles(QString path);

private slots:
    void output(QString message);
    
    void chooseWebPath();

    void switchMainPanel(QModelIndex index);

    void UIFlushToData();

    void JSONFlushToData();

    void flushToUI();

    void serverAction();
};
#endif // MAINWINDOW_H
