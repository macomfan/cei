/*
 * Copyright (C) 2019 Ma
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.ma.cei.generator;

import cn.ma.cei.exception.CEIException;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 *
 * @author Ma
 */
public class CEIPath {

    public enum Type {
        FOLDER,
        FILE
    }

    private String filename = null;
    private final File io;

    public CEIPath(Type type, String path) {
        io = Paths.get(path).toFile();
        if (type == Type.FILE) {
            filename = io.getName();
        }
    }

    public CEIPath(CEIPath path) {
        io = path.io;
        filename = path.filename;
    }

    public File getIO() {
        return io;
    }

    public static CEIPath appendPath(CEIPath path, String... paths) {
        if (!path.isFolder()) {
            throw new CEIException("[CEIPath] Cannot append path to file");
        }
        Path tmp = Paths.get(path.io.getAbsolutePath(), paths);
        return new CEIPath(Type.FOLDER, tmp.toString());
    }

    public static CEIPath appendFile(CEIPath path, String filename) {
        if (!path.isFolder()) {
            throw new CEIException("[CEIPath] Cannot append path to file");
        }
        Path tmp = Paths.get(path.io.getAbsolutePath(), filename);
        return new CEIPath(Type.FILE, tmp.toString());
    }

    boolean isFolder() {
        return filename == null || filename.equals("");
    }

    public boolean exists() {
        return io.exists();
    }

    public void mkdirs() {
        if (isFolder()) {
            io.mkdirs();
        } else {
            throw new CEIException("[CEIPath] not folder: " + filename);
        }
    }

    public void write(String str) {
        if (isFolder()) {
            throw new CEIException("[CEIPath] Cannot write to folder");
        }
        try {
            if (io.exists()) {
                //TODO
                // Warning
            }
            io.createNewFile();
            FileWriter newFileWriter = new FileWriter(io);
            BufferedWriter bufferedWriter = new BufferedWriter(newFileWriter);
            bufferedWriter.write(str);
            bufferedWriter.close();
            newFileWriter.close();
        } catch (Exception e) {
        }
    }

    public void copyTo(CEIPath destPath) {
        if (destPath.isFolder() && !destPath.exists()) {
            destPath.mkdirs();
        }
        if (!isFolder()) {
            // Copy file only
            copyFile(destPath);
            return;
        }
        try {
            for (File file : Objects.requireNonNull(io.listFiles())) {
                if (file.isFile()) {
                    System.out.println(file.getAbsolutePath());
                    CEIPath newSrc = new CEIPath(Type.FILE, file.getAbsolutePath());
                    newSrc.copyTo(destPath);
                } else {
                    System.out.println(file.getAbsolutePath());
                    CEIPath newSrc = new CEIPath(Type.FOLDER, file.getAbsolutePath());
                    CEIPath newDest = CEIPath.appendPath(destPath, file.getName());
                    newSrc.copyTo(newDest);
                }
            }
        } catch (Exception e) {
            throw new CEIException("Cannot copy folder");
        }
    }

    private void copyFile(CEIPath destPath) {
        if (isFolder()) {
            throw new CEIException("Cannot copy folder as a file");
        }
        FileChannel inputChannel;
        FileChannel outputChannel;
        CEIPath destFile = destPath;
        if (destPath.isFolder()) {
            destFile = appendFile(destPath, filename);
        }
        try {
            inputChannel = new FileInputStream(io).getChannel();
            outputChannel = new FileOutputStream(destFile.io).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            inputChannel.close();
            outputChannel.close();
        } catch (Exception e) {
            throw new CEIException("Cannot copy file");
        }
    }
}
