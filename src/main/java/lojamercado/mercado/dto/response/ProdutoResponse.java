package lojamercado.mercado.dto.response;

import java.math.BigDecimal;

import lojamercado.mercado.enumerate.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProdutoResponse {
    
    private Long id;
    private String nome;
    private BigDecimal preco;
    private int estoque;
    private Categoria categoria;
}

