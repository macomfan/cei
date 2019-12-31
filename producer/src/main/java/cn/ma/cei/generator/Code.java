/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.generator;

/**
 *
 * @author u0151316
 */
public class Code {

    private static final String CRLF = "\r\n";
    private static final String LF = "\n";
    private static final String CR = "\r";
    private static final String endl = LF;
    private static final String block = "    ";

    private int blockLevel = 0;

    private boolean isNewLine = true;

    private final StringBuilder stringBuilder = new StringBuilder();

    private void append(String statement) {
        stringBuilder.append(statement);
    }

    public void appendln(String statement) {
        appendBlock();
        append(statement);
        endln();
    }

    public void appendWords(String... statements) {
        appendBlock();
        String str = "";
        for (String statement : statements) {
            if (str.equals("")) {
                str += statement;
            } else {
                str += " " + statement;
            }
        }
        append(str);
    }

    public void appendWordsln(String... statements) {
        appendWords(statements);
        endln();
    }

    private void appendBlock() {
        if (isNewLine) {
            for (int i = 0; i < blockLevel; i++) {
                stringBuilder.append(block);
            }
            isNewLine = false;
        }
    }

    public void endln() {
        stringBuilder.append(endl);
        isNewLine = true;
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

    @FunctionalInterface
    public interface Block {

        void inBlock();
    }

    public void newBlock(Block block) {
        blockLevel++;
        block.inBlock();
        blockLevel--;
        if (blockLevel < 0) {
            blockLevel = 0;
        }
    }
}
