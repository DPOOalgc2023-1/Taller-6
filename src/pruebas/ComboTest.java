package pruebas;
import logica.Combo;
import logica.ProductoMenu;


import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ComboTest {
    private Combo combo;

    @BeforeEach
    public void setUp() {
        combo = new Combo("Combo1", 10);
    }

    @Test
    public void testAgregarItemACombo(){
        ProductoMenu producto1 = new ProductoMenu("especial", 24000, 52);
        combo.agregarItemACombo(producto1);
        assertEquals(1, combo.getItemsCombo().size());
    }

    @Test
    public void testGetCalorias(){
        ProductoMenu producto1 = new ProductoMenu("especial", 24000, 52);
        combo.agregarItemACombo(producto1);
        assertEquals(0, combo.getCalorias());
    }

    @Test
    public void testGetDescuento(){
        assertEquals(10, combo.getDescuento());
    }

    @Test
    public void testGetItemsCombo(){
        ProductoMenu producto1 = new ProductoMenu("especial", 24000, 52);
        combo.agregarItemACombo(producto1);
        assertEquals(1, combo.getItemsCombo().size());
    }

    @Test
    public void testGenerarTextoFactura(){
        assertEquals("Combo1  - descuento de: 10.0%", combo.generarTextoFactura());
    }
    
}
