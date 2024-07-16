package me.parzibyte.sistemaventasspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/productos")
public class ProductosController {

    @Autowired
    private ProductosRepository productosRepository;

    @Autowired
    private ProductoApartadoRepository productoApartadoRepository;

    @Autowired
    private AbonoRepository abonoRepository;

    @GetMapping(value = "/agregar")
    public String agregarProducto(Model model) {
        model.addAttribute("producto", new Producto());
        return "productos/agregar_producto";
    }

    @PostMapping(value = "/agregar")
    public String guardarProducto(@Valid @ModelAttribute Producto producto, BindingResult result, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "productos/agregar_producto";
        }
        productosRepository.save(producto);
        redirectAttrs.addFlashAttribute("mensaje", "Producto guardado correctamente")
                     .addFlashAttribute("clase", "success");
        return "redirect:/productos/mostrar";
    }

    @GetMapping(value = "/mostrar")
    public String mostrarProductos(Model model) {
        model.addAttribute("productos", productosRepository.findAll());
        return "productos/ver_productos";
    }

    @PostMapping("/eliminar")
    public String eliminarProducto(@RequestParam("id") Integer id, RedirectAttributes redirectAttrs) {
        Optional<Producto> productoOpt = productosRepository.findById(id);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            List<ProductoApartado> apartados = productoApartadoRepository.findByProducto(producto);
            if (!apartados.isEmpty()) {
                redirectAttrs.addFlashAttribute("mensaje", "No se puede eliminar el producto porque tiene apartados activos.")
                             .addFlashAttribute("clase", "danger");
            } else {
                productosRepository.deleteById(id);
                redirectAttrs.addFlashAttribute("mensaje", "Producto eliminado correctamente")
                             .addFlashAttribute("clase", "success");
            }
        } else {
            redirectAttrs.addFlashAttribute("mensaje", "Producto no encontrado")
                         .addFlashAttribute("clase", "danger");
        }
        return "redirect:/productos/mostrar";
    }

    @PostMapping("/apartar")
    public String apartarProducto(@RequestParam("id") Integer id, @RequestParam("cantidad") Float cantidad, @RequestParam("nombrePersona") String nombrePersona, @RequestParam("abonoInicial") Float abonoInicial, RedirectAttributes redirectAttrs) {
        Optional<Producto> optionalProducto = productosRepository.findById(id);
        if (optionalProducto.isPresent()) {
            Producto producto = optionalProducto.get();
            if (producto.getExistencia() >= cantidad) {
                Float existenciaActual = producto.getExistencia();
                producto.restarExistencia(cantidad);
                productosRepository.save(producto);
                ProductoApartado productoApartado = new ProductoApartado(producto, cantidad, LocalDateTime.now(), existenciaActual, nombrePersona, abonoInicial);
                productoApartadoRepository.save(productoApartado);
                redirectAttrs.addFlashAttribute("mensaje", "Producto apartado correctamente")
                             .addFlashAttribute("clase", "success");
            } else {
                redirectAttrs.addFlashAttribute("mensaje", "No hay suficiente existencia para apartar")
                             .addFlashAttribute("clase", "danger");
            }
        } else {
            redirectAttrs.addFlashAttribute("mensaje", "Producto no encontrado")
                         .addFlashAttribute("clase", "danger");
        }
        return "redirect:/productos/apartados";
    }

    @GetMapping("/apartados")
    public String listarApartados(@RequestParam(name = "search", required = false) String search, Model model) {
        List<ProductoApartado> apartados = productoApartadoRepository.findAllWithProductos();
        model.addAttribute("apartados", apartados);

        if (search != null && !search.isEmpty()) {
            List<Producto> productos = productosRepository.findByNombreContainingOrCodigoContaining(search, search);
            model.addAttribute("productos", productos);
        } else {
            model.addAttribute("productos", productosRepository.findAll());
        }

        // Asegúrate de que el modelo contenga el objeto `producto`
        model.addAttribute("producto", new Producto());

        return "productos/apartados";
    }

    @PostMapping("/retirar")
    public String retirarProducto(@RequestParam("id") Integer id, RedirectAttributes redirectAttrs) {
        Optional<ProductoApartado> optionalProductoApartado = productoApartadoRepository.findById(id);
        if (optionalProductoApartado.isPresent()) {
            ProductoApartado productoApartado = optionalProductoApartado.get();
            Producto producto = productoApartado.getProducto();
            
            // Actualizar la existencia del producto
            producto.setExistencia(producto.getExistencia() + productoApartado.getCantidad());
            productosRepository.save(producto);
            
            // Eliminar el producto apartado
            productoApartadoRepository.deleteById(id);
            
            redirectAttrs.addFlashAttribute("mensaje", "Producto retirado correctamente")
                         .addFlashAttribute("clase", "success");
        } else {
            redirectAttrs.addFlashAttribute("mensaje", "Producto apartado no encontrado")
                         .addFlashAttribute("clase", "danger");
        }
        return "redirect:/productos/apartados";
    }

    @PostMapping("/abonar")
    public String abonarProducto(@RequestParam("id") Integer id, @RequestParam("cantidad") Float cantidad, RedirectAttributes redirectAttrs) {
        Optional<ProductoApartado> optionalProductoApartado = productoApartadoRepository.findById(id);
        if (optionalProductoApartado.isPresent()) {
            ProductoApartado productoApartado = optionalProductoApartado.get();
            if (productoApartado != null && cantidad != null) {
                LocalDateTime fechaAbono = LocalDateTime.now();
                
                Abono nuevoAbono = new Abono(productoApartado, cantidad, fechaAbono);
                abonoRepository.save(nuevoAbono);
                
                if (productoApartado.getTotalAbono() == null) {
                    productoApartado.setTotalAbono(0f); // Inicializar si es null
                }

                productoApartado.setFechaUltimoAbono(fechaAbono);
                productoApartado.setTotalAbono(productoApartado.getTotalAbono() + cantidad);
                productoApartadoRepository.save(productoApartado);
                
                redirectAttrs.addFlashAttribute("mensaje", "Abono registrado correctamente")
                             .addFlashAttribute("clase", "success");
            } else {
                redirectAttrs.addFlashAttribute("mensaje", "Cantidad de abono no válida")
                             .addFlashAttribute("clase", "danger");
            }
        } else {
            redirectAttrs.addFlashAttribute("mensaje", "Producto apartado no encontrado")
                         .addFlashAttribute("clase", "danger");
        }
        return "redirect:/productos/apartados";
    }

}
