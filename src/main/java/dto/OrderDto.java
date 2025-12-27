package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sjaak Derksen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String name;
    private Long quantity;
    private Boolean isDone;

}
