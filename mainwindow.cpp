#include "mainwindow.h"
#include "./ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent), ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    init();

    initConnect();
}

void MainWindow::init()
{
    slightServer = new SlightServer(tr(""));
    serverStatus = false;

    this->setWindowTitle(tr("SLIGHT SERVER"));

    ui->stackedWidget->setCurrentIndex(0); // select main panel

    panelModel = new QStandardItemModel();

    QStringList panelList;
    panelList.append(tr("MAIN")); // main panel

    panelList.append(tr("SERVER CONFIG")); // server config

    panelList.append(tr("RULE CONFIG")); // rule config

    for (int i = 0; i < panelList.size(); i++)
    {
        QString string = static_cast<QString>(panelList.at(i));
        QStandardItem *item = new QStandardItem(string);
        panelModel->appendRow(item);
    }

    ui->listView->setModel(panelModel);

    auto defaultLine = panelModel->index(0, 0); // 0 row 0 column.

    ui->listView->setCurrentIndex(defaultLine); // show main panel.

    ui->listView->setEditTriggers(QAbstractItemView::NoEditTriggers);

    ui->serverConfigGroup->setTabText(0, tr("EDIT UI"));
    ui->serverConfigGroup->setTabText(1, tr("EDIT JSON"));

    // TODO ui panle
    // ui->ruleConfigGroup->setTabText(0, tr("EDIT UI"));
    // ui->ruleConfigGroup->setTabText(1, tr("EDIT JSON"));
    ui->ruleConfigGroup->setTabText(0, tr("EDIT JSON"));
}

void MainWindow::output(QString message)
{
    ui->outputBrowser->append(message);
}

void MainWindow::loadWebConfigFiles(QString path)
{
    auto dir = std::make_unique<QDir>(path);
    dir->cd(tr("config"));

    QString webConfigFilePath = dir->absoluteFilePath("web-config.json");
    QString ruleConfigFilePath = dir->absoluteFilePath("response-rule.json");

    webConfig = std::make_unique<WebConfig>(webConfigFilePath);
    ruleConfig = std::make_unique<RuleConfig>(ruleConfigFilePath);

    flushToUI();

    // TODO ADD KEY ACTION TO SAVE THE FILE AND HIT TO UODATA.
    // QVariant tempValue = tempParam;
    // bool tempFinished = tempValue.toBool();
}

void MainWindow::UIFlushToData()
{
    try
    {
        // ui data to json.
        webConfig->setLocal(ui->local->text());
        webConfig->setPort(ui->port->text().toInt());
        webConfig->setConfigPath(ui->configPath->text());
        webConfig->setHome(ui->home->text());
        webConfig->setDebug((ui->debug->text() == tr("true")) ? true : false);

        webConfig->writeBack();
    }
    catch (const std::exception &e)
    {
        output(tr("ERROR INFO: "));
        output(tr("--------------------------------------------------------\n"));
        output(QString(e.what()));
    }

    output(tr("save successful!"));

    flushToUI();
}

void MainWindow::JSONFlushToData()
{
    QString serverJsonData = ui->serverConfigJSONData->toPlainText();
    webConfig->initializeJsonText(serverJsonData);
    webConfig->writeBack();

    QString ruleJsonData = ui->ruleConfigJSONData->toPlainText();
    ruleConfig->initializeJsonText(ruleJsonData);
    ruleConfig->writeBack();

    output(tr("save successful!"));

    flushToUI();
}

void MainWindow::flushToUI()
{
    // full data to ui.
    ui->local->setText(webConfig->getLocal());
    ui->port->setText(QString::number(webConfig->getPort()));
    ui->configPath->setText(webConfig->getConfigPath());
    ui->home->setText(webConfig->getHome());
    ui->debug->setText((webConfig->getDebug()) ? tr("true") : tr("false"));

    // full data to ui json.
    ui->serverConfigJSONData->clear();
    ui->serverConfigJSONData->append(QString(webConfig->toJson()));

    // auto configItem = ui->ruleConfigItem;

    auto configList = ruleConfig->getRulesData().get();

    auto config = configList->constBegin();

    // TODO add the ruleConfig data.

    // rule json
    ui->ruleConfigJSONData->clear();
    ui->ruleConfigJSONData->append(QString(ruleConfig->toJson()));
}

void MainWindow::serverAction()
{
    if (serverStatus == false)
    {
        // start server.
        // slightServer->setWebPath(webConfig->getConfigPath());
        QDir wp(webPath);
        if (wp.exists())
        {

            slightServer->setWebPath(wp.absolutePath());
            slightServer->start();

            // if not error.
            serverStatus = true;

            ui->startServer->setText(tr("STOP SERVER"));
        } else {
            output(tr("PATH NOT FOUND!"));
        }
    }
    else
    {
        // stop server
        slightServer->exit();

        serverStatus = false;

        ui->startServer->setText(tr("START SERVER"));
    }
}

void MainWindow::switchMainPanel(QModelIndex index)
{
    /**
     * 0为 main page
     * 1为 server page
     * 2为 rule page
     * 
     * index.row() is panel index
     */
    ui->stackedWidget->setCurrentIndex(index.row());
}

void MainWindow::initConnect()
{
    connect(ui->listView, SIGNAL(clicked(QModelIndex)), this, SLOT(switchMainPanel(QModelIndex)));

    connect(ui->startServer, SIGNAL(clicked()), this, SLOT(serverAction()));

    connect(ui->loadWeb, SIGNAL(clicked()), this, SLOT(chooseWebPath()));

    connect(ui->saveRuleConfigJsonData, SIGNAL(clicked()), this, SLOT(JSONFlushToData()));

    connect(ui->saveServerConfigJsonData, SIGNAL(clicked()), this, SLOT(JSONFlushToData()));

    connect(ui->saveServerConfigUiData, SIGNAL(clicked()), this, SLOT(UIFlushToData()));

    connect(slightServer, SIGNAL(outputMessage(QString)), this, SLOT(output(QString)));
}

void MainWindow::chooseWebPath()
{
    QString curPath = QApplication::applicationDirPath();
    QString path = QFileDialog::getExistingDirectory(this, tr("choose a folder as a web"), curPath, QFileDialog::ShowDirsOnly);
    // null path
    if (path.isEmpty() == false)
    {
        if (QDir dir(path); dir.isReadable())
        {
            bool existConfigPath = dir.cd(tr("config"));
            QFile webConfig(dir.absoluteFilePath("web-config.json"));
            QFile ruleConfig(dir.absoluteFilePath("response-rule.json"));

            if (!webConfig.exists() || !ruleConfig.exists())
            {
                // to create config files.
                output(tr("Can not find the config file."));
                auto messageBox = std::make_unique<QMessageBox>();
                messageBox->setWindowTitle(tr("WARNING"));
                messageBox->setText(tr("The path is not exist config floder! Are you sure to create them?"));
                messageBox->setStandardButtons(QMessageBox::StandardButton::Ok | QMessageBox::StandardButton::Cancel);
                messageBox->addButton(QMessageBox::StandardButton::Ok);
                messageBox->addButton(QMessageBox::StandardButton::Cancel);
                // messageBox->setButtonText(QMessageBox::StandardButton::Ok, tr("yes"));
                // messageBox->setButtonText(QMessageBox::StandardButton::Cancel, tr("no"));
                messageBox->exec();

                QMessageBox::StandardButton ret = messageBox->standardButton(messageBox->clickedButton());

                if (ret == QMessageBox::Ok)
                {
                    QFile webConfigFileTemplate(tr(":source/data/template/config/web-config.json"));
                    QFile ruleConfigFileTemplate(tr(":source/data/template/config/response-rule.json"));

                    if (!existConfigPath)
                    {
                        dir.mkdir(tr("config"));
                        dir.cd(tr("config"));
                    }

                    webConfigFileTemplate.copy(dir.absoluteFilePath(tr("web-config.json")));
                    ruleConfigFileTemplate.copy(dir.absoluteFilePath(tr("rule-config.json")));

                    output(tr("Added file: ").append(dir.absoluteFilePath(tr("web-config.json"))));
                    output(tr("Added file: ").append(dir.absoluteFilePath(tr("rule-config.json"))));
                }
            }
            setWebPath(path);

            output(tr("-----------------  web path change to  -----------------"));
            output(tr("  \"").append(path).append(tr("\"")));
            output(tr("--------------------------------------------------------\n"));

            loadWebConfigFiles(getWebPath());
        }
        else
        {
            output(tr("folder can not be a web path!"));
        }
    }
}

MainWindow::~MainWindow()
{
    delete ui;
}
