import dto.CustomerDto;
import dto.OrderItemDto;
import dto.OrderItemKeyDto;
import mapper.Cloner;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Lier
 * @since 2023/11/24 11:10
 */
public class MapStructTest {


    @Test
    public void test() {

        CustomerDto customerDto1 = new CustomerDto();
        customerDto1.setId(10L);
        customerDto1.setCustomerName("Jaques");

        OrderItemDto order1 = new OrderItemDto();
        order1.setName("Table");
        order1.setQuantity(2L);

        customerDto1.setOrders(new ArrayList<>(Collections.singleton(order1)));
        OrderItemKeyDto key = new OrderItemKeyDto();
        key.setStockNumber(5);

        Map<OrderItemKeyDto,OrderItemDto> stock = new HashMap<>();
        stock.put(key, order1);
        customerDto1.setStock(stock);

        CustomerDto customerDto2 = Cloner.MAPPER.clone(customerDto1);

        CustomerDto customerDto3 = new CustomerDto();
        Cloner.MAPPER.clone(customerDto3,customerDto1);


        System.out.println(Stream.of(1,2).toList().contains(1));
    }
}
