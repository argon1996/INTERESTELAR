package me.parzibyte.prueba;

import me.parzibyte.sistemaventasspringboot.ProductoVendido;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CashDrawerService {

    private static final String PRINTER_URL = "http://localhost:9100"; // Dirección redirigida por el túnel SSH
    private static final Logger logger = LoggerFactory.getLogger(CashDrawerService.class);

    public void openDrawer() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(PRINTER_URL + "/open-drawer", null, String.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new Exception("Error opening drawer: " + response.getBody());
        }
    }

    public void printTest() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String testReceipt = "=== Test Receipt ===\nItem 1    1.00\nItem 2    2.00\nTotal    3.00\n";
        ResponseEntity<String> response = restTemplate.postForEntity(PRINTER_URL + "/print", testReceipt, String.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new Exception("Error printing: " + response.getBody());
        }
    }

    public void printFormattedInvoice(List<ProductoVendido> productosVendidos, double totalVenta) throws Exception {
        StringBuilder sb = new StringBuilder();

        // Obtener fecha y hora actuales
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaHoraActual = sdf.format(new Date());

        // Comandos ESC/POS para formatear la factura
        sb.append((char) 0x1B).append((char) 0x40); // Reset printer
        sb.append((char) 0x1B).append((char) 0x61).append((char) 0x01); // Center align
        sb.append("Interestelar\n\n");

        sb.append((char) 0x1B).append((char) 0x61).append((char) 0x00); // Left align
        sb.append("Fecha: ").append(fechaHoraActual.split(" ")[0]).append("\n");
        sb.append("Hora: ").append(fechaHoraActual.split(" ")[1]).append("\n");
        sb.append("Teléfono: 3142649585\n");
        sb.append("------------------------------\n");
        sb.append("Producto       Cant  Precio\n");
        sb.append("------------------------------\n");

        for (ProductoVendido productoVendido : productosVendidos) {
            sb.append(String.format("%-13s %3.0f %8.2f\n", productoVendido.getNombre(), productoVendido.getCantidad(), productoVendido.getPrecio()));
        }

        sb.append("------------------------------\n");
        sb.append(String.format("Total:              %8.2f\n", totalVenta));
        sb.append("------------------------------\n\n"); // Agrega un espacio extra antes del mensaje

        sb.append((char) 0x1B).append((char) 0x61).append((char) 0x01); // Center align
        sb.append("\n\n¡Gracias por su compra!\n\n"); // Agrega un espacio extra antes y después del mensaje

        // Feed and cut paper
        sb.append((char) 0x1D).append((char) 0x56).append((char) 0x42).append((char) 0x00);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(PRINTER_URL + "/print", sb.toString(), String.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new Exception("Error printing: " + response.getBody());
        }
    }
}
