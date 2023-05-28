package logica;

import java.util.ArrayList;

public class ProductoAjustado implements Producto {
	
	private ProductoMenu base;
	private ArrayList<Ingrediente> agregados = new ArrayList<Ingrediente>();
	private ArrayList<Ingrediente> eliminados = new ArrayList<Ingrediente>();
	
	
	public ProductoAjustado(ProductoMenu base) {
		this.base = base;
		this.agregados = new ArrayList<Ingrediente>();
		this.eliminados = new ArrayList<Ingrediente>();
	}
	
	//
	
	public void agregarIngrediente(Ingrediente ingrediente) {
		this.agregados.add(ingrediente);
	}
	
	public void eliminarIngrediente(Ingrediente ingrediente) {
		this.eliminados.add(ingrediente);
	}
	
	public String getNombre() {
		String nombre = base.getNombre();
		return (nombre + " modificado");
		
	}
	
	public int getCalorias() {
		return base.getCalorias();
	}
	
	//
	
	public int getPrecio() {
		int preciobase = base.getPrecio();
		int precioAgregadoTotal = 0;
		
		for (Ingrediente ing : agregados) {
			int precioAgregado = ing.getCostoAdicional();
			precioAgregadoTotal += precioAgregado;
		}
		
		return preciobase + precioAgregadoTotal;
		
	}
	
	//
	
	public String generarTextoFactura() {
		StringBuilder sb = new StringBuilder();
		sb.append(base.getNombre()+" modificado\n");
		if (agregados != null) {
			sb.append("   | Adiciones: \n");
			
			for (Ingrediente agregado : agregados) {
				System.out.println(agregado.getNombre());
				sb.append("    - " + agregado.getNombre()+" -  $" + agregado.getCostoAdicional()+" Cal: " + agregado.getCalorias() + "\n");
			}}
		
		if  (eliminados != null) {
			sb.append("   | Ingredientes eliminados\n");
			for (Ingrediente eliminado : eliminados) {
				sb.append("    - " + eliminado.getNombre()+"\n");
			}
		}
		return sb.toString();
		
	}
}
