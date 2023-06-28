package io.github.oxmoon.jsubtitle_editor;

import io.github.oxmoon.jsubtitle_editor.SrtController;
import io.github.oxmoon.jsubtitle_editor.srt.SrtSubtitle;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.List;

public class SrtTestBase {
    File testFile = new File("src/test/resources/testsubs1.srt");
    String outputPath = "src/test/resources/outputTest.srt";

    SrtController srtController = new SrtController(testFile, outputPath);
}
