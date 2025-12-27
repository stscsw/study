package mapper;

import dto.CustomerDto;
import dto.OrderItemDto;
import dto.OrderItemKeyDto;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-22T18:34:40+0800",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
public class ClonerImpl implements Cloner {

    @Override
    public CustomerDto clone(CustomerDto customerDto) {
        if ( customerDto == null ) {
            return null;
        }

        CustomerDto customerDto1 = new CustomerDto();

        customerDto1.setId( customerDto.getId() );
        customerDto1.setCustomerName( customerDto.getCustomerName() );
        customerDto1.setOrders( orderItemDtoListToOrderItemDtoList( customerDto.getOrders() ) );
        customerDto1.setStock( orderItemKeyDtoOrderItemDtoMapToOrderItemKeyDtoOrderItemDtoMap( customerDto.getStock() ) );

        return customerDto1;
    }

    @Override
    public void clone(CustomerDto target, CustomerDto source) {
        if ( source == null ) {
            return;
        }

        target.setId( source.getId() );
        target.setCustomerName( source.getCustomerName() );
        if ( target.getOrders() != null ) {
            List<OrderItemDto> list = orderItemDtoListToOrderItemDtoList( source.getOrders() );
            if ( list != null ) {
                target.getOrders().clear();
                target.getOrders().addAll( list );
            }
            else {
                target.setOrders( null );
            }
        }
        else {
            List<OrderItemDto> list = orderItemDtoListToOrderItemDtoList( source.getOrders() );
            if ( list != null ) {
                target.setOrders( list );
            }
        }
        if ( target.getStock() != null ) {
            Map<OrderItemKeyDto, OrderItemDto> map = orderItemKeyDtoOrderItemDtoMapToOrderItemKeyDtoOrderItemDtoMap( source.getStock() );
            if ( map != null ) {
                target.getStock().clear();
                target.getStock().putAll( map );
            }
            else {
                target.setStock( null );
            }
        }
        else {
            Map<OrderItemKeyDto, OrderItemDto> map = orderItemKeyDtoOrderItemDtoMapToOrderItemKeyDtoOrderItemDtoMap( source.getStock() );
            if ( map != null ) {
                target.setStock( map );
            }
        }
    }

    protected OrderItemDto orderItemDtoToOrderItemDto(OrderItemDto orderItemDto) {
        if ( orderItemDto == null ) {
            return null;
        }

        OrderItemDto orderItemDto1 = new OrderItemDto();

        orderItemDto1.setName( orderItemDto.getName() );
        orderItemDto1.setQuantity( orderItemDto.getQuantity() );

        return orderItemDto1;
    }

    protected List<OrderItemDto> orderItemDtoListToOrderItemDtoList(List<OrderItemDto> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderItemDto> list1 = new ArrayList<OrderItemDto>( list.size() );
        for ( OrderItemDto orderItemDto : list ) {
            list1.add( orderItemDtoToOrderItemDto( orderItemDto ) );
        }

        return list1;
    }

    protected OrderItemKeyDto orderItemKeyDtoToOrderItemKeyDto(OrderItemKeyDto orderItemKeyDto) {
        if ( orderItemKeyDto == null ) {
            return null;
        }

        OrderItemKeyDto orderItemKeyDto1 = new OrderItemKeyDto();

        orderItemKeyDto1.setStockNumber( orderItemKeyDto.getStockNumber() );

        return orderItemKeyDto1;
    }

    protected Map<OrderItemKeyDto, OrderItemDto> orderItemKeyDtoOrderItemDtoMapToOrderItemKeyDtoOrderItemDtoMap(Map<OrderItemKeyDto, OrderItemDto> map) {
        if ( map == null ) {
            return null;
        }

        Map<OrderItemKeyDto, OrderItemDto> map1 = new LinkedHashMap<OrderItemKeyDto, OrderItemDto>( Math.max( (int) ( map.size() / .75f ) + 1, 16 ) );

        for ( java.util.Map.Entry<OrderItemKeyDto, OrderItemDto> entry : map.entrySet() ) {
            OrderItemKeyDto key = orderItemKeyDtoToOrderItemKeyDto( entry.getKey() );
            OrderItemDto value = orderItemDtoToOrderItemDto( entry.getValue() );
            map1.put( key, value );
        }

        return map1;
    }
}
