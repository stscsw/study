package extend;

import java.util.*;

public class MianClass {
    public static void main(String[] args) {
        HashSet<Collection<A>> singleton = new HashSet<>();
        singleton.add(Collections.singletonList(new B()));
        show(singleton);
    }


    public static <T> void show(Set<Collection<A>> a) {
        System.out.println(a.getClass());
    }
}
