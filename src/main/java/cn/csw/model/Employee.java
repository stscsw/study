package cn.csw.model;

import lombok.Data;

/**
 * @author Lier
 * @since 2023/8/4 15:10
 */
@Data
public class Employee {
    private String name;
    private int age;
    final static int LEN = 8;

    public Employee(String name, int age) {
        if (name.length() > LEN) {
            name = name.substring(0, 8);
        } else {
            while (name.length() < LEN) {
                name = name + '\u0000';
            }
            this.name = name;
            this.age = age;
        }
    }
}
