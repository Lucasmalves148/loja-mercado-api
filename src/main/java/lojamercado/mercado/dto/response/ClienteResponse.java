package lojamercado.mercado.dto.response;

import lombok.Data;

@Data
public class ClienteResponse {
    
    private Long id;
    private String nome;
    private String email;
}