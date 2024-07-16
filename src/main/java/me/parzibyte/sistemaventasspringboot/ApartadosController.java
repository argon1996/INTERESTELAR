package me.parzibyte.sistemaventasspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/apartados")
public class ApartadosController {

    @Autowired
    private ProductoApartadoRepository productoApartadoRepository;

    @GetMapping
    public String mostrarApartados(Model model) {
        // Obtener todos los productos apartados
        List<ProductoApartado> apartados = productoApartadoRepository.findAll();

        if (apartados.isEmpty()) {
            model.addAttribute("mensaje", "No hay productos apartados disponibles");
            model.addAttribute("clase", "info");
        } else {
            model.addAttribute("apartados", apartados);
        }

        return "apartados/apartados";  // Asegúrate de tener una plantilla Thymeleaf llamada apartados.html en el directorio templates/apartados/
    }
}
