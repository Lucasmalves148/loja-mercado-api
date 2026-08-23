package lojamercado.mercado.dto.request;

import java.time.LocalDate;
import java.util.List;
import lojamercado.mercado.enumerate.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {
 
    private Long clienteId;
    
    private LocalDate data;

    private Status status;
    
    private List<ItemPedidoRequest> itens;
}
