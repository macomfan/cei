package cn.ma.cei;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.finalizer.XMLDatabase;
import cn.ma.cei.generator.BuildSDK;
import cn.ma.cei.langs.cpp.CppFramework;
import cn.ma.cei.langs.golang.GoFramework;
import cn.ma.cei.langs.java.JavaFramework;
import cn.ma.cei.langs.python3.Python3Framework;
import cn.ma.cei.model.xSDK;
import cn.ma.cei.service.Service;
import cn.ma.cei.service.messages.ExchangeInfoMessage;
import cn.ma.cei.service.messages.ExchangeQueryMessage;
import cn.ma.cei.service.processors.ExchangeInfoProcessor;
import cn.ma.cei.service.processors.ExchangeQueryProcessor;
import cn.ma.cei.service.restful.GetExchangeSummary;
import cn.ma.cei.service.restful.StaticPage;
import cn.ma.cei.service.websocket.OperationHandler;
import cn.ma.cei.utils.Checker;
import cn.ma.cei.xml.Convert;
import cn.ma.cei.xml.JsonToXml;
import cn.ma.cei.xml.XmlToJson;
import io.vertx.core.cli.CLI;
import io.vertx.core.cli.CommandLine;
import io.vertx.core.cli.UsageMessageFormatter;
import io.vertx.core.cli.annotations.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Name("java")
@Summary("CryptoCurrency Exchange Interface Builder")
public class Main {

    public Main(String[] args) {
        cli = CLI.create(Main.class);
        List<String> userArgs = Arrays.asList(args);
        CommandLine commandLine = cli.parse(userArgs);
        CLIConfigurator.inject(commandLine, this);
    }

    private String configFile;
    private Boolean commandMode;
    private Boolean uiMode;
    private Boolean testMode;
    private String language;
    private Integer port;
    private Boolean help;
    private Boolean version;
    private CLI cli;

    @Argument(argName = "xxx.xml", index = 0, required = false)
    @Description("Specify the config file, the file should be xml file.")
    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    @Option(shortName = "c", longName = "command", flag = true)
    @Description("Run in command mode")
    public void setCommandMode(boolean commandMode) {
        this.commandMode = commandMode;
    }

    @Option(shortName = "u", longName = "ui", flag = true)
    @Description("Run in Web UI mode")
    public void setUIMode(boolean uiMode) {
        this.uiMode = uiMode;
    }

    @Option(shortName = "t", longName = "test", flag = true)
    @Description("Run in test mode")
    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    @Option(shortName = "h", longName = "help", flag = true)
    @Description("Show this help")
    public void setHelp(boolean help) {
        this.help = help;
    }

    @Option(shortName = "v", longName = "version", flag = true)
    @Description("Show version")
    public void setVersion(boolean version) {
        this.version = version;
    }

    public static void main(String[] args) {
        Main main = new Main(args);
        main.run();
    }

    public void run() {
        if (help) {
            showHelp();
            return;
        } else if (version) {
            showVersion();
            return;
        } else {
            initialize();
            if (uiMode) {
                runUI();
                return;
            } else if (testMode) {
                runTest();
                return;
            }
        }
        if (Checker.isEmpty(configFile)) {
            CEIErrors.showInputFailure("-f or --file is required");
            showHelp();
            return;
        }
        if (!configFile.endsWith(".xml")) {
            CEIErrors.showInputFailure("a *.xml file is required for -f or --file");
        }
    }

    private void initialize() {
        BuildSDK.registerFramework(new JavaFramework());
        BuildSDK.registerFramework(new CppFramework());
        BuildSDK.registerFramework(new Python3Framework());
        BuildSDK.registerFramework(new GoFramework());
        BuildSDK.build("C:\\dev\\cei\\exchanges\\test", "java", "C:\\dev\\cei\\output");
        xSDK sdk = XMLDatabase.getSDK("debug");
        XmlToJson xmlToJson = new XmlToJson();
        Convert.doConvert(xmlToJson, sdk);
        System.out.println(xmlToJson.toJsonString());
        JsonToXml jsonToXml = new JsonToXml(xmlToJson.getJsonObject());
        xSDK test = new xSDK();
        Convert.doConvert(jsonToXml, test);
        XmlToJson xmlToJson2 = new XmlToJson();
        Convert.doConvert(xmlToJson2, test);
        System.out.println(xmlToJson2.toJsonString());
    }

    public void showHelp() {
        StringBuilder buffer = new StringBuilder();
        UsageMessageFormatter formatter = new UsageMessageFormatter();
        formatter.setLeftPadding(4);
        formatter.buildWrapped(buffer, cli.getSummary());
        formatter.buildWrapped(buffer, "");
        formatter.computeUsage(buffer, "java -jar cei.jar [options] <xxx.xml>");
        formatter.buildWrapped(buffer, "");
        formatter.buildWrapped(buffer, "Arguments:");
        formatter.computeOptionsAndArguments(buffer, new LinkedList<>(), cli.getArguments());
        formatter.buildWrapped(buffer, "");
        formatter.buildWrapped(buffer, "Options: (You can only choose one option!)");
        formatter.computeOptionsAndArguments(buffer, cli.getOptions(), new LinkedList<>());
        formatter.buildWrapped(buffer, "");
        formatter.buildWrapped(buffer, "Example:");
        formatter.buildWrapped(buffer, "    java -jar cei.jar -c build.xml");

        System.out.println(buffer.toString());
    }

    public void showVersion() {
        System.out.println("0.0.1");
    }

    private void runUI() {
        Service service = new Service();
        service.registerRestful("/api/getExchangeSummary", new GetExchangeSummary())
                .registerRestful("/*", new StaticPage("C:/dev/cei/webroot"));
        service.registerWebSocket("/ws", new OperationHandler());
        ExchangeInfoMessage.setProcessor(new ExchangeInfoProcessor());
        ExchangeQueryMessage.setProcessor(new ExchangeQueryProcessor());
        service.start(8090);
    }

    private void runTest() {
        //BuildSDK.build("C:\\dev\\cei\\exchanges", "java", "C:\\dev\\cei\\output");
        TestMode.run(8888);
    }

//    private boolean checkMode() {
//
//    }
}
