package tasks.irregularverbstest;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by Allexb.
 */
public class WorkingWindow extends JFrame {

    private JMenuBar menuBar;
    private JMenu menuMain, menuOptions, menuTest, menuHelp;
    private JMenuItem menuItemExit,
            menuItemLoad, menuItemSettings,
            menuItemStart, menuItemStop, menuItemAchievements,
            menuItemAbout;
    private JFileChooser fileChooser;

    private JPanel contentPane;

    private JLabel translation, statusBar;
    private JTextField infinitive,
                       pastSimple,
                       pastParticiple;

    private JButton check,
                    skip,
                    cancel;

    private Statistic statistic;

    private LoadVocabulary vocabulary;
    private String loadVocabulary;
    private TestModule testModule;
    private IrregularVerb testingVerb;


    public WorkingWindow(){
        this.setTitle("Irregular Verbs Testing");
        this.setSize(400, 235);
        this.setLocationRelativeTo(null);
        this.setJMenuBar(createMenuBar());
        this.setContentPane(createContentPane());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

        try {
//            this.vocabulary = new LoadVocabulary(Paths.get("").toRealPath() + "\\src\\main\\resources\\vocabulary.dat");
//            String string = Paths.get("").toAbsolutePath() + "\\classes\\IrregularVerbsTest\\resources\\vocabulary.dat";
//            JOptionPane.showMessageDialog(this, string, "Vocabulary error!", JOptionPane.INFORMATION_MESSAGE);
            this.vocabulary = new LoadVocabulary(Paths.get("").toRealPath() + "\\resources\\vocabulary.dat");


        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Main vocabulary not find.", "Vocabulary error!", JOptionPane.WARNING_MESSAGE);
            menuItemStart.setEnabled(false);
//            e.printStackTrace();
        }

    }

