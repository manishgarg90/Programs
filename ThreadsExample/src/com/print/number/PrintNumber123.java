package com.print.number;

class S {
    int no = 1;
    int nextThread = 1;
}

public class PrintNumber123 implements Runnable {
    int id;
    S s;

    public PrintNumber123(int id, S s) {
        this.s = s;
        this.id = id;
    }

    @Override
    public void run() {
        synchronized (s) {
            for (int i = 0; i < 15; i++) {
                //System.out.println(s.nextThread + " ---   " + this.id);
                while (s.nextThread != this.id) {
                    try {
                        s.wait();
                    } catch (InterruptedException e) {
                    }
                }
                System.out.println("Thread " + this.id + " print : " + s.no);
                s.no += 1;
                s.nextThread = this.id == 3 ? 1 : this.id + 1;
               // System.out.println(s.nextThread + " --***-   " + this.id);
                s.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        S s = new S();
        new Thread(new PrintNumber123(1, s)).start();
        new Thread(new PrintNumber123(2, s)).start();
        new Thread(new PrintNumber123(3, s)).start();
    }
}
