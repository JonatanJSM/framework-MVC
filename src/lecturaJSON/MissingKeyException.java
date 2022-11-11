package lecturaJSON;

public class MissingKeyException extends Exception{
    public MissingKeyException(){
        super("Al archivo de configuración le falta una configuración");
    }
}
