package me.parzibyte.conectorpluginv3;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PrinterService {

    public void printBytes(String printerIpAddress, byte[] bytes) throws IOException {
        Socket socket = null;
        OutputStream outputStream = null;

        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(printerIpAddress, 9100), 1000); // Puerto 9100 para impresoras de red
            outputStream = socket.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // Manejo de errores
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // Manejo de errores
                }
            }
        }
    }
}
