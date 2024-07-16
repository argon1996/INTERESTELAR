/*
 * ProductoVendidosRepository.java
 */
package me.parzibyte.sistemaventasspringboot;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProductosVendidosRepository extends CrudRepository<ProductoVendido, Integer> {
    List<ProductoVendido> findByVenta(Venta venta);

}
