package util;

// "extends Exception" para forçar tratar o try/catch
public class NegocioException extends Exception{
    
    // Construtor que recebe e "printa" a mensagem
    public NegocioException(String msg) {
        super(msg);
    }

}
