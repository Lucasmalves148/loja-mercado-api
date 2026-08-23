package lojamercado.mercado.dto.response;
import java.time.LocalDate;
import java.util.List;
import lojamercado.mercado.enumerate.Status;
import lombok.Data;

@Data
public class PedidoResponse {
    
    private Long id;

    private ClienteResponse cliente;
    
    private LocalDate data;

    private Status status;
    
    private List<ItemPedidoResponse> itens;
}
