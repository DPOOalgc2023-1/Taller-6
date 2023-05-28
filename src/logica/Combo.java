package logica;

import java.util.ArrayList;

public class Combo implements Producto{
	
	private double descuento;
	private String nombreCombo;
	private ArrayList<Producto>	itemsCombo = new ArrayList<Producto>();
	
	
	public Combo(String nombre, double descuento) {
		  this.nombreCombo = nombre;
		  this.descuento = descuento;
	      this.itemsCombo = new ArrayList<Producto>();
	}
	
	//
	
	public int getCalorias() {
		return 0;
	}
	public double getDescuento() {
		return descuento;
	}
	
	
	public void agregarItemACombo(Producto itemCombo) {
		this.itemsCombo.add(itemCombo);
	}
	
	public ArrayList<Producto> getItemsCombo(){
		return itemsCombo;
	}
	
	//
	
	public int getPrecio() {
		int precioFinal = 0;
		for (Producto item : itemsCombo) {
			int precioitem = item.getPrecio();
			double desc;
			desc = this.descuento;
			double precioitemdesc =  (precioitem*(100-desc)/100);
			precioFinal += precioitemdesc;
			
		}
				
		return precioFinal;
		
	}
	
	//
	
	public String generarTextoFactura() {
		return (nombreCombo + "  - descuento de: "+ descuento +"%");
		
	}
	
	//
	
	public String getNombre() {
		return nombreCombo;
	}
}
