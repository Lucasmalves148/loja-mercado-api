package lojamercado.mercado.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoRequest {

    private Long idProduto;

    @PositiveOrZero
    @Min(value = 1, message = "Quantidade deve ser maior que zero")
    private Integer quantidade;
}
