package com.sadwyn.monitor;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;


public class CreateGUI {
   private JFrame frame;
   private JFileChooser chooser;
   private JButton button;
   private static File file;
   private FileNameExtensionFilter filter;


    public static File getFile() {
        return file;
    }

    public void GUI() {
        //отрисовка формы
        frame = new JFrame("Program Runner");
        frame.setSize(400, 200);
        frame.setMinimumSize(new Dimension(400, 200));
        frame.setDefaultCloseOperation(Frame.ICONIFIED);
        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        filter = new FileNameExtensionFilter(".EXE files","exe");
         button = new JButton("Выберите файл...");

        panel.add(button);
        frame.add(panel);
        frame.setVisible(true);
        setTrayIcon();
        chooser = new JFileChooser();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                chooser.setFileFilter(filter);
                int returnVal = chooser.showDialog(frame,"Открыть файл");
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                    startChooser();
                }

            }
        });
    }
    public void startChooser(){
            CheckThread checkThread = new CheckThread();
            Thread thread = new Thread(checkThread);
            thread.start();
    }

    //генерация трея
    public void setTrayIcon(){

        if(!SystemTray.isSupported()) return;
        PopupMenu popupMenu = new PopupMenu();
        MenuItem item = new MenuItem("Exit");
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        popupMenu.add(item);
        URL url = Main.class.getResource("/ico.png");
        Image icon = Toolkit.getDefaultToolkit().getImage(url);
        TrayIcon trayIcon = new TrayIcon(icon);
        trayIcon.setImageAutoSize(true);
        trayIcon.setPopupMenu(popupMenu);
        SystemTray tray = SystemTray.getSystemTray();

        //Добавляю обработчик на двойной клик по иконке
        trayIcon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2) {
                    frame.setState(Frame.NORMAL);
                    frame.setVisible(true);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        try {
            tray.add(trayIcon); //добавляю иконку к системному трею
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

}
