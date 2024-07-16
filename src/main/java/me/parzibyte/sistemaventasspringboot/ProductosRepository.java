package me.parzibyte.sistemaventasspringboot;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductosRepository extends CrudRepository<Producto, Integer> {

    List<Producto> findByNombreContainingOrCodigoContaining(String nombre, String codigo);

    Producto findFirstByCodigo(String codigo);

    @Query("SELECT p FROM Producto p WHERE p.existencia < :umbral")
    List<Producto> findProductosFaltantes(@Param("umbral") Float umbral);

    @Query("SELECT p FROM Producto p WHERE p.apartado = true")
    List<Producto> findProductosApartados();

    List<Producto> findByApartadoTrue();
}
