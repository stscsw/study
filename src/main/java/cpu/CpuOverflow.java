package cpu;

public class CpuOverflow {

    public static void main(String[] args) {

        int random = 0;
        while (random < 100) {
            random = random * 10;
        }

    }
}
