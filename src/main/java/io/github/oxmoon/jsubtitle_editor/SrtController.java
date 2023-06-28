package io.github.oxmoon.jsubtitle_editor;

import io.github.oxmoon.jsubtitle_editor.srt.SrtSubtitle;

import java.io.*;
import java.sql.SQLOutput;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SrtController {

    private File file;
    private List<SrtSubtitle> subtitles;
    private String outputPath;

    public SrtController(File file, String outputPath) {
        this.file = file;
        this.outputPath = outputPath;
        loadFile();
    }

    public void loadFile() {
        try {
            this.subtitles = SrtSubtitle.read(file);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void moveTimeStamp(long time) {
        long minimumTimestamp = -(subtitles.get(0).getStart());
        if(time < minimumTimestamp) {
            time = minimumTimestamp;
        }
        for (SrtSubtitle subtitle : subtitles) {
            subtitle.setStart(subtitle.getStart() + time);
            subtitle.setEnd(subtitle.getEnd() + time);
        }
    }

    public void findAndReplace(String find, String replace) {
        for (SrtSubtitle subtitle : subtitles) {
            if(subtitle.getText().contains(find)) {
                String newText = subtitle.getText().replace(find, replace);
                subtitle.setText(newText);
            }
        }
    }

    public void removeTextBetween(String start, String end, Boolean cleanup) {
        for (SrtSubtitle subtitle : subtitles) {
            while (subtitle.getText().contains(start) && subtitle.getText().contains(end)) {
                char startChar = start.charAt(0);
                char endChar = end.charAt(0);
                char[] currentText = subtitle.getText().toCharArray();
                StringBuilder textToRemove = new StringBuilder();

                int position = -1;
                int length = currentText.length;
                boolean startFound = false;
                boolean finished = false;

                do {
                    position++;
                    char currentChar = currentText[position];
                    if(currentChar == startChar && startFound == false) {
                        textToRemove.append(currentChar);
                        startFound = true;
                    } else if (currentChar == endChar && startFound == true) {
                        textToRemove.append(currentChar);
                        String newText = subtitle.getText().replace(textToRemove, "");
                        subtitle.setText(newText.trim() + '\n');
                        finished = true;

                    } else if (startFound == true) {
                        textToRemove.append(currentChar);
                    }
                } while(position < length && finished == false);
            }

            //cleanUp boolean
            if(cleanup == true) {
                if (subtitle.getText().contains(start)) {
                    String newText = subtitle.getText().replace(start, "");
                    subtitle.setText(newText);
                }
                if (subtitle.getText().contains(end)) {
                    String newText = subtitle.getText().replace(end, "");
                    subtitle.setText(newText);
                }
            }
        }
    }

    public void reIndex() {
        boolean subtitleRemoved = false;
        List<SrtSubtitle> subtitlesToRemove = new LinkedList<SrtSubtitle>();
        for (SrtSubtitle subtitle : subtitles) {
            if(subtitle.getText().trim().equals("")) {
                subtitlesToRemove.add(subtitle);
                subtitleRemoved = true;
            }
        }
        for (SrtSubtitle subtitle : subtitlesToRemove) {
            subtitles.remove(subtitle);
        }
        if (subtitleRemoved == true) {
            int i = 1;
            for (SrtSubtitle subtitle : subtitles) {
                subtitle.setIndex(i);
                i++;
            }
        }
    }

    public void outputFile() throws IOException {
        PrintWriter writer = new PrintWriter(
                new BufferedWriter (
                        new FileWriter(outputPath)));
        for (SrtSubtitle subtitle : subtitles) {
            writer.write(
                    Integer.toString(subtitle.getIndex()) + "\n" +
                            convTimestamp(subtitle.getStart()) + " --> " +
                            convTimestamp(subtitle.getEnd()) + "\n" +
                            subtitle.getText() + "\n"
            );
        }
        writer.flush();
        writer.close();
    }

    static String convTimestamp(long milliseconds) {

        long hours = (milliseconds / 1000) / 60 / 60 % 24;
        long minutes = (milliseconds / 1000) / 60 % 60;
        long seconds = (milliseconds / 1000) % 60;
        long mils = milliseconds % 1000;

        return String.format("%02d:%02d:%02d,%03d", hours, minutes, seconds, mils);
    }

    public List<SrtSubtitle> getSubtitles() {
        return subtitles;
    }
}
