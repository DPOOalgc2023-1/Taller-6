package logica;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Restaurante {
	private Pedido pedidoencurso;
	private ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
	private ArrayList <Producto> menuBase = new ArrayList<Producto>();
	private ArrayList <Producto> comboinfo = new ArrayList<Producto>();
	private ArrayList <Producto> bebidas = new ArrayList<Producto>();
	HashMap<Integer, ArrayList<String>> facturas = new HashMap<Integer, ArrayList<String>>();
	private HashSet<String> nombresProductos = new HashSet<>();
	private HashSet<String> nombresIngredientes = new HashSet<>();

	
	public Restaurante() {
	}
	
	public void iniciarPedido(String nombreCliente, String direccionCliente) {
		pedidoencurso = new Pedido(nombreCliente, direccionCliente);
		
		//inicia el pedido con el nombre y direccion del cliente
	}
	
	public void cerrarYGuardarPedido() {
	    
		    System.out.println("Pedido cerrado y factura guardada.");
		    pedidoencurso.guardarFactura(new File("data/factura"));
	
		    ArrayList<Producto> prod = pedidoencurso.getProductos();
		    ArrayList<String> nombres = new ArrayList<String>();
		    
		    for (Producto producto: prod) {
		    	String nombre = producto.getNombre();
		    	nombres.add(nombre);
		    }
		    
		    int id = pedidoencurso.getIdPedido();
		    facturas.put(id, pedidoencurso.getProductosStr(nombres));
		    if (facturas.size() > 1) {
		    	Set<Integer> llaves = facturas.keySet();
		    	for (int num: llaves) {
		    		if (id != num) {
		    			if(facturas.get(id).equals(facturas.get(num))) {
		    				System.out.println("Existe un pedido exactamente como este.");
		    			}
		    		}
		    	}
		    }
		    
		    pedidoencurso = null; // Reiniciar el pedido en curso para el próximo cliente

	}
	
	public void consultarInfoPedidoPorId(String id)
	{
		File pedido = new File("data/factura/" + id + ".txt");
		try
		{
			FileReader fr = new FileReader(pedido);//Se abre el archivo
			BufferedReader br = new BufferedReader(fr);//Se lee el archivo
			
			String linea;
			System.out.println("Pedido: ");
			while((linea = br.readLine())!= null) 
			{
				System.out.println(linea);
			}
			br.close();
			fr.close();
			
		}
		catch(IOException e) 
		{
			System.out.println("Ocurrio un error.");
		    e.printStackTrace();
		}
	}
	
	public Pedido getPedidoEnCurso() {
		return pedidoencurso;
		
	}
	
	public ArrayList<Producto> getMenuBase(){
		return menuBase;
	}
	
	public ArrayList<Ingrediente> getIngredientes(){
		return ingredientes;
	} 
	
	public ArrayList<Producto> getCombo(){
		return comboinfo;
	}
	public ArrayList<Producto> getBebidas(){
		return bebidas;
	}
	
	//Se llama a cada funcion de carga para cargar la informacion de cada archivo. 
	public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos, File archivoBebidas) throws ProductoRepetidoException, IngredienteRepetidoException {
			cargarMenu(archivoMenu);
			cargarIngredientes(archivoIngredientes);
			cargarCombos(archivoCombos);
			cargarBebidas(archivoBebidas);
	}
	
	//Se recorre la arrayList con la informacion de los productos para mostrar el menu. 
	public void imprimirInformacionRestaurante(ArrayList<Producto> menuBase, ArrayList<Ingrediente> ingredientes, 
			ArrayList<Producto> combos, ArrayList<Producto> bebidas) {
		System.out.println("------------------------------------");
		 System.out.println("|               Menu                |");
		 System.out.println("------------- Productos ------------");
		 int n = 1;
		 for (Producto producto : menuBase) 
		 {
			 System.out.println(n+". "+producto.getNombre()+ " - $" +  producto.getPrecio()+"   Cal: "+producto.getCalorias());
			 n+=1;
		 }
		 
		 int beb =1;
		 System.out.println("\n--- Bebidas ---");
		 for (Producto bebida: bebidas) 
		 {
			 System.out.println(beb + ". " + bebida.getNombre()+ " - $" + bebida.getPrecio() + "   Cal: "+bebida.getCalorias());
			 beb += 1;
		 }
		 
		 int ing = 1;
		 System.out.println("\n--- Ingredientes adicionales ---");
		 for (Ingrediente ingrediente : ingredientes) 
		 {
			 System.out.println(ing + ". " + ingrediente.getNombre() + " -$" + ingrediente.getCostoAdicional()+"   Cal: "+ingrediente.getCalorias());
			 ing +=1;
		 }
		 
		 int com = 1;
		 System.out.println("\n--- Combos ---");
		 for (Producto combo: combos) 
		 {
			 System.out.println(com+ ". " + combo.getNombre() + " - $" + combo.getPrecio());
			 com += 1;
		 }
		 
		 
	}

	private void cargarIngredientes(File archivoIngredientes) throws IngredienteRepetidoException {
		try (FileReader fr = new FileReader(archivoIngredientes)){
			BufferedReader bf = new BufferedReader(fr);
			//Se lee la informacion del archivo
			
			String bfRead;
			while ((bfRead = bf.readLine()) != null) { //Verifica que hay datos
				
				String[] datosIngrediente = bfRead.split(";"); //Separa en ;
				String nombre = datosIngrediente[0]; //Se toman los nombre de los ingr
				int costoAdicional = Integer.parseInt(datosIngrediente[1]); //Se toman los precios
				int calorias = Integer.parseInt(datosIngrediente[2]);
				Ingrediente ingrediente = new Ingrediente(nombre, costoAdicional, calorias); //Se crea el ingr
				if (nombresIngredientes.contains(nombre)) {
					throw new IngredienteRepetidoException(nombre);
				}
				nombresIngredientes.add(nombre);
				ingredientes.add(ingrediente);  //Se agrega a la arrayl
			
			}
			bf.close();
			fr.close();
			}
			catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	private void cargarBebidas(File archivoBebidas) {
		try {
			FileReader fr = new FileReader(archivoBebidas);
			BufferedReader bf = new BufferedReader(fr);
			//Se lee la informacion del archivo
			
			String bfRead;
			while ((bfRead = bf.readLine()) != null) { //Verifica que hay datos
				
				String[] datosIngrediente = bfRead.split(";"); //Separa en ;
				String nombre = datosIngrediente[0]; //Se toman los nombre de los ingr
				int precioBase = Integer.parseInt(datosIngrediente[1]); //Se toman los precios
				int calorias = Integer.parseInt(datosIngrediente[2]);
				ProductoMenu prodMenu = new ProductoMenu(nombre, precioBase, calorias); //Se crea el ingr
				bebidas.add(prodMenu);
				
				}
			bf.close();
			fr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void cargarMenu(File archivoMenu) throws ProductoRepetidoException {
			try (FileReader fr = new FileReader(archivoMenu)) {
				BufferedReader bf = new BufferedReader(fr);
				//Se lee la informacion del archivo
				
				String bfRead;
				while ((bfRead = bf.readLine()) != null) { //Verifica que hay datos
					
					String[] datosIngrediente = bfRead.split(";"); //Separa en ;
					String nombre = datosIngrediente[0]; //Se toman los nombre de los ingr
					int precioBase = Integer.parseInt(datosIngrediente[1]); //Se toman los precios
					int calorias = Integer.parseInt(datosIngrediente[2]);
					ProductoMenu prodMenu = new ProductoMenu(nombre, precioBase, calorias); //Se crea el ingr
					if (nombresProductos.contains(nombre) ){
						throw new ProductoRepetidoException(nombre);
					}
					nombresProductos.add(nombre);
					menuBase.add(prodMenu);
					
					}
				bf.close();
				fr.close();
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	
	private void cargarCombos(File archivoCombos) {
		try {
			FileReader fr = new FileReader(archivoCombos);
			BufferedReader bf = new BufferedReader(fr);
			//Se lee la informacion del archivo
			
			String bfRead;
			while ((bfRead = bf.readLine()) != null) { //Verifica que hay datos
				String[] datosCombo = bfRead.split(";"); //Separa en ;
				String nombreCombo = datosCombo[0]; //Se toma el nombre del combo
				String desc = datosCombo[1];	
				Double descuento = Double.parseDouble(desc.substring(0, desc.length() - 1)); //Se toma el % de descuento 
				Combo combo = new Combo(nombreCombo, descuento);
				
				//comboinfo.addAll(item1);itemsCombo.add(item2);itemsCombo.add(item3);
				
				for (int i = 2; i<5; i++) {
					String itemcombo = datosCombo[i];
					for (Producto item : menuBase) 
					{
						if ( item.getNombre().equals(itemcombo)) {
							combo.agregarItemACombo(item);
						}
					}
				}
				comboinfo.add(combo);
				}
			bf.close();
			fr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
