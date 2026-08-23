package lojamercado.mercado.exceptions;

public class PedidoNotFoundException extends RuntimeException{
    
    public PedidoNotFoundException(String msg){
        super(msg);
    }
}
