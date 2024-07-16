
package me.parzibyte.conectorpluginv3;
import java.util.ArrayList;

public class ImpresionConNombrePluginV3 {
    public ArrayList<OperacionPluginV3> operaciones;
    public String nombreImpresora;
    public String serial;
    
    public ImpresionConNombrePluginV3(ArrayList<OperacionPluginV3> operaciones, String nombreImpresora, String serial) {
        this.operaciones = operaciones;
        this.nombreImpresora = nombreImpresora;
        this.serial = serial;
    }
}

