package io.github.oxmoon.jsubtitle_editor.gui;

import javax.swing.*;
import java.awt.*;

public class TextField extends JTextField {

    TextField(int length) {
        this.setPreferredSize(new Dimension(new Dimension(length,20)));
        this.setBackground(Color.white);
        this.setCaretColor(Color.black);
    }

    TextField() {
        this.setPreferredSize(new Dimension(new Dimension(300,20)));
        this.setBackground(Color.white);
        this.setCaretColor(Color.black);
    }
}
