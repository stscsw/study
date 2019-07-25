package annotation;

import java.lang.annotation.*;
import java.util.Collection;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MyAnnotation {

    //时间字段 必须为偶数个数  判断相邻两个时间的间距
    String[] dateTimeField() default {};

    //店铺字段 若是搜索店铺为多个的集合 请把多个字段名放进去  若isSessionStore字段值是true 并且店铺字段合并的店铺集为空集 店铺搜索按session里的店铺权限
    String[] storeCodeField() default {};

    //时间转化的格式
    String dateTimePattern() default "yyyy-MM-dd HH:mm:ss";

    //店铺字段类型
    Class storeCodeClass() default Collection.class;

    //分隔符  若是 storeCodeClass为String.class 使用该字符串分割成集合
    String separation() default ",";

    //true 会获取方法签名的HttpServletRequest入参拿出店铺权限 与 用户所选的店铺权限做交集判断
    boolean isSessionStore() default false;

}
