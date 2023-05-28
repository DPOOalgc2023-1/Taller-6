package logica;

public class IngredienteRepetidoException extends HamburguesaException{

    public IngredienteRepetidoException(String ingrediente) {
        super("Se encontró un problema en los datos. " + "\n" + "El Ingrediente " + ingrediente + " ya había sido cargado anteriormente." + "\n");
        //TODO Auto-generated constructor stub
    }
    
}
