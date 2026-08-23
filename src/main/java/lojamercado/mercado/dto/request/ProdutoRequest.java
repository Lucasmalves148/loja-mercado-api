package lojamercado.mercado.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lojamercado.mercado.enumerate.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProdutoRequest {
     
    @NotBlank
    private String nome;

    @NotNull
    @Positive
    private BigDecimal preco;

    @PositiveOrZero
    private int estoque;

    @NotNull
    private Categoria categoria;
}
