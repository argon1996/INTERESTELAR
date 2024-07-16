package me.parzibyte.sistemaventasspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import me.parzibyte.conectorpluginv3.Recibo;
import java.util.Optional;
import java.util.List;

@Controller
@RequestMapping(path = "/ventas")
public class VentasController {
    @Autowired
    VentasRepository ventasRepository;

    @Autowired
    ProductosVendidosRepository productosVendidosRepository;

    @GetMapping(value = "/")
    public String mostrarVentas(Model model) {
        List<Venta> ventas = ventasRepository.findAllByOrderByFechaYHoraDesc();
        model.addAttribute("ventas", ventas);
        return "ventas/ver_ventas";
    }

    
 


}
