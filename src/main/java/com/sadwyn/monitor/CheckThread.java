package com.sadwyn.monitor;

import java.io.IOException;


public class CheckThread implements Runnable{

    @Override
    public void run() {
        try {
            Runtime.getRuntime().exec(CreateGUI.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
