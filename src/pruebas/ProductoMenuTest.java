package pruebas;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ProductoMenu;

public class ProductoMenuTest {
    private ProductoMenu producto;

    @BeforeEach
    public void setUp() {
        producto = new ProductoMenu("especial", 24000, 52);
    }

    @Test
    public void testGetCalorias() {
        assertEquals(52, producto.getCalorias());
    }

    @Test
    public void testGenerarTextoFactura(){
        assertEquals("especial  $24000 cal: 52", producto.generarTextoFactura());
    }
    
}
