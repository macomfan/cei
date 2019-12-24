/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.ma.cei.utils;

import cn.ma.cei.exception.CEIException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 *
 * @author u0151316
 */
public class FileCopy {

    public static void folderCopy(File srcPath, File destPath) {
        if (!destPath.exists()) {
            destPath.mkdirs();
        }
        try {
            for (File file : srcPath.listFiles()) {
                if (file.isFile()) {
                    File newDest = new File(destPath.getPath() + File.separator + file.getName());
                    fileCopy(file, newDest);
                } else {
                    File newDest = new File(destPath.getPath() + File.separator + file.getName());
                    folderCopy(file, newDest);
                }
            }
        } catch (Exception e) {
            throw new CEIException("Cannot copy filder");
        }
    }

    public static void fileCopy(File srcFile, File destFile) throws FileNotFoundException {
        FileChannel inputChannel;
        FileChannel outputChannel;
        try {
            inputChannel = new FileInputStream(srcFile).getChannel();
            outputChannel = new FileOutputStream(destFile).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            inputChannel.close();
            outputChannel.close();
        } catch (Exception e) {
            throw new CEIException("Cannot copy file");
        }
    }
}
