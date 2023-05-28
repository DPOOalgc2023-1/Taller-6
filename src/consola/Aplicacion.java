package consola;
import logica.Restaurante;
import logica.Ingrediente;
import logica.IngredienteRepetidoException;
import logica.Pedido;
import logica.PedidoException;
import logica.Producto;
import logica.ProductoAjustado;
import logica.ProductoMenu;
import logica.ProductoRepetidoException;

import java.io.File;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Aplicacion 
{
	private static Restaurante restaurante= new Restaurante();

	public  void ejecutarAplicacion() 
	{
		System.out.println("Restaurante\n");
		
		boolean continuar = true;
		while (continuar)
		{
			try
			{
				mostrarMenu();
				int opc = Integer.parseInt(input("Por favor seleccione una opción"));	
				if (opc == 1)
					ejecutarMostrarMenuRest();
				else if (opc == 2)
					iniciarPedido();
				else if (opc == 3)
					agregarElemento();
				else if (opc == 4)
					agregarIngrediente();
				else if (opc == 5)
					cerrarPedido();
				else if (opc == 6)
					conseguirPorId();
				else if (opc == 7)
				{
					System.out.println("Saliendo de la aplicación ...");
					continuar = false;
				}
				else if (restaurante == null){System.out.println("Para poder ejecutar esta opción primero debe cargar el menu del restaurante.");}
				else{System.out.println("Por favor seleccione una opción válida.");}
			}
			catch (NumberFormatException e){System.out.println("Debe seleccionar uno de los números de las opciones.");}
		}	
	}
	
	public static void mostrarMenu() {
		System.out.println("\nOpciones del restaurante\n");
		System.out.println("1. Mostrar el menu del restaurante.");
		System.out.println("2. Iniciar un pedido.");
		System.out.println("3. Agregar un producto/combo al pedido.");
		System.out.println("4. Agregar/Eliminar un ingrediente a un producto. ");
		System.out.println("5. Cerrar el pedido y guardar su factura.");
		System.out.println("6. Consultar la informacion de un pedido con su id.");
		System.out.println("7. Salir.\n");
	}
	
	private void ejecutarMostrarMenuRest() {
	System.out.println("| MENÚ RESTAURANTE |");
	// Carga la info del restaurante a partir de los archivos txt
		try{
		 restaurante.cargarInformacionRestaurante(new File("data/ingredientes.txt"), new File("data/menu.txt"), new File("data/combos.txt"), new File("data/bebidas.txt"));
		 ArrayList<Producto> menuBase = restaurante.getMenuBase();
		 ArrayList<Ingrediente> ingredientes = restaurante.getIngredientes();
		 ArrayList<Producto> combos = restaurante.getCombo();
		 ArrayList<Producto> bebidas = restaurante.getBebidas();

		 restaurante.imprimirInformacionRestaurante(menuBase, ingredientes, combos, bebidas);
		}catch (ProductoRepetidoException e) {
			System.out.println(e.getMessage());
		} catch (IngredienteRepetidoException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void iniciarPedido() {
		System.out.println("Iniciando su pedido...\n");
		String nombreCliente = input("Ingrese su nombre");
		String direccionCliente = input("Ingrese la dirección del pedido");
		
		restaurante.iniciarPedido(nombreCliente, direccionCliente);
	}

	private void agregarElemento() {
		Producto elemento = null;

		int tipo = Integer.parseInt(input("Desea agregar un: \n1. Producto\n2. Combo\n3. Bebida"));
		if (tipo == 1) {
			System.out.println("Puede adicionar los siguientes productos: ");
			ArrayList<Producto> menuBase = restaurante.getMenuBase();
		
			int n= 1;
			for (Producto p : menuBase) {
				 System.out.println("| " + n + "."+p.getNombre()+ "- $" +  p.getPrecio() + "  Cal: " + p.getCalorias() + "  |");
				 n+=1;
			 }
			int numproducto = Integer.parseInt(input("Elija el numero del producto que deseea adicionar a su pedido: "));
			System.out.println(numproducto);
			System.out.println(menuBase.get(numproducto -1).getNombre());
			System.out.println(menuBase.get(numproducto).getNombre());
			if (0 <= numproducto && numproducto <= n) {
				elemento = menuBase.get(numproducto -1);
			}
		}
		else if (tipo == 2) 
		{
			System.out.println("Puede adicionar los siguientes combos: ");
			ArrayList<Producto> combos = restaurante.getCombo();
			
			int n= 1;
			for (Producto combo: combos) {
				 System.out.println(n+" . "+combo.getNombre() + "- $" + combo.getPrecio());
				 n+=1;
			 }				
			int numproducto = Integer.parseInt(input("Elija el numero del combo que deseea adicionar a su pedido: "));
			if (0 <= numproducto && numproducto <= n) {
				elemento = combos.get(numproducto -1);
			}
		}
		else if (tipo == 3)
		{
			System.out.println("Puede adicionar las siguientes bebidas: ");
			ArrayList<Producto> bebidas = restaurante.getBebidas();
			int n = 1; 
			for (Producto b: bebidas) {
				 
				 System.out.println(n+" . " + b.getNombre() + "- $" + b.getPrecio() + "  Cal: " + b.getCalorias());
				 n+=1;
			 }			
			int numproducto = Integer.parseInt(input("Elija el numero de la bebida que deseea adicionar a su pedido: "));
			if (0 <= numproducto && numproducto <= n) {
				elemento = bebidas.get(numproducto -1);
			}				
		}
		Pedido pedidoActual = restaurante.getPedidoEnCurso();
		try{
		pedidoActual.agregarProducto(elemento);
		} catch (PedidoException e) {
			System.out.println(e.getMessage());
		}
			
	}
	
	private void agregarIngrediente() {
		Pedido pedidoActual = restaurante.getPedidoEnCurso();
		ArrayList<Ingrediente> ingredientes = restaurante.getIngredientes();
		ProductoMenu prodAModificar = null;
		Ingrediente adicion = null;
		/////
		System.out.println("Puede modificar los siguientes productos: \nRecuerde que no se puede agregar ingredientes a los combos");	
		int n = 1;
		ArrayList<String> itemsProducto = new ArrayList<>();
		for (Producto producto: pedidoActual.getProductos()) {
			if (producto instanceof ProductoMenu || producto instanceof ProductoAjustado) {
				System.out.println(n + "." + producto.getNombre() + " - $" + producto.getPrecio());
				itemsProducto.add(producto.getNombre());
				n+=1;
				}
			}
		
		int prod = Integer.parseInt(input("Que producto desea modificar: "));		
		String prodSel = itemsProducto.get(prod-1);
		for (Producto producto2: pedidoActual.getProductos()) {
			if (producto2.getNombre() == prodSel) {
				prodAModificar = (ProductoMenu) producto2;
				}
			}
		
		ArrayList<String> itemsIngredientes = new ArrayList<>();
		System.out.println("Puede agregar/eliminar los siguientes ingredientes");
		int opc =1;
		for (Ingrediente ingrediente : ingredientes) {
			System.out.println(opc +". " + ingrediente.getNombre() + "  $" + ingrediente.getCostoAdicional());
			itemsIngredientes.add(ingrediente.getNombre());
			opc +=1;
			}
		int adicionareliminar = Integer.parseInt(input("Desea (1)Adicionar (2)Eliminar un ingrediente"));
		int ingr = Integer.parseInt(input("Que ingrediente desea adicionar/eliminar"));
		String ingrSel = itemsIngredientes.get(ingr-1);
		for (Ingrediente ingrediente2 : ingredientes) {
			if (ingrediente2.getNombre() == ingrSel) {
				adicion = ingrediente2;
				}
			}
		pedidoActual.eliminarProducto(prodAModificar);
		ProductoAjustado productoAjustado = new ProductoAjustado(prodAModificar);
		
		if (adicionareliminar == 1) 
			productoAjustado.agregarIngrediente(adicion);
		else 
			productoAjustado.eliminarIngrediente(adicion);
		try{
		pedidoActual.agregarProducto(productoAjustado);
		} catch (PedidoException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	private void cerrarPedido() {
		System.out.println("Finalizando su pedido...");
		restaurante.cerrarYGuardarPedido();
	}
			
	private void conseguirPorId() {
		String id = input("Ingrese el id del pedido que desea consultar: ");
		restaurante.consultarInfoPedidoPorId(id);
	}
	
	
	public String input(String mensaje) {
		try {
			System.out.println(mensaje + ":");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
	

	
	/////////////////////
	public static void main(String[] args)  {
		
		Aplicacion app = new Aplicacion();
		app.ejecutarAplicacion();
	}
}
