package io.github.oxmoon.jsubtitle_editor;

import io.github.oxmoon.jsubtitle_editor.gui.GuiJFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Program {

    public static final int NO_SRT_FILES_FOUND = 0;
    public static final int FINISHED = 1;



    String dirPath;
    long moveTimeMilliseconds;
    String outputPath;
    List<String> find;
    String replace;
    String starting;
    String ending;
    boolean cleanUp;

    public Program(String dirPath, long moveTimeMilliseconds, String in_find, String replace, String starting, String ending, boolean cleanUp) {
        this.dirPath = dirPath;
        this.moveTimeMilliseconds = moveTimeMilliseconds;
        this.replace = replace;
        this.starting = starting;
        this.ending = ending;
        this.cleanUp = cleanUp;

        this.find = new ArrayList<>(
                Arrays.asList(in_find.split(",")));
    }

    public int run() {
        try {
            File dir = new File(dirPath);
            File[] dirFiles = dir.listFiles();

            if (dirFiles != null) {
                outputPath = dirFiles[0].getParent() + "/jsubtitle-editor/";
                Files.createDirectories(Paths.get(outputPath));
                for (File file : dirFiles) {

                    String[] fullFileName = file.getName().split("\\.");
                    String fileName = file.getName().replace(".srt", "");

                    if (fullFileName[fullFileName.length - 1].equals("srt")) {
                        SrtController srtController = new SrtController(file,
                                outputPath + fileName +".srt");
                        srtController.moveTimeStamp(moveTimeMilliseconds);

                        for (String s : find) {
                            srtController.findAndReplace(s.trim(), replace);
                        }

                        if(!starting.equals("") && !ending.equals("")) {
                            srtController.removeTextBetween(starting, ending, cleanUp);
                        }

                        srtController.reIndex();

                        try {
                            srtController.outputFile();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                return FINISHED;
            } else {
                return NO_SRT_FILES_FOUND;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
