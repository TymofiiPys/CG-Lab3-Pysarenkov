package org.cglab3.GUI;

//import org.example.GUI.Lab1MenuBar;
//import org.example.GUI.RegTreeDrawer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class MainWindow extends Container {
    private JPanel mainPanel;
    private JPanel graphicsPanel;
    private JPanel controlPanel;
    private JPanel controlInsidePanel;
    private JLabel offsetLabel;
    private JTextField textField1;
    public final Dimension mainWindowDims = new Dimension(600, 500);
//    public final BODrawer BODrawer;

    public MainWindow() {
//        nextEventButton.setText("Наступна подія");
//        nextEventButton.setEnabled(false);
//
//        BODrawer = new BODrawer(graphicsPanel);
//
//        //button action listeners
//        nextEventButton.addActionListener(e -> {
//            if (BODrawer.BOSet()) {
//                BODrawer.nextEvent();
//                int[] nEvents = BODrawer.getEvents();
//                if (!BODrawer.checkNextEvent()) {
//                    nextEventButton.setEnabled(false);
//                }
//            }
//        });
//
//        //graphics panel listeners
//        graphicsPanel.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                super.componentResized(e);
//                if (BODrawer.BOSet()) {
//                    BODrawer.drawBO();
//                }
//            }
//        });
//
//        offset1TextField.getDocument().addDocumentListener(regTFListener());
//
//        offset2TextField.getDocument().addDocumentListener(regTFListener());
    }

    public DocumentListener regTFListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                insertUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        };
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("ЛР№3. Перетин відрізків на площині (Bentley–Ottmann).");
        MainWindow mw = new MainWindow();
        Lab1MenuBar menuBar = new Lab1MenuBar(frame, mw);
        frame.setJMenuBar(menuBar);
        frame.setContentPane(mw.mainPanel);
        frame.setMinimumSize(mw.mainWindowDims);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
