package lojamercado.mercado.enumerate;

import lojamercado.mercado.exceptions.StatusNotFoundException;

public enum Status{

    PENDENTE(1),
    PAGO(2),
    ENVIADO(3),
    ENTREGUE(4),
    CANCELADO(5);


    public static Status valorDoStatus(int valor){
        for(Status s : Status.values()){
            if(s.codigo == valor){
                return s;
            }
        }   
        throw new StatusNotFoundException("Não foi encontrado nenhum status com este código");
    }

    private final int codigo;

    Status(int codigo){
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}