package lojamercado.mercado.handler;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestErrorMessage {
 
    private HttpStatus httpstatus;
    private String mensagem;

}
