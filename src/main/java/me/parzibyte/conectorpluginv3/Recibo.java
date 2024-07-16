package me.parzibyte.conectorpluginv3;

import me.parzibyte.sistemaventasspringboot.ProductoVendido;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Recibo {

    private static final String IMPRESORA_TERMICA = "ImpresoraTermica"; // Asegúrate de que el nombre de la impresora sea correcto

    
        public void abrirCajonMonedero() {
            try {
                // Comando ESC/POS para abrir el cajón del monedero
                byte[] abrirCajon = new byte[]{27, 112, 0, 48, 55, 121};
        
                // Establecer la conexión con la impresora
                PrinterService printerService = new PrinterService();
                printerService.printBytes(IMPRESORA_TERMICA, abrirCajon);
        
                System.out.println("Cajón del monedero abierto correctamente");
            } catch (Exception e) {
                System.out.println("Error abriendo el cajón del monedero: " + e.getMessage());
                e.printStackTrace(); // Imprime el stack trace completo de la excepción
            }
        }
    
        // Otros métodos de la clase Recibo
    
    

    
    
    public static void metodo1(List<ProductoVendido> productosVendidos, double totalVenta) {
        ConectorPluginV3 conectorPluginV3 = new ConectorPluginV3(ConectorPluginV3.URL_PLUGIN_POR_DEFECTO, "");
        
        try {
            // Realizar el pulso para abrir el cajón del dinero
            conectorPluginV3.Iniciar().Pulso(48, 60, 120);
    
            // Esperar un momento antes de imprimir (opcional)
            Thread.sleep(2000);
    
            // Imprimir en la impresora térmica
            conectorPluginV3.abrirCajon("impresoraFicticia");
    
            System.out.println("Cajón de dinero abierto y documento impreso correctamente");
        } catch (Exception e) {
            System.out.println("Error al abrir el cajón de dinero o al imprimir el documento: " + e.getMessage());
        }
    }
    
    
    
    
    
    


    
    
    


    //metodos 2
    public static void generarReciboEImprimirYAbrirCajonMonedero(List<ProductoVendido> productosVendidos, double totalVenta) {
        ConectorPluginV3 conectorPluginV3 = new ConectorPluginV3(ConectorPluginV3.URL_PLUGIN_POR_DEFECTO, "");
        conectorPluginV3.Iniciar()
                .DeshabilitarElModoDeCaracteresChinos()
                .EstablecerAlineacion(ConectorPluginV3.ALINEACION_CENTRO)
                .EscribirTexto("Recibo de venta\n")
                .Feed(1)
                .EscribirTexto("Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n")
                .Feed(1)
                .EscribirTexto("Detalle de la venta:\n")
                .EstablecerAlineacion(ConectorPluginV3.ALINEACION_IZQUIERDA);
    
        for (ProductoVendido producto : productosVendidos) {
            conectorPluginV3.EscribirTexto(producto.getNombre() + " - $" + producto.getPrecio() + "\n");
        }
    
        conectorPluginV3.EscribirTexto("Total: $" + totalVenta + "\n")
                .Feed(1) // Añade una línea en blanco después del total
                .EscribirTexto("¡Gracias por su compra!\n") // Agrega un mensaje de agradecimiento
                .Feed(1) // Añade dos líneas en blanco antes de cortar el recibo
                .Corte(1); // Corta el recibo
    
        try {
            conectorPluginV3.imprimirEn("impresoraTermica");
            conectorPluginV3.abrirCajonMonedero("impresoraTermica");
            System.out.println("Impreso y cajón abierto correctamente");
        } catch (Exception e) {
            System.out.println("Error imprimiendo o abriendo el cajón del monedero: " + e.getMessage());
        }
    }
    
    
    private static String obtenerFechaActual() {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return fechaHoraActual.format(formateador);
    }
}
