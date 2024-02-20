package com.mygdx.game;

import java.util.Timer;
import java.util.TimerTask;

public class Test {
    public static void main(String[] args) {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("hade");
            }
        };


        System.out.println("hallo");


        Timer timer = new Timer("Timer");
        long delay = 15000;
        timer.schedule(task, delay);

        System.out.println("på bade");

    }



}
