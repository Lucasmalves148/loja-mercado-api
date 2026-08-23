package lojamercado.mercado.exceptions;

public class ProdutoNotFoundException extends RuntimeException{

    public ProdutoNotFoundException(String msg){
        super(msg);
    }
}
