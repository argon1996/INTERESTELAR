package me.parzibyte.conectorpluginv3;

import com.fasterxml.jackson.jr.ob.JSON;
import java.io.IOException;
import java.util.ArrayList;
import java.net.URI;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import java.time.Duration;
import java.lang.String;


public class ConectorPluginV3 {

    public static String URL_PLUGIN_POR_DEFECTO = "http://localhost:8000";
    public static float TAMANO_IMAGEN_NORMAL = 0;
    public static float TAMANO_IMAGEN_DOBLE_ANCHO = 1;
    public static float TAMANO_IMAGEN_DOBLE_ALTO = 2;
    public static float TAMANO_IMAGEN_DOBLE_ANCHO_Y_ALTO = 3;
    public static float ALINEACION_IZQUIERDA = 0;
    public static float ALINEACION_CENTRO = 1;
    public static float ALINEACION_DERECHA = 2;
    public static float RECUPERACION_QR_BAJA = 0;
    public static float RECUPERACION_QR_MEDIA = 1;
    public static float RECUPERACION_QR_ALTA = 2;
    public static float RECUPERACION_QR_MEJOR = 3;
  

    public ArrayList<OperacionPluginV3> operaciones;
    public String urlPlugin;
    public String serial;

    public ConectorPluginV3(String urlPlugin, String serial) {
        this.operaciones = new ArrayList<>();
        this.urlPlugin = urlPlugin;
        this.serial = serial;
    }

    public void abrirCajonMonedero(String impresora) {
        try {
            // Comando ESC/POS para abrir el cajón del monedero
            byte[] abrirCajon = new byte[]{27, 112, 0, 48, 55, 121};

            // Establecer la conexión con la impresora
            PrinterService printerService = new PrinterService();
            printerService.printBytes(impresora, abrirCajon);

            System.out.println("Cajón del monedero abierto correctamente");
        } catch (Exception e) {
            System.out.println("Error abriendo el cajón del monedero: " + e.getMessage());
        }
    }

    public boolean imprimirEn(String impresora, ArrayList<String> operacionesPermitidas) throws Exception, IOException, InterruptedException {
        // Filtrar las operaciones permitidas
        ArrayList<OperacionPluginV3> operacionesFiltradas = new ArrayList<>();
        for (OperacionPluginV3 operacion : this.operaciones) {
            if (operacionesPermitidas.contains(operacion.nombre)) {
                operacionesFiltradas.add(operacion);
            }
        }
    
        // Crear el objeto de impresión con las operaciones filtradas
        ImpresionConNombrePluginV3 impresionConNombre = new ImpresionConNombrePluginV3(operacionesFiltradas, impresora, this.serial);
    
        // Resto del código para enviar la impresión al servidor y manejar la respuesta...
    
        // Return statement to handle the boolean return type
        return true; // Or false, depending on your implementation
    }
    

    


    public ConectorPluginV3(String urlPlugin) {
        this.operaciones = new ArrayList<>();
        this.urlPlugin = urlPlugin;
        this.serial = "";
    }

    public ConectorPluginV3() {
        this.urlPlugin = URL_PLUGIN_POR_DEFECTO;
        this.operaciones = new ArrayList<>();
    }

    private void agregarOperacion(OperacionPluginV3 operacion) {
        this.operaciones.add(operacion);
    }

