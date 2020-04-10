package cn.ma.cei;

import cn.ma.cei.exception.CEIErrors;
import cn.ma.cei.generator.BuildSDK;
import cn.ma.cei.langs.cpp.CppFramework;
import cn.ma.cei.langs.golang.GoFramework;
import cn.ma.cei.langs.java.JavaFramework;
import cn.ma.cei.langs.python3.Python3Framework;
import cn.ma.cei.utils.Checker;
import io.vertx.core.cli.CLI;
import io.vertx.core.cli.CommandLine;

import java.util.Arrays;
import java.util.List;

import io.vertx.core.cli.annotations.*;

@Name("java")
@Summary("CryptoCurrency Exchange Interface Builder")
public class Main {

    public Main(String[] args) {
        CLI cli = CLI.create(Main.class);
        List<String> userArgs = Arrays.asList(args);
        CommandLine commandLine = cli.parse(userArgs);
        CLIConfigurator.inject(commandLine, this);
    }

    private String configFile;
    private Boolean commandMode;
    private String language;
    private Boolean webuiMode;
    private Integer port;
    private Boolean help;
    private Boolean version;
    private CLI cli;

    @Option(shortName = "f", longName = "file")
    @Description("Specify the config file, the file should be xml file.")
    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    } 

    @Option(shortName = "c", longName = "command", flag = true)
    @Description("Run in command mode")
    public void setCommandMode(boolean commandMode) {
        this.commandMode = commandMode;
    }

    @Option(shortName = "u", longName = "webui", flag = true)
    @Description("Run in Web UI mode")
    public void setWebuiMode(boolean webuiMode) {
        this.webuiMode = webuiMode;
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
        }
        if (Checker.isEmpty(configFile)) {
            CEIErrors.showInputFailure("-f or --file is required");
            return;
        }
        if (!configFile.endsWith(".xml")) {
            CEIErrors.showInputFailure("a *.xml file is required for -f or --file");
            return;
        }
        BuildSDK.registerFramework(new JavaFramework());
        BuildSDK.registerFramework(new CppFramework());
        BuildSDK.registerFramework(new Python3Framework());
        BuildSDK.registerFramework(new GoFramework());
        BuildSDK.build("C:\\dev\\cei\\exchanges\\huobipro", "java", "C:\\dev\\cei\\output");
    }

    public void showHelp() {
        StringBuilder builder = new StringBuilder();
        cli.usage(builder);
        System.out.println(builder.toString());
    }

    public void showVersion() {
        System.out.println("0.0.1");
    }
}
