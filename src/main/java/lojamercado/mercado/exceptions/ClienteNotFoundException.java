package lojamercado.mercado.exceptions;


public class ClienteNotFoundException extends RuntimeException{
    
    public ClienteNotFoundException (String msg){
        super(msg);
    }
    
}