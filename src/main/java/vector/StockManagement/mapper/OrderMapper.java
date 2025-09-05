package vector.StockManagement.mapper;

import org.springframework.stereotype.Component;
import vector.StockManagement.dto.OrderDTO;
import vector.StockManagement.model.Order;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    
    public OrderDTO toDTO(Order order) {
        if (order == null) {
            return null;
        }
        
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setNumber(order.getNumber());
        dto.setStatus(order.getStatus());
        dto.setLevel(order.getLevel());
        dto.setChannel(order.getChannel());
        dto.setCurrency(order.getCurrency());
        // Extract values from totals map
        if (order.getTotals() != null) {
            dto.setSubtotal(order.getTotals().get("subtotal"));
            dto.setTaxAmount(order.getTotals().get("tax"));
            dto.setTotalAmount(order.getTotals().get("total"));
        }
        dto.setNotes(order.getNotes());
        dto.setCancellationReason(order.getCancellationReason());
        dto.setDeliveryDate(order.getDeliveryDate());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        
        // Set IDs only to avoid lazy loading issues
        if (order.getDistributor() != null) {
            dto.setDistributorId(order.getDistributor().getId());
        }
        if (order.getStore() != null) {
            dto.setStoreId(order.getStore().getId());
        }
        if (order.getTenant() != null) {
            dto.setTenantId(order.getTenant().getId());
        }
        
        return dto;
    }
    
    public List<OrderDTO> toDTOList(List<Order> orders) {
        return orders.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
