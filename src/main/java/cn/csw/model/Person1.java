package cn.csw.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 *
 *@Builder(创建内部类)
 *@Builder.Default(默认指需要加注解 不然会丢失)
 *@SuperBuilder(父类的属性也要是用链式编程)
 *@Accessor(fluent,chain,prefix)
 * fluent get和set方法已字段名做为方法名并且set方法返回this
 * chain set方法返回this
 * prefix 去掉属性名的前缀
 * makeFinal get和set方法 添加final修饰符
 * @author Lier
 * @since 2023/7/20 16:28
 */
@Accessors(chain = true)
@Data
public class Person1 {

    private final Integer id;
    private String personName;
    private String personMobilePhone;


}
