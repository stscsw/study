package cn.csw.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Lier
 * @since 2023/7/20 16:28
 */
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Builder.Default
    private String name = "name";
    private String mobilePhone;

    public static void main(String[] args) {
        System.out.println(Person.builder().build());
        System.out.println(new Person());
    }
}
