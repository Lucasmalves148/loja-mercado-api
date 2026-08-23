package lojamercado.mercado.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lojamercado.mercado.exceptions.ClienteNotFoundException;
import lojamercado.mercado.exceptions.EstoqueInsuficienteException;
import lojamercado.mercado.exceptions.PedidoNotFoundException;
import lojamercado.mercado.exceptions.ProdutoNotFoundException;
import lojamercado.mercado.exceptions.StatusException;
import lojamercado.mercado.exceptions.StatusNotFoundException;

@RestControllerAdvice

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(ClienteNotFoundException.class)
    private ResponseEntity<RestErrorMessage> clienteNotFoundHandler(ClienteNotFoundException c){
        RestErrorMessage mensagemErro = new RestErrorMessage(HttpStatus.NOT_FOUND, c.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemErro);
    }

    @ExceptionHandler(ProdutoNotFoundException.class)
    private ResponseEntity<RestErrorMessage> produtoNotFoundHandler(ProdutoNotFoundException p){
        RestErrorMessage mensagemErro = new RestErrorMessage(HttpStatus.NOT_FOUND, p.getMessage());
        return ResponseEntity.status((HttpStatus.NOT_FOUND)).body(mensagemErro);
    }

    @ExceptionHandler(StatusNotFoundException.class)
    private ResponseEntity<RestErrorMessage> statusNotFoundHandler(StatusNotFoundException s){
        RestErrorMessage mensagemErro = new RestErrorMessage(HttpStatus.NOT_FOUND, s.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemErro);
    }
    
    @ExceptionHandler(PedidoNotFoundException.class)
    private ResponseEntity<RestErrorMessage> pedidoNotFoundHandler(PedidoNotFoundException pedido){
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, pedido.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(StatusException.class)
    private ResponseEntity<RestErrorMessage> StatusHandler(StatusException status){
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, status.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(EstoqueInsuficienteException.class)
    private ResponseEntity<RestErrorMessage> estoqueHandler(EstoqueInsuficienteException estoque){
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, estoque.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}