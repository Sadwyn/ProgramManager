package com.sadwyn.monitor;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;


public class CheckThread implements Runnable{
    private int sec;
    private int mins;
    private int hours;
    private boolean IsMatch = true;
    private File file;

    public CheckThread(int sec, int mins, int hours,File file) {
        this.sec = sec;
        this.mins = mins;
        this.hours = hours;
        this.file = file;
    }

    @Override
    public void run() {
        while (IsMatch) {
            {
                try {
                    Thread.sleep(1000);
                    if(new Date().getHours()==hours && (new Date().getMinutes()==mins && new Date().getSeconds()==sec-30)||
                            new Date().getMinutes()!=mins&& new Date().getSeconds()==sec+30) {
                        CreateGUI.getTrayIcon().displayMessage("Info","Программа "+file.getName()+" будет запущена через 30 сек.", TrayIcon.MessageType.INFO);
                    }
                    if(new Date().getHours()==hours && new Date().getMinutes()==mins && new Date().getSeconds()==sec) {
                        Runtime.getRuntime().exec(file.getAbsolutePath());
                        IsMatch = false;
                        CreateGUI.getArrayList().remove(file.getName());
                        if(!CreateGUI.getFrame().isVisible()){
                            CreateGUI.getTrayIcon().displayMessage("Info", "Программа "+file.getName()+" запущена.", TrayIcon.MessageType.INFO);
                        }
                        CreateGUI.getList().setListData(new Vector<String>(CreateGUI.getArrayList()));
                    }
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
