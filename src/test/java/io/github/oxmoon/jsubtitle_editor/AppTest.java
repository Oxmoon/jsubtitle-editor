package io.github.oxmoon.jsubtitle_editor;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest extends SrtTestBase {


    @Test
    void testMoveTimeStamp() throws IOException {

        //Milliseconds
        srtController.moveTimeStamp(100);
        assertEquals(14020, srtController.getSubtitles().get(0).getStart());
        assertEquals(18280, srtController.getSubtitles().get(0).getEnd());
        srtController.moveTimeStamp(-100);
        assertEquals(13920, srtController.getSubtitles().get(0).getStart());
        assertEquals(18180, srtController.getSubtitles().get(0).getEnd());

        //Seconds
        srtController.moveTimeStamp(1000);
        assertEquals(14920, srtController.getSubtitles().get(0).getStart());
        assertEquals(19180, srtController.getSubtitles().get(0).getEnd());
        srtController.moveTimeStamp(-1000);
        assertEquals(13920, srtController.getSubtitles().get(0).getStart());
        assertEquals(18180, srtController.getSubtitles().get(0).getEnd());

        //Minutes
        srtController.moveTimeStamp(60000);
        assertEquals(73920, srtController.getSubtitles().get(0).getStart());
        assertEquals(78180, srtController.getSubtitles().get(0).getEnd());
        srtController.moveTimeStamp(-60000);
        assertEquals(13920, srtController.getSubtitles().get(0).getStart());
        assertEquals(18180, srtController.getSubtitles().get(0).getEnd());

        //Hours
        srtController.moveTimeStamp(3600000);
        assertEquals(3613920, srtController.getSubtitles().get(0).getStart());
        assertEquals(3618180, srtController.getSubtitles().get(0).getEnd());
        srtController.moveTimeStamp(-3600000);
        assertEquals(13920, srtController.getSubtitles().get(0).getStart());
        assertEquals(18180, srtController.getSubtitles().get(0).getEnd());
    }

    @Test
    void testMoveTimeStampTooSmall() throws IOException {

        srtController.moveTimeStamp(-100000);
        assertEquals(0, srtController.getSubtitles().get(0).getStart());
        assertEquals(4260, srtController.getSubtitles().get(0).getEnd());
    }


    @Test
    void testFindAndReplaceOne() {

        srtController.findAndReplace("（エレン）", "（ゲージ）");
        assertEquals(
                "（ゲージ）\n" +
                        "壁の向）こうに（あああ）は海があると\n" +
                        "アルミンが言った\n",
                srtController.getSubtitles().get(0).getText());
    }

    @Test
    void testFindAndReplaceMultiple() {

        srtController.findAndReplace("あ", "え");
        assertEquals(
                "（エレン）\n" +
                        "壁の向）こうに（えええ）は海がえると\n" +
                        "アルミンが言った\n",
                srtController.getSubtitles().get(0).getText());
    }

    @Test
    void testRemoveTextBetweenNoCleanup() {
        srtController.removeTextBetween("（", "）", false);
        assertEquals(
                "壁の向）こうには海があると\n" +
                "アルミンが言った\n",
                srtController.getSubtitles().get(0).getText());

    }

    @Test
    void testRemoveTextBetweenWithCleanup() {
        srtController.removeTextBetween("（", "）", true);
        assertEquals(
                "壁の向こうには海があると\n" +
                        "アルミンが言った\n",
                srtController.getSubtitles().get(0).getText());

    }
}
