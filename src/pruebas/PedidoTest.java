package pruebas;
import logica.Pedido;
import logica.PedidoException;
import logica.ProductoMenu;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PedidoTest {
    private Pedido pedido;

    @BeforeEach
    public void setUp() {
        pedido = new Pedido("Juan", "Calle 123");
    }

    @Test
    public void testAgregarProducto() {
        ProductoMenu producto1 = new ProductoMenu("especial", 24000, 52);
        try {
            pedido.agregarProducto(producto1);
        } catch (PedidoException e) {
            e.printStackTrace();
        }
        assertEquals(1, pedido.getProductos().size());
        ProductoMenu producto2 = new ProductoMenu("especial2", 150000, 52);
        assertThrows(PedidoException.class, () -> {
            pedido.agregarProducto(producto2);
        });
    }

    @Test
    public void testGetProductosStr() {
        ArrayList<String> productos = new ArrayList<>();
        productos.add("Producto A");
        productos.add("Producto B");
        productos.add("Producto C");

        pedido.productosStr = productos;

        ArrayList<String> productosObtenidos = pedido.getProductosStr(productos);
        Assertions.assertEquals(productos, productosObtenidos);
    }

    @Test
    public void testGetPrecioNetoPedido() {
        ProductoMenu producto1 = new ProductoMenu("especial", 24000, 52);
        try {
            pedido.agregarProducto(producto1);
        } catch (PedidoException e) {
            e.printStackTrace();
        }
        int preciototal = pedido.getPrecioTotalPedido();
        int precioiva = pedido.getPrecioIVApedido();
        assertEquals(preciototal + precioiva, pedido.getPrecioNetoPedido());
    }

    @Test
    public void testGetPrecioTotalPedido() {
        ProductoMenu producto1 = new ProductoMenu("especial", 24000, 52);
        try {
            pedido.agregarProducto(producto1);
        } catch (PedidoException e) {
            e.printStackTrace();
        }
        assertEquals(24000, pedido.getPrecioTotalPedido());
    }

    @Test
    public void testGetPrecioIVApedido() {
        ProductoMenu producto1 = new ProductoMenu("especial", 24000, 52);
        try {
            pedido.agregarProducto(producto1);
        } catch (PedidoException e) {
            e.printStackTrace();
        }
        assertEquals(4560, pedido.getPrecioIVApedido());
    }

    @Test
    public void testEliminarProducto() throws PedidoException {
        ProductoMenu producto1 = new ProductoMenu("especial", 24000, 52);
        pedido.agregarProducto(producto1);
        assertEquals(1, pedido.getProductos().size());
        pedido.eliminarProducto(producto1);
        assertEquals(0, pedido.getProductos().size());
    }

    @Test
    public void testGetIdPedido() {
        assertEquals(1, pedido.getIdPedido());
    }

    @Test
    public void testGetNombreCliente() {
        assertEquals("Juan", pedido.getNombreCliente());
    }

    @Test
    public void testGetDireccionCliente() {
        assertEquals("Calle 123", pedido.getDireccionCliente());
    }

    @Test
    public void testGetProductos() {
        ProductoMenu producto1 = new ProductoMenu("especial", 24000, 52);

        try {
            pedido.agregarProducto(producto1);
        } catch (PedidoException e) {
            e.printStackTrace();
        }

        assertTrue(pedido.getProductos().contains(producto1));
    }

    @Test
    public void testGenerarTextoFactura(){
        ProductoMenu producto1 = new ProductoMenu("especial", 24000, 52);
        try {
            pedido.agregarProducto(producto1);
        } catch (PedidoException e) {
            e.printStackTrace();
        }
        String texto = "Factura pedido #0\n" +
                "Cliente: Juan\n" +
                "Dirección: Calle 123\n" +
                "Detalle del pedido; \n" +
                "- especial  $24000 cal: 52\n" +
                "----- Precio total del pedido: $24000 -----\n" +
                "----- Impuestos (19%) $4560 -----\n" +
                "----- Precio Neto total $28560 -----\n";
                        assertEquals(texto, pedido.generarTextoFactura());
    }

    @Test
    public void testGuardarFactura() throws IOException, PedidoException {
        
        ProductoMenu productoMenu = new ProductoMenu("especial", 24000, 52);
        pedido.agregarProducto(productoMenu);

        Path tempDirectory = Files.createTempDirectory("facturas");

        File archivo = new File(tempDirectory.toString());
        pedido.guardarFactura(archivo);

        File facturaGuardada = new File(tempDirectory.toString() + "/" + pedido.getIdPedido() + ".txt");
        Assertions.assertTrue(facturaGuardada.exists());
    }
 

    
}
