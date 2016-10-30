package com.sadwyn.monitor;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;


public class CreateGUI {
   private static JFrame frame;
   private JFileChooser chooser;
   private JButton button;
   private static File file;
   private FileNameExtensionFilter filter;
    private JSpinner timeSpinner;
    private static TrayIcon trayIcon;
    private static ArrayList<String> arrayList = new ArrayList<>();
    private static JList list;

    public static JFrame getFrame() {
        return frame;
    }

    public static JList getList() {
        return list;
    }

    public static ArrayList<String> getArrayList() {
        return arrayList;
    }

    public static TrayIcon getTrayIcon() {
        return trayIcon;
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
        timeSpinner = new JSpinner( new SpinnerDateModel() );
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date());

        list = new JList();
        list.setLayoutOrientation(JList.VERTICAL);


        panel.add(list);
        panel.add(button);
        panel.add(timeSpinner);
        panel.setBackground(Color.lightGray);
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
                    arrayList.add(file.getName());
                    list.setListData(new Vector<String>(arrayList));
                    Date date = (Date) timeSpinner.getModel().getValue();
                    startThread(date.getSeconds(),date.getMinutes(),date.getHours());
                }

            }
        });
    }
    public void startThread(int sec, int min, int hour){
            CheckThread checkThread = new CheckThread(sec,min,hour,file);
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
        trayIcon = new TrayIcon(icon);
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
