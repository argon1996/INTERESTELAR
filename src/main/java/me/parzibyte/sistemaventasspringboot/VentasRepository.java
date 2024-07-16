package me.parzibyte.sistemaventasspringboot;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VentasRepository extends CrudRepository<Venta, Long> {
    @Query("SELECT v FROM Venta v WHERE v.id = :id")
    Optional<Venta> findByIdAsInteger(@Param("id") Integer id);

    List<Venta> findAllByOrderByFechaYHoraDesc();
}
