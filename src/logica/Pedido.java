package logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Pedido {
	
	private static int numeroPedidos = 0;  //contador de pedidos para sacar su id
	
	private int idPedido;					//id pedido
	private String nombreCliente;
	private String direccionCliente;
	private ArrayList<Producto> productos = new ArrayList<Producto>();
	public ArrayList<String> productosStr = new ArrayList<String>();
	
	public Pedido(String nombreCliente, String direccionCliente) {
		this.nombreCliente = nombreCliente;
		this.direccionCliente = direccionCliente;
		this.idPedido = + numeroPedidos;
		this.productos = new ArrayList<>();
	}
	
	//
	
	public int getIdPedido() {
		return idPedido ;
	}
	
	public String getNombreCliente() {
		return nombreCliente;
	}
	
	public String getDireccionCliente() {
		return direccionCliente;
	}
	//
	
	public void agregarProducto(Producto nuevoItem) throws PedidoException {
		if (nuevoItem.getPrecio()+getPrecioNetoPedido() > 150000){
			throw new PedidoException(nuevoItem.getNombre(), getPrecioNetoPedido());
		}
		productos.add(nuevoItem);
	}
	
	public void eliminarProducto(Producto nuevoItem) {
		productos.remove(nuevoItem);
	}
	
	public ArrayList<Producto> getProductos() {
		return productos;
	}
	
	public ArrayList<String> getProductosStr (ArrayList<String> productosStr){	
		return this.productosStr;
	}
	
	//
	public int getPrecioNetoPedido() {
		return getPrecioTotalPedido() + getPrecioIVApedido();
		
	}
	
	//
	public int getPrecioTotalPedido() {
		int precioNeto = 0;
		for (Producto producto : productos) {
			precioNeto += producto.getPrecio();
		}
		return precioNeto;
	}
	
	//
	public int getPrecioIVApedido() {
		int precioNeto = getPrecioTotalPedido();
		return (int) Math.round(precioNeto * 0.19);
	}
	
	//
	public String generarTextoFactura() {
		StringBuilder sb = new StringBuilder();
		sb.append("Factura pedido #" + idPedido + "\n");	
		sb.append("Cliente: " + nombreCliente + "\n");
		sb.append("Dirección: " + direccionCliente + "\n");
		sb.append("Detalle del pedido; \n");
		
		for (Producto producto : productos) {
			sb.append("- " + producto.generarTextoFactura()+ "\n");
		}
		
		sb.append("----- Precio total del pedido: $"+ getPrecioTotalPedido()+" -----\n");
		sb.append("----- Impuestos (19%) $" + getPrecioIVApedido()+" -----\n");
		sb.append("----- Precio Neto total $"+ getPrecioNetoPedido()+" -----\n");
				
		return sb.toString();
	}
	
	//
	public void guardarFactura(File archivo) {
		try {
			
			FileWriter fw = new FileWriter(archivo + "/" + idPedido + ".txt");
            PrintWriter escritor = new PrintWriter(fw);
            fw.write(generarTextoFactura());
            String factura = generarTextoFactura();
            System.out.println(factura);
            escritor.close();
            System.out.println("Factura guardada exitosamente en " + archivo.getAbsolutePath());
            numeroPedidos ++;
        } catch (FileNotFoundException e) {
            System.out.println("No se pudo guardar la factura. Archivo no encontrado.");
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	
}
