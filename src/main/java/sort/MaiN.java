package sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MaiN {


    public static void main(String[] args) {
        String s = "b";
        Entity a = new Entity("a", 1);
        Entity b = new Entity("b", 3);
        Entity c = new Entity("c", 2);
        Entity d = new Entity("d", 4);
        List<Entity> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        Comparator<Entity> comparator = (e1,e2) -> e1.getName().equals(s) ? -1 : e2.getName().equals(s) ? 1 : e1.getSort() - e2.getSort();
        List<Entity> collect = list.stream().sorted(comparator).collect(Collectors.toList());
        for (int i = 0; i < collect.size(); i++) {
            System.out.println(collect.get(i));
        }
    }
}
