package cn.csw.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Lier
 * @since 2023/4/12 9:57
 */
@Data
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "ofNameAndAge")
public class TestLombok {

    @NonNull
    private String name;

    @NonNull
    private Integer age;


}
