package pruebas;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Ingrediente;
import logica.ProductoAjustado;
import logica.ProductoMenu;

public class ProductoAjustadoTest {
    private ProductoMenu productoMenu;
    private ProductoAjustado productoAjustado;

    @BeforeEach
    public void setUp() {
        productoMenu = new ProductoMenu("ProductoMenu", 100000, 100); 
        productoAjustado = new ProductoAjustado(productoMenu);
        Ingrediente ingrediente1 = new Ingrediente("Ingrediente1", 10, 50);
        Ingrediente ingrediente2 = new Ingrediente("Ingrediente2", 20, 100);
        productoAjustado.agregarIngrediente(ingrediente1);
        productoAjustado.agregarIngrediente(ingrediente2);
        Ingrediente ingredienteEliminado = new Ingrediente("IngredienteEliminado", 5, 25);
        productoAjustado.eliminarIngrediente(ingredienteEliminado);
    }

    @Test
    public void testAgregarIngrediente() {
        Ingrediente ingrediente = new Ingrediente("NuevoIngrediente", 15, 75);
        productoAjustado.agregarIngrediente(ingrediente);
    
        // Verificar que el ingrediente se agregó correctamente
        String textoFactura = productoAjustado.generarTextoFactura();
        Assertions.assertTrue(textoFactura.contains(ingrediente.getNombre()));
        Assertions.assertTrue(textoFactura.contains("Adiciones"));
    }
    
    @Test
    public void testEliminarIngrediente() {
        Ingrediente ingrediente = new Ingrediente("IngredienteExistente", 10, 50);
        productoAjustado.agregarIngrediente(ingrediente);
        productoAjustado.eliminarIngrediente(ingrediente);

        // Verificar que el ingrediente se eliminó correctamente
        String textoFactura = productoAjustado.generarTextoFactura();
        Assertions.assertTrue(textoFactura.contains("Ingredientes eliminados"));
        Assertions.assertTrue(textoFactura.contains("- " + ingrediente.getNombre()));
    }
    

    @Test
    public void testGetNombre() {
        String nombreEsperado = "ProductoMenu modificado";
        Assertions.assertEquals(nombreEsperado, productoAjustado.getNombre());
    }

    @Test
    public void testGetCalorias() {
        int caloriasEsperadas = 100; 
        Assertions.assertEquals(caloriasEsperadas, productoAjustado.getCalorias());
    }

    @Test
    public void testGetPrecio() {
        int precioEsperado = 100030; // Reemplaza con el valor esperado
        Assertions.assertEquals(precioEsperado, productoAjustado.getPrecio());
    }

    @Test
    public void testGenerarTextoFactura() {
        String textoFacturaEsperado = "ProductoMenu modificado\n" +
                "   | Adiciones: \n" +
                "    - Ingrediente1 -  $10 Cal: 50\n" +
                "    - Ingrediente2 -  $20 Cal: 100\n" +
                "   | Ingredientes eliminados\n" +
                "    - IngredienteEliminado\n";
        Assertions.assertEquals(textoFacturaEsperado, productoAjustado.generarTextoFactura());
    }
}
