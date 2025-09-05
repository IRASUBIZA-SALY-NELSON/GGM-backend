package vector.StockManagement.dto;

import lombok.Data;
import vector.StockManagement.model.enums.OrderChannel;
import vector.StockManagement.model.enums.OrderLevel;
import vector.StockManagement.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Long id;
    private String number;
    private OrderStatus status;
    private OrderLevel level;
    private OrderChannel channel;
    private String currency;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private String notes;
    private String cancellationReason;
    private LocalDateTime deliveryDate;
    private String deliveryAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Simple references without full entity data
    private Long distributorId;
    private Long storeId;
    private Long tenantId;
}
