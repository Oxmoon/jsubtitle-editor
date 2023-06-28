package io.github.oxmoon.jsubtitle_editor.gui;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {
    Label(String text) {
        this.setHorizontalAlignment(SwingConstants.RIGHT);
        this.setForeground(Color.white);
        this.setText(text);
    }
}