    public ConectorPluginV3 CargarImagenLocalEImprimir(String ruta, float tamano, float maximoAncho) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(ruta);
        argumentos.add(tamano);
        argumentos.add(maximoAncho);
        this.agregarOperacion(new OperacionPluginV3("CargarImagenLocalEImprimir", argumentos));
        return this;
    }

    public ConectorPluginV3 Corte(float lineas) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(lineas);
        this.agregarOperacion(new OperacionPluginV3("Corte", argumentos));
        return this;
    }

    public ConectorPluginV3 CorteParcial() {
        ArrayList<Object> argumentos = new ArrayList<>();
        this.agregarOperacion(new OperacionPluginV3("CorteParcial", argumentos));
        return this;
    }

    public ConectorPluginV3 DefinirCaracterPersonalizado(String caracterRemplazoComoCadena, String matrizComoCadena) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(caracterRemplazoComoCadena);
        argumentos.add(matrizComoCadena);
        this.agregarOperacion(new OperacionPluginV3("DefinirCaracterPersonalizado", argumentos));
        return this;
    }

    public ConectorPluginV3 DescargarImagenDeInternetEImprimir(String urlImagen, float tamano, float maximoAncho) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(urlImagen);
        argumentos.add(tamano);
        argumentos.add(maximoAncho);
        this.agregarOperacion(new OperacionPluginV3("DescargarImagenDeInternetEImprimir", argumentos));
        return this;
    }

    public ConectorPluginV3 DeshabilitarCaracteresPersonalizados() {
        ArrayList<Object> argumentos = new ArrayList<>();
        this.agregarOperacion(new OperacionPluginV3("DeshabilitarCaracteresPersonalizados", argumentos));
        return this;
    }

    public ConectorPluginV3 DeshabilitarElModoDeCaracteresChinos() {
        ArrayList<Object> argumentos = new ArrayList<>();
        this.agregarOperacion(new OperacionPluginV3("DeshabilitarElModoDeCaracteresChinos", argumentos));
        return this;
    }

    public ConectorPluginV3 EscribirTexto(String texto) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(texto);
        this.agregarOperacion(new OperacionPluginV3("EscribirTexto", argumentos));
        return this;
    }

    public ConectorPluginV3 EstablecerAlineacion(float alineacion) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(alineacion);
        this.agregarOperacion(new OperacionPluginV3("EstablecerAlineacion", argumentos));
        return this;
    }

    public ConectorPluginV3 EstablecerEnfatizado(boolean enfatizado) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(enfatizado);
        this.agregarOperacion(new OperacionPluginV3("EstablecerEnfatizado", argumentos));
        return this;
    }

    public ConectorPluginV3 EstablecerFuente(float fuente) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(fuente);
        this.agregarOperacion(new OperacionPluginV3("EstablecerFuente", argumentos));
        return this;
    }

    public ConectorPluginV3 EstablecerImpresionAlReves(boolean alReves) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(alReves);
        this.agregarOperacion(new OperacionPluginV3("EstablecerImpresionAlReves", argumentos));
        return this;
    }

    public ConectorPluginV3 EstablecerImpresionBlancoYNegroInversa(boolean invertir) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(invertir);
        this.agregarOperacion(new OperacionPluginV3("EstablecerImpresionBlancoYNegroInversa", argumentos));
        return this;
    }

    public ConectorPluginV3 EstablecerRotacionDe90Grados(boolean rotar) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(rotar);
        this.agregarOperacion(new OperacionPluginV3("EstablecerRotacionDe90Grados", argumentos));
        return this;
    }

    public ConectorPluginV3 EstablecerSubrayado(boolean subrayado) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(subrayado);
        this.agregarOperacion(new OperacionPluginV3("EstablecerSubrayado", argumentos));
        return this;
    }

    public ConectorPluginV3 EstablecerTamanoFuente(float multiplicadorAncho, float multiplicadorAlto) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(multiplicadorAncho);
        argumentos.add(multiplicadorAlto);
        this.agregarOperacion(new OperacionPluginV3("EstablecerTamañoFuente", argumentos));
        return this;
    }

    public ConectorPluginV3 Feed(float lineas) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(lineas);
        this.agregarOperacion(new OperacionPluginV3("Feed", argumentos));
        return this;
    }

    public ConectorPluginV3 HabilitarCaracteresPersonalizados() {
        ArrayList<Object> argumentos = new ArrayList<>();
        this.agregarOperacion(new OperacionPluginV3("HabilitarCaracteresPersonalizados", argumentos));
        return this;
    }

    public ConectorPluginV3 HabilitarElModoDeCaracteresChinos() {
        ArrayList<Object> argumentos = new ArrayList<>();
        this.agregarOperacion(new OperacionPluginV3("HabilitarElModoDeCaracteresChinos", argumentos));
        return this;
    }

    public ConectorPluginV3 ImprimirCodigoDeBarrasCodabar(String contenido, float alto, float ancho,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPluginV3("ImprimirCodigoDeBarrasCodabar", argumentos));
        return this;
    }

    public ConectorPluginV3 ImprimirCodigoDeBarrasCode128(String contenido, float alto, float ancho,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPluginV3("ImprimirCodigoDeBarrasCode128", argumentos));
        return this;
    }

    public ConectorPluginV3 ImprimirCodigoDeBarrasCode39(String contenido, boolean incluirSumaDeVerificacion,
            boolean modoAsciiCompleto, float alto, float ancho, float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(incluirSumaDeVerificacion);
        argumentos.add(modoAsciiCompleto);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPluginV3("ImprimirCodigoDeBarrasCode39", argumentos));
        return this;
    }

    public ConectorPluginV3 ImprimirCodigoDeBarrasCode93(String contenido, float alto, float ancho,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPluginV3("ImprimirCodigoDeBarrasCode93", argumentos));
        return this;
    }

    public ConectorPluginV3 ImprimirCodigoDeBarrasEan(String contenido, float alto, float ancho,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPluginV3("ImprimirCodigoDeBarrasEan", argumentos));
        return this;
    }

    public ConectorPluginV3 ImprimirCodigoDeBarrasEan8(String contenido, float alto, float ancho,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPluginV3("ImprimirCodigoDeBarrasEan8", argumentos));
        return this;
    }

    public ConectorPluginV3 ImprimirCodigoDeBarrasPdf417(String contenido, float nivelSeguridad, float alto,
            float ancho, float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(nivelSeguridad);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPluginV3("ImprimirCodigoDeBarrasPdf417", argumentos));
        return this;
    }

    public ConectorPluginV3 ImprimirCodigoDeBarrasTwoOfFiveITF(String contenido, boolean intercalado, float alto,
            float ancho, float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(intercalado);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPluginV3("ImprimirCodigoDeBarrasTwoOfFiveITF", argumentos));
        return this;
    }

    public ConectorPluginV3 ImprimirCodigoDeBarrasUpcA(String contenido, float alto, float ancho,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPluginV3("ImprimirCodigoDeBarrasUpcA", argumentos));
        return this;
    }

    public ConectorPluginV3 ImprimirCodigoDeBarrasUpcE(String contenido, float alto, float ancho,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(alto);
        argumentos.add(ancho);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPluginV3("ImprimirCodigoDeBarrasUpcE", argumentos));
        return this;
    }

    public ConectorPluginV3 ImprimirCodigoQr(String contenido, float anchoMaximo, float nivelRecuperacion,
            float tamanoImagen) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(contenido);
        argumentos.add(anchoMaximo);
        argumentos.add(nivelRecuperacion);
        argumentos.add(tamanoImagen);
        this.agregarOperacion(new OperacionPluginV3("ImprimirCodigoQr", argumentos));
        return this;
    }

    public ConectorPluginV3 ImprimirImagenEnBase64(String imagenCodificadaEnBase64, float tamano, float maximoAncho) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(imagenCodificadaEnBase64);
        argumentos.add(tamano);
        argumentos.add(maximoAncho);
        this.agregarOperacion(new OperacionPluginV3("ImprimirImagenEnBase64", argumentos));
        return this;
    }

    public ConectorPluginV3 Iniciar() {
        ArrayList<Object> argumentos = new ArrayList<>();
        this.agregarOperacion(new OperacionPluginV3("Iniciar", argumentos));
        return this;
    }

    public ConectorPluginV3 Pulso(float pin, float tiempoEncendido, float tiempoApagado) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(pin);
        argumentos.add(tiempoEncendido);
        argumentos.add(tiempoApagado);
        this.agregarOperacion(new OperacionPluginV3("Pulso", argumentos));
        return this;
    }


    public ConectorPluginV3 TextoSegunPaginaDeCodigos(float numeroPagina, String pagina, String texto) {
        ArrayList<Object> argumentos = new ArrayList<>();
        argumentos.add(numeroPagina);
        argumentos.add(pagina);
        argumentos.add(texto);
        this.agregarOperacion(new OperacionPluginV3("TextoSegunPaginaDeCodigos", argumentos));
        return this;
    }

    //mi codigo
    public boolean abrirCajon(String impresora) throws Exception, IOException, InterruptedException {
        // Preparar la solicitud de impresión sin contenido
        ImpresionConNombrePluginV3 impresionConNombre = new ImpresionConNombrePluginV3(this.operaciones, impresora, this.serial);
        String postEndpoint = this.urlPlugin + "/imprimir";
        String inputJson = JSON.std.asString(impresionConNombre);
        
        // Realizar la solicitud de impresión sin contenido
        URL url = new URL(postEndpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = inputJson.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        
        // Verificar la respuesta del servidor
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("Código de respuesta distinto a 200. Verifique que el plugin se está ejecutando");
        }
        
        // Leer la respuesta del servidor
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            String respuesta = response.toString();
            if (respuesta.equals("true\n")) {
                return true;
            } else {
                throw new Exception("Petición ok pero error en el servidor: " + respuesta);
            }
        }
    }
    
    

     



    public boolean imprimirEn(String impresora) throws Exception, IOException, InterruptedException {
        ImpresionConNombrePluginV3 impresionConNombre = new ImpresionConNombrePluginV3(this.operaciones, impresora, this.serial);
        String postEndpoint = this.urlPlugin + "/imprimir";
        String inputJson = JSON.std.asString(impresionConNombre);
        URL url = new URL(postEndpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = inputJson.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("código de respuesta distinto a 200. Verifique que el plugin se está ejecutando");
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            String respuesta = response.toString();
            if (respuesta.equals("true\n")) {
                return true;
            } else {
                throw new Exception("petición ok pero error en el servidor: " + respuesta);
            }
        }
    }
    
    public static String[] obtenerImpresoras(String urlPlugin) throws InterruptedException, IOException {
        URL url = new URL(urlPlugin + "/impresoras");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(10));
    
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            String[] impresoras = response.toString().replace("[", "").replace("]", "").replace("\n", "").replace("\"", "").split(",");
            return impresoras;
        }
    }
    
    public static String[] obtenerImpresoras() throws InterruptedException, IOException {
        return obtenerImpresoras(URL_PLUGIN_POR_DEFECTO);
    }


}
