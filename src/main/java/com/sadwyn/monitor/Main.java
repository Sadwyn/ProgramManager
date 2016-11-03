package com.sadwyn.monitor;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CreateGUI gui = new CreateGUI();
                gui.GUI();
            }
        });
    }
}
