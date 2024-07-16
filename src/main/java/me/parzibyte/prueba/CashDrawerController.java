package me.parzibyte.prueba;

import me.parzibyte.sistemaventasspringboot.ProductoVendido;
import me.parzibyte.sistemaventasspringboot.ProductosVendidosRepository;
import me.parzibyte.sistemaventasspringboot.Venta;
import me.parzibyte.sistemaventasspringboot.VentasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/drawer")
public class CashDrawerController {

    @Autowired
    private CashDrawerService cashDrawerService;

    @Autowired
    private VentasRepository ventasRepository;

    @Autowired
    private ProductosVendidosRepository productosVendidosRepository;

    private static final Logger logger = LoggerFactory.getLogger(CashDrawerController.class);

    @GetMapping("/open")
    public String openDrawer() {
        try {
            cashDrawerService.openDrawer();
            return "Cajón abierto exitosamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al abrir el cajón: " + e.getMessage();
        }
    }

    @GetMapping("/printTest")
    public String printTest() {
        try {
            cashDrawerService.printTest();
            return "Impresión de prueba realizada exitosamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al imprimir: " + e.getMessage();
        }
    }

    @GetMapping("/printFormattedInvoice/{ventaId}")
    public String printFormattedInvoice(@PathVariable Integer ventaId) {
        try {
            // Recuperar la venta por ID
            Venta venta = ventasRepository.findByIdAsInteger(ventaId).orElseThrow(() -> new Exception("Venta no encontrada"));
            // Recuperar los productos vendidos asociados a la venta
            List<ProductoVendido> productosVendidos = productosVendidosRepository.findByVenta(venta);
            
            // Calcular el total de la venta
            double totalVenta = productosVendidos.stream()
                    .mapToDouble(p -> p.getPrecio() * p.getCantidad())
                    .sum();

            // Registro para verificar los valores antes de imprimir
            logger.info("Productos vendidos: {}", productosVendidos);
            logger.info("Total de la venta: {}", totalVenta);

            cashDrawerService.printFormattedInvoice(productosVendidos, totalVenta);
            return "Impresión de factura realizada exitosamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al imprimir: " + e.getMessage();
        }
    }
}