    public JMenuBar createMenuBar() {

        menuBar =  new JMenuBar();

        //Build the first menu.
        menuMain = new JMenu("Main");
        menuMain.setMnemonic(KeyEvent.VK_M);
        menuMain.getAccessibleContext().setAccessibleDescription("System control menu.");
        menuBar.add(menuMain);

        //a group of JMenuItems
        menuItemExit = new JMenuItem("Exit", KeyEvent.VK_X);
        menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        menuItemExit.getAccessibleContext().setAccessibleDescription("Closing program.");
        menuMain.add(menuItemExit);

        //functionality of menuItemExit
        menuItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WorkingWindow.this.dispose();
            }
        });

        //Build the first menu.
        menuOptions = new JMenu("Options");
        menuOptions.setMnemonic(KeyEvent.VK_O);
        menuOptions.getAccessibleContext().setAccessibleDescription("Edit program settings.");
        menuBar.add(menuOptions);

        //a group of JMenuItems
        menuItemLoad = new JMenuItem("Load Vocabulary", KeyEvent.VK_L);
        menuItemLoad.getAccessibleContext().setAccessibleDescription("Load or update words vocabulary.");
        menuOptions.add(menuItemLoad);
        menuItemLoad.setEnabled(true);

        menuItemLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    String path = Paths.get("").toRealPath() + "\\resources\\";
                    fileChooser = new JFileChooser(path);
                } catch (IOException e1) {
                    fileChooser = new JFileChooser();
                }
                int rVal = fileChooser.showOpenDialog(WorkingWindow.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    loadVocabulary = fileChooser.getCurrentDirectory().toString()+ "\\" + fileChooser.getSelectedFile().getName();
                    try {
                        WorkingWindow.this.vocabulary = new LoadVocabulary(loadVocabulary);
                        WorkingWindow.this.menuItemStart.setEnabled(true);
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(WorkingWindow.this, "Selected vocabulary can`t be loaded.", "Vocabulary error!", JOptionPane.WARNING_MESSAGE);
                        e1.printStackTrace();
                    }
//                    System.out.println(loadVocabulary);
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    JOptionPane.showMessageDialog(WorkingWindow.this, "Vocabulary has not been selected.", "Vocabulary error!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        menuOptions.addSeparator();

        menuItemSettings = new JMenuItem("Settings", KeyEvent.VK_T);
        menuItemSettings.getAccessibleContext().setAccessibleDescription("Program settings.");
        menuOptions.add(menuItemSettings);
        menuItemSettings.setEnabled(false);


        //Build the first menu.
        menuTest = new JMenu("Testing");
        menuTest.setMnemonic(KeyEvent.VK_T);
        menuTest.getAccessibleContext().setAccessibleDescription("Main program functions.");
        menuBar.add(menuTest);

        menuItemStart = new JMenuItem("Start test", KeyEvent.VK_R);
        menuItemStart.getAccessibleContext().setAccessibleDescription("Start testing.");
        menuTest.add(menuItemStart);
        menuItemStart.setEnabled(true);

        //functionality of menuItemStart
        menuItemStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                menuItemLoad.setEnabled(false);
                menuItemSettings.setEnabled(false);
                menuItemStop.setEnabled(true);
                menuItemStart.setEnabled(false);

                testModule = new TestModule(vocabulary.loadData());
                testingVerb = testModule.getNext(TestModule.RANDOM_NOT_REPEATING);

                statistic = new Statistic(testModule.testSize(TestModule.RANDOM_NOT_REPEATING));

                translation.setText(testingVerb.getTranslation());
                infinitive.setText(testingVerb.getInfinitive());
                statusBar.setText(statistic.toString());
                check.setEnabled(true);
                skip.setEnabled(true);

            }
        });

        menuItemStop = new JMenuItem("Stop test", KeyEvent.VK_P);
        menuItemStop.getAccessibleContext().setAccessibleDescription("Stop testing.");
        menuTest.add(menuItemStop);
        menuItemStop.setEnabled(false);

        //functionality of menuItemStop
        menuItemStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WorkingWindow.this.menuStop();
            }
        });

        menuTest.addSeparator();

        menuItemAchievements = new JMenuItem("Achievements", KeyEvent.VK_C);
        menuItemAchievements.getAccessibleContext().setAccessibleDescription("Achievements in previous testings.");
        menuTest.add(menuItemAchievements);
        menuItemAchievements.setEnabled(false);

        //Build the first menu.
        menuHelp = new JMenu("Help");
        menuHelp.setMnemonic(KeyEvent.VK_H);
        menuHelp.getAccessibleContext().setAccessibleDescription("Help information");
        menuBar.add(menuHelp);

        menuItemAbout = new JMenuItem("About", KeyEvent.VK_A);
        menuItemAbout.getAccessibleContext().setAccessibleDescription("About program and author");
        menuHelp.add(menuItemAbout);


        //functionality of menuAbout
        menuItemAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               JOptionPane.showMessageDialog(WorkingWindow.this, "Irregular Verbs Test!\n\n" +
                       "Version: 1.00 build 030\n\n" + "Author: Alexander Bogomolnyy\n" + "e-mail: post@allexb.kiev.ua\n" +
                               "\u00a9 2015 allexb.kiev.ua. All rights reserved.\n", "About", JOptionPane.PLAIN_MESSAGE);
            }
        });

        return menuBar;
    }

    public Container createContentPane() {
        Font font = new Font("Arial", Font.BOLD, 22);
        contentPane = new JPanel();
        contentPane.setOpaque(true);
//        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(null);

        // "translation" field
        translation = new JLabel("translation");
        translation.setFont(font);
        translation.setSize(400, font.getSize());
        translation.setHorizontalAlignment(SwingConstants.CENTER);
        translation.setLocation(0, 15);

        // "infinitive" field
        JLabel infinitiveLabel = new JLabel("Infinitive");
        infinitiveLabel.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() - 4));
        infinitiveLabel.setSize(this.getWidth() / 3 - 5, font.getSize());
        infinitiveLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infinitiveLabel.setLocation(1, 55);

        infinitive = new JTextField("");
        infinitive.setFont(font);
        infinitive.setEditable(false);
        infinitive.setBackground(Color.WHITE);
        infinitive.setHorizontalAlignment(SwingConstants.CENTER);
        infinitive.setSize(this.getWidth() / 3 - 5, font.getSize() + 6);
        infinitive.setLocation(1, 80);

        // "pastSimple" field
        JLabel pastSimpleLabel = new JLabel("Past Simple");
        pastSimpleLabel.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() - 4));
        pastSimpleLabel.setSize(this.getWidth() / 3 - 5, font.getSize());
        pastSimpleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pastSimpleLabel.setLocation(this.getWidth() / 3 , 55);

        pastSimple = new JTextField("");
        pastSimple.setFont(font);
//        pastSimple.setEnabled(false);
        pastSimple.setHorizontalAlignment(SwingConstants.CENTER);
        pastSimple.setSize(this.getWidth() / 3 - 5, font.getSize() + 6);
        pastSimple.setLocation(this.getWidth() / 3, 80);

        // "pastParticiple" field
        JLabel pastParticipleLabel = new JLabel("Past Participle");
        pastParticipleLabel.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() - 4));
        pastParticipleLabel.setSize(this.getWidth() / 3 - 5, font.getSize());
        pastParticipleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pastParticipleLabel.setLocation(-1 + this.getWidth() / 3 * 2, 55);

        pastParticiple = new JTextField("");
        pastParticiple.setFont(font);
