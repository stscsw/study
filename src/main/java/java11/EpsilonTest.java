package java11;


import java.util.ArrayList;
import java.util.List;


// -XX:+UnlockExperimentalVMOptions -XX:+UseEpsilonGC
//-XX:+UnlockExperimentalVMOptions -XX:+UseZGC
//-Xms 是指设定程序启动时占用内存大小。
//-Xmx 是指设定程序运行期间最大可占用的内存大小。
//-Xss 是指设定每个线程的堆栈大小。
public class EpsilonTest {

    public static void main(String[] args) {
        List<Garbage> list = new ArrayList<>();
        boolean flag = true;
        while (flag) {
            list.add(new Garbage());
            if (list.size() == 500) {
                list.clear();
            }
        }
    }
}


class Garbage {
    private String name;
    private int age;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println(this + "=======我已经变成垃圾");
    }
}