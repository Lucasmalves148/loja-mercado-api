package lojamercado.mercado.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoResponse {

    private ProdutoResponse produto;
    private Integer quantidade;
    private BigDecimal precoUnitario;

    public BigDecimal getValorTotal(){
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

}
