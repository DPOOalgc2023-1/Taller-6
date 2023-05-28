package logica;

public class ProductoRepetidoException extends HamburguesaException{

    public ProductoRepetidoException(String producto) {

        super("Se encontró un problema en los datos. " + "\n" + "El producto " + producto + " ya había sido cargado anteriormente." + "\n");
    }
    
}
