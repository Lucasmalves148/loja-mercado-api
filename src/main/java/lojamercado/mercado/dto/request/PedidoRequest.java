package lojamercado.mercado.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {
 
    @NotNull(message = "Cliente ID é obrigatório")
    private Long clienteId;
    
    private LocalDate data;

    @NotEmpty(message = "Pedido deve ter pelo menos um item")
    private List<ItemPedidoRequest> itens;
}
