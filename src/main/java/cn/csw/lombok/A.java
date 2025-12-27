package cn.csw.lombok;

import lombok.Data;

/**
 * @author Lier
 * @since 2024/2/28 11:34
 */
@Data
public class A {


    private String name;

    private Integer age;

    private String getDept(){
        return "dept";
    }
}
