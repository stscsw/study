package common;

import java.util.Date;

public class VolatileTest {

    public static void main(String[] args) throws InterruptedException {
        Run run = new Run();
        new Thread(run).start();

        while (true){
            Thread.sleep(200);
            if(run.isFlag()){
                System.out.println("----------------");
            }
        }
    }

}


class Run implements Runnable {
    private boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
        flag = true;
        System.out.println("flag = " + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
