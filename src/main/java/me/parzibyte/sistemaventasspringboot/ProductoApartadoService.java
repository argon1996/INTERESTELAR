package me.parzibyte.sistemaventasspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProductoApartadoService {

    @Autowired
    private ProductoApartadoRepository productoApartadoRepository;

    @Autowired
    private ProductosRepository productosRepository;

    @Autowired
    private AbonoRepository abonoRepository;

    public void apartarProducto(Integer id, Float cantidad, String nombrePersona, Float abonoInicial) {
        Producto producto = productosRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID de producto no válido: " + id));
        ProductoApartado productoApartado = new ProductoApartado(producto, cantidad, LocalDateTime.now(), producto.getExistencia(), nombrePersona, abonoInicial);
        productoApartadoRepository.save(productoApartado);
        producto.setExistencia(producto.getExistencia() - cantidad);
        productosRepository.save(producto);
    }

    public void abonar(Integer id, Float cantidad) {
        ProductoApartado productoApartado = productoApartadoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID de producto apartado no válido: " + id));
        LocalDateTime fechaAbono = LocalDateTime.now();
        
        Abono nuevoAbono = new Abono(productoApartado, cantidad, fechaAbono);
        abonoRepository.save(nuevoAbono);
        
        if (productoApartado.getTotalAbono() == null) {
            productoApartado.setTotalAbono(0f); // Inicializar si es null
        }

        productoApartado.setFechaUltimoAbono(fechaAbono);
        productoApartado.setTotalAbono(productoApartado.getTotalAbono() + cantidad);
        productoApartadoRepository.save(productoApartado);
    }

    public void retirar(Integer id) {
        ProductoApartado productoApartado = productoApartadoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID de producto apartado no válido: " + id));
        Producto producto = productoApartado.getProducto();
        producto.setExistencia(producto.getExistencia() + productoApartado.getCantidad());
        productosRepository.save(producto);
        productoApartadoRepository.deleteById(id);
    }
}
