import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UninTest {
    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= Integer.MAX_VALUE) ? Integer.MAX_VALUE : n + 1;
    }

    @Test
    public void test1() {
        System.out.println(tableSizeFor(-1));
    }


    @Test
    public void test2(){
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        System.out.println(list.stream().reduce(0, (u, t) -> u + t));
        int i = 2;
        i +=5;
        System.out.println(i);
    }

    @Test
    public void test3(){
        String[] split = "12345".split(",");
        System.out.println(Arrays.asList(split));
    }

    @Test
    public void test4(){
        String reg = "^[NTZ].*";
        System.out.println("N21312".matches(reg));
        System.out.println("T21312".matches(reg));
        System.out.println("Z21312".matches(reg));
        System.out.println("A21312".matches(reg));
        System.out.println("B21312".matches(reg));
    }

    @Test
    public void test5() {
        List<Integer> cycleBarcode = new ArrayList<>();
        for(int i = 0;i<120;i++){
            cycleBarcode.add(i);
        }

        int batchSize = 100;
        int size = cycleBarcode.size();

        // 判断是否有必要分批
        if (batchSize < size) {
            // 分批数
            int part = size / batchSize;
            for (int i = 0; i < part; i++) {
                List<Integer> batchList = cycleBarcode.subList(0, batchSize);
                //处理
                show(batchList);
                batchList.clear();
            }
            // 最后剩下的数据
            if (!cycleBarcode.isEmpty()) {
                //处理
                show(cycleBarcode);
            }
        } else {
            //处理
            show(cycleBarcode);
        }
    }

    private void show(List<Integer> list){
        System.out.println(list);
    }

    @Test
    public void test11(){
        BigDecimal b = BigDecimal.ZERO;
        BigDecimal a = new BigDecimal(10);
        b = b.add(a);
        System.out.println(b);

    }
}