//        pastSimple.setEnabled(false);
        pastParticiple.setHorizontalAlignment(SwingConstants.CENTER);
        pastParticiple.setSize(this.getWidth() / 3 - 5, font.getSize() + 6);
        pastParticiple.setLocation(-1 + this.getWidth() / 3 * 2, 80);


        //"check" button
        check = new JButton("Check");
        check.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() - 4));
        check.setSize(check.getText().length() * 20, font.getSize() + 6);
        check.setLocation(this.getWidth() / 2 - check.getWidth() - 20, 120);
        check.setEnabled(false);

        check.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(pastSimple.getText().equals(testingVerb.getPastSimple())) {
                    pastSimple.setBackground(Color.GREEN);
                } else {
                    pastSimple.setBackground(Color.RED);
                }
                if(pastParticiple.getText().equals(testingVerb.getPastParticiple())) {
                    pastParticiple.setBackground(Color.GREEN);
                } else {
                    pastParticiple.setBackground(Color.RED);
                }
                if (testModule.isRight(pastSimple.getText(), pastParticiple.getText())) {
                    testingVerb = testModule.getNext(TestModule.RANDOM_NOT_REPEATING);
                    translation.setText(testingVerb.getTranslation());
                    infinitive.setText(testingVerb.getInfinitive());
                    statistic.check();
                    statusBar.setText(statistic.toString());
                    pastSimple.setText("");
                    pastSimple.grabFocus();
                    pastParticiple.setText("");
                    pastSimple.setBackground(Color.WHITE);
                    pastParticiple.setBackground(Color.WHITE);
                } else {
//                    pastSimple.setBackground(Color.WHITE);
//                    pastParticiple.setBackground(Color.WHITE);
                }
                if (!testModule.hasNext()) {
                    WorkingWindow.this.menuStop();
                }
            }
        });

//        //"cancel" button
//        cancel = new JButton("Cancel");
//        cancel.setFont(new Font(font.getFontName(), 0, font.getSize() - 4));
//        cancel.setSize(check.getText().length()* 20, font.getSize() + 6);
//        cancel.setLocation(this.getWidth() / 2 + 20,120);

        //"skip" button
        skip = new JButton("Skip");
        skip.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() - 4));
        skip.setSize(check.getText().length() * 20, font.getSize() + 6);
        skip.setLocation(this.getWidth() / 2 + 20, 120);
        skip.setEnabled(false);

        skip.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                testingVerb = testModule.getNext(TestModule.RANDOM_NOT_REPEATING);
                translation.setText(testingVerb.getTranslation());
                infinitive.setText(testingVerb.getInfinitive());
                statistic.skip();
                statusBar.setText(statistic.toString());
                pastSimple.setText("");
                pastSimple.grabFocus();
                pastParticiple.setText("");
                pastSimple.setBackground(Color.WHITE);
                pastParticiple.setBackground(Color.WHITE);
                if (!testModule.hasNext()) {
                    WorkingWindow.this.menuStop();
                }

            }
        });


        statusBar = new JLabel(" Status bar: no testing now!");
        statusBar.setSize(this.getWidth() - 6, 25);
        statusBar.setLocation(-1, this.getHeight() - 76);
        statusBar.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() - 10));
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));

        contentPane.add(translation);
        contentPane.add(infinitiveLabel);
        contentPane.add(infinitive);
        contentPane.add(pastSimpleLabel);
        contentPane.add(pastSimple);
        contentPane.add(pastParticipleLabel);
        contentPane.add(pastParticiple);
        contentPane.add(check);
//        contentPane.add(cancel);
        contentPane.add(skip);
        contentPane.add(statusBar);


        return contentPane;
    }

    private void menuStop() {
        check.setEnabled(false);
        skip.setEnabled(false);
        menuItemLoad.setEnabled(true);

        menuItemSettings.setEnabled(false);
        menuItemAchievements.setEnabled(false);

        menuItemStart.setEnabled(true);
        menuItemStop.setEnabled(false);
        translation.setText("translation");
        infinitive.setText("");
        pastSimple.setText("");
        pastParticiple.setText("");
        statusBar.setText(" Status bar: no testing now!");
        JOptionPane.showMessageDialog(WorkingWindow.this, "Test statictic!\n\n" +
                "Test size: " + statistic.getSize() + " words.\n" + "Learned:  " + statistic.getCheckCount() + " words.\n" +
                "Skipped:  " + (statistic.getSkipCount() + statistic.getBalance()) + " words.\n\n" +
                "Effectiveness: " + (statistic.getCheckCount()*100/statistic.getSize()) + "%.", "Statistic", JOptionPane.INFORMATION_MESSAGE);
    }
}
