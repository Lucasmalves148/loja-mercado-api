package lojamercado.mercado.exceptions;

public class EstoqueInsuficienteException extends RuntimeException{
    
    public EstoqueInsuficienteException(String msg){
        super(msg);
    }
}
