package logica;

public class PedidoException extends Exception{
    public PedidoException (String producto, int precio) {
        super("\n"+"No se puede agregar el producto " + producto + " por que el precio del pedido supera los 150.000 pesos." + "\n" + "Precio actual del pedido: " + precio + "\n");
    }
}
