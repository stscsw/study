package cn.csw.guava;


import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;

/**
 * @author Lier
 * @since 2023/9/12 11:10
 */
public class GuavaTest {


    /**
     * Table - 双键Map
     */
    @Test
    public void test1() {
        Table<String, String, Integer> table = HashBasedTable.create();
        //存放元素
        table.put("Hydra", "Jan", 20);
        table.put("Hydra", "Feb", 28);

        table.put("Trunks", "Jan", 28);
        table.put("Trunks", "Feb", 16);

        //取出元素
        Integer dayCount = table.get("Hydra", "Feb");


        Set<String> keySet = table.rowKeySet();
        System.out.println("keySet" + keySet);
        Set<String> columnKeySet = table.columnKeySet();
        System.out.println("columnKeySet"+columnKeySet);
        Collection<Integer> values = table.values();
        System.out.println("values"+values);


        Table<String, String, Integer> transpose = Tables.transpose(table);
        System.out.println(transpose);

    }


}
