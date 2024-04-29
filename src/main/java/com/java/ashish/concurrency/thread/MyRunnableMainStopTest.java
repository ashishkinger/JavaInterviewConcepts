package com.java.ashish.concurrency.thread;

public class MyRunnableMainStopTest {
    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();
        try{
            System.out.println("Running main");
            Thread.sleep(10L * 1000L);
        }catch(Exception e){
            e.printStackTrace();
        }

        runnable.doStop();
        System.out.println("Running main end");
    }

}

class MyRunnable implements Runnable{

    private boolean stop = false;

    public synchronized void doStop(){
        this.stop =true;
    }

    private synchronized boolean keepRunning(){
        return this.stop ==false;
    }
    @Override
    public void run() {
        while(keepRunning()){
           System.out.println("Running Runnable");
           try{
               Thread.sleep(3L * 1000L);
           }catch(Exception e){
               e.printStackTrace();
           }
        }
    }
}