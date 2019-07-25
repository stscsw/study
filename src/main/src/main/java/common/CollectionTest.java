package common;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionTest {

    public static void main(String[] args) {
        Collection<Long> c1 = new ArrayList<>();
        c1.add(2000L);
        c1.add(2010L);
        c1.add(2020L);
        Collection<Long> c2 = new ArrayList<>();
        c2.add(2010L);
        c2.add(2020L);
        c2.add(2030L);

        System.out.println(xorOperation(c1, c2));


        System.out.println(new Long(2000L)==new Long(2000L));
        System.out.println(new Long(2000L).equals(2000L));


    }



    /**
     * 异或运算
     * c1集合发生变化 c2集合不变  返回一个新的异或结果集合
     * AΔB = (A-B)∪(B-A) = (A∪B)-(A∩B)
     *  [1,2]  [2,3]  ----->  [1,3]
     * @param c1 集合1
     * @param c2 集合2
     * @param <T> 异或结果集合
     * @return
     */
    private static  <T> Collection<T> xorOperation(Collection<T> c1,Collection<T> c2){
        Collection<T> result = new ArrayList<>();
        result.addAll(c1);
        result.addAll(c2);
        c1.retainAll(c2);
        result.removeAll(c1);
        return  result;
    }
}
