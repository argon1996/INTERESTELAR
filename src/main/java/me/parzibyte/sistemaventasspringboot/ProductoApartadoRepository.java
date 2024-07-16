package me.parzibyte.sistemaventasspringboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoApartadoRepository extends JpaRepository<ProductoApartado, Integer> {
    List<ProductoApartado> findByProducto(Producto producto);

    @Query("SELECT pa FROM ProductoApartado pa LEFT JOIN FETCH pa.producto")
    List<ProductoApartado> findAllWithProductos();
}
