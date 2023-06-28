package io.github.oxmoon.jsubtitle_editor.gui;

import io.github.oxmoon.jsubtitle_editor.Program;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class GuiJFrame extends JFrame implements ActionListener {

    public String in_directoryText;
    public String in_timeStampText;
    public String in_findText;
    public String in_replaceText;
    public String in_startingText;
    public String in_endingText;
    public boolean in_cleanup;
    public boolean in_forward;

    JButton fileButton;
    TextField fileDirText;
    JFormattedTextField timeStamp;
    JRadioButton forward;
    JRadioButton backward;
    TextField findField;
    TextField replaceField;
    TextField startingField;
    TextField endingField;
    JCheckBox cleanup;
    JButton runButton;

    public GuiJFrame() {
        //Frame Settings
        int frameWidth = 500;
        int frameHeight = 300;
        this.setSize(frameWidth, frameHeight);
        this.setTitle("jsubtitle-editor");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //Left Side Panel
        Label l1 = new Label("Folder Path: ");
        Label l3 = new Label("Find: ");
        Label l2 = new Label("Move Timestamps: ");
        Label l4 = new Label("Replace: ");
        Label l5 = new Label(" ");
        Label l6 = new Label("Starting: ");
        Label l7 = new Label("Ending: ");
        Label l8 = new Label("Cleanup: ");

        JPanel left = new JPanel(new GridLayout(0,1));
        left.setBackground(Color.DARK_GRAY);
        left.setPreferredSize(new Dimension(this.getWidth()/4, frameHeight));
        this.add(left, BorderLayout.WEST);
        left.add(l1);
        left.add(l2);
        left.add(l3);
        left.add(l4);
        left.add(l5);
        left.add(l6);
        left.add(l7);
        left.add(l8);

        //text boxes, buttons and checkbox
        fileDirText = new TextField((getWidth()/3));
        fileButton = new JButton("Select Folder");
        timeStamp = new JFormattedTextField(
                createFormatter("##:##:##,###"));
        forward = new JRadioButton("", true);
        backward = new JRadioButton();
        ButtonGroup group = new ButtonGroup();
        group.add(forward);
        group.add(backward);
        findField = new TextField(150);
        replaceField = new TextField(150);
        startingField = new TextField(10);
        endingField = new TextField(10);
        cleanup = new JCheckBox();


        //Right Side Panels
        JPanel right = new JPanel(new GridLayout(0,1));
        JPanel spacer = new JPanel(new GridBagLayout());
        JPanel filePanel = new JPanel(new GridBagLayout());
        JPanel timeStampPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        right.setBackground(Color.DARK_GRAY);
        right.setPreferredSize(new Dimension((this.getWidth()/4 * 3), 0));
        this.add(right, BorderLayout.EAST);

        //File Panel
        filePanel.setBackground(Color.DARK_GRAY);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 3;
        filePanel.add(fileDirText,c);
        filePanel.add(fileButton);
        right.add(filePanel);

        //Time Stamp Panel
        timeStampPanel.setBackground(Color.DARK_GRAY);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridwidth = 4;
        timeStampPanel.add(timeStamp, c);
        Label forwardL = new Label("Forward:");
        Label backwardL = new Label("Backward:");
        timeStampPanel.add(forwardL);
        timeStampPanel.add(forward);
        timeStampPanel.add(backwardL);
        timeStampPanel.add(backward);
        right.add(timeStampPanel);

        right.add(findField);
        right.add(replaceField);

        //Remove Between Text Options
        Label spacerLabel = new Label("Remove Between Text");
        spacer.setBackground(Color.DARK_GRAY);
        spacer.add(spacerLabel);
        right.add(spacer);
        right.add(startingField);
        right.add(endingField);
        right.add(cleanup);

        //Bottom
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        bottom.setBackground(Color.lightGray);
        bottom.setPreferredSize(new Dimension(0, getHeight()/8));
        this.add(bottom, BorderLayout.SOUTH);
        runButton = new JButton("Run");
        bottom.add(runButton);

        //Action Listeners
        fileButton.addActionListener(this);
        runButton.addActionListener(this);
        forward.addActionListener(this);
        backward.addActionListener(this);
        cleanup.addActionListener(this);

        ImageIcon image = new ImageIcon("src/main/resources/logo.png");
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(Color.DARK_GRAY);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == runButton) {
            if(fileDirText.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "No directory selected", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if(!startingField.getText().equals("")
                    && endingField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Must specify 'Ending' field.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else if(startingField.getText().equals("")
                    && !endingField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Must specify 'Starting' field.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                this.in_directoryText = fileDirText.getText();
                this.in_timeStampText = timeStamp.getText();
                this.in_findText = findField.getText();
                this.in_replaceText = replaceField.getText();
                this.in_startingText = startingField.getText();
                this.in_endingText = endingField.getText();
                this.in_cleanup = cleanup.isSelected();
                this.in_forward = forward.isSelected();
                runProgram();
            }
        }

        if(e.getSource() == fileButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int response = fileChooser.showOpenDialog(null);

            if (response == JFileChooser.APPROVE_OPTION) {
                String selectedPath = fileChooser.getSelectedFile().getPath();
                fileDirText.setText(selectedPath);
            }
        }
    }

    private void runProgram() {
        long time = convertTextToMilliseconds(in_timeStampText);
        if(!in_forward) {
            time = -time;
        }
        Program program = new Program(in_directoryText, time, in_findText, in_replaceText,
                in_startingText, in_endingText, in_cleanup);
        int status = program.run();
        if (status == program.NO_SRT_FILES_FOUND) {
            JOptionPane.showMessageDialog(null, "No .srt files found in directory.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } else if (status == program.FINISHED) {
            JOptionPane.showMessageDialog(null, "Program finished.", "Complete",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    protected MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
            formatter.setPlaceholderCharacter('0');
        } catch (java.text.ParseException exc) {
            JOptionPane.showMessageDialog(null, "Time formatter broken.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return formatter;
    }

    private long convertTextToMilliseconds(String s) {
        String[] parsedString = s.split(":");
        Long hours = TimeUnit.HOURS.toMillis(Long.parseLong(parsedString[0]));
        Long minutes = TimeUnit.MINUTES.toMillis(Long.parseLong(parsedString[1]));
        String[] parsedString2 = parsedString[2].split(",");
        Long seconds = TimeUnit.SECONDS.toMillis(Long.parseLong(parsedString2[0]));
        Long millis = Long.parseLong(parsedString2[1]);

        return hours + minutes + seconds + millis;
    }
}
