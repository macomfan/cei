package cn.ma.cei.generator;

public class Code {
    private static final String CRLF = "\r\n";
    private static final String LF = "\n";
    private static final String CR = "\r";
    private static final String endl = LF;

    private int blockLevel = 0;
    private String block = "    ";
    private boolean isNewLine = true;

    private StringBuilder stringBuilder = new StringBuilder();

    public void appendln(String str) {
        appendBlock();
        append(str);
        endln();
    }

    public void endln() {
        stringBuilder.append(endl);
        isNewLine = true;
    }

    public void appendWordsln(String... args) {
        appendWords(args);
        endln();
    }

    public void appendWords(String... args) {
        appendBlock();
        String tmp = "";
        boolean first = true;
        for (String str : args) {
            if (!first) tmp += " ";
            tmp += str;
            first = false;
        }
        append(tmp);
    }

    public void appendCode(Code code) {
        int from = 0;
        int index = 0;
        while (true) {
            index = code.stringBuilder.indexOf(endl, from);
            if (index == -1) {
                break;
            }
            String tmp = code.stringBuilder.substring(from, index);
            from = index + endl.length();
            appendln(tmp);
        }
    }

    @Override
    public String toString() {
        return stringBuilder.toString();
    }

    public void newBlock(Block block) {
        blockLevel++;
        block.inBlock();
        blockLevel--;
        if (blockLevel < 0) {
            blockLevel = 0;
        }
    }

    public void startBlock() {
        blockLevel++;
    }

    public void endBlock() {
        blockLevel--;
        if (blockLevel < 0) {
            blockLevel = 0;
        }
    }

    @FunctionalInterface
    public interface Block {
        void inBlock();
    }

    private void append(String str) {
        String tmp = str.replace("\r\n", "").replace("\r", "");
        stringBuilder.append(tmp);
    }

    private void appendBlock() {
        if (isNewLine) {
            for (int i = 0; i < blockLevel; i++) {
                stringBuilder.append(block);
            }
            isNewLine = false;
        }
    }
}

