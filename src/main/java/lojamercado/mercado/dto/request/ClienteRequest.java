package lojamercado.mercado.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClienteRequest {


    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;
}
