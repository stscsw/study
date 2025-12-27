package cn.csw.lombok;

import lombok.Data;
import lombok.experimental.Delegate;

/**
 * @author Lier
 * @since 2024/2/28 11:34
 */
@Data
public class B {

    @Delegate
    private A a;
}
