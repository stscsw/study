package cn.csw.function;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

/**
 * @author Lier
 * @since 2023/4/12 10:17
 */
@Data
@RequiredArgsConstructor
public class ExtractFunction implements Base {

    @NonNull
    @Delegate
    private BaseFunction baseFunction;


}
