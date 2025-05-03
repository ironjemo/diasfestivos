package diasfestivos.api.infraestructura.repositorios;

import diasfestivos.api.dominio.entidades.Calendario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import java.time.LocalDate;
import java.util.List;

@Repository
public interface ICalendarioRepositorio extends JpaRepository<Calendario, Long> {

    @Query(value = "SELECT * FROM calendario c WHERE EXTRACT(YEAR FROM c.fecha) = :anio", nativeQuery = true)
    List<Calendario> findByFechaYear(@Param("anio") int anio);

    // Buscar fechas entre dos valores (rango)
    List<Calendario> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);

    // Verificar si ya existe una fecha registrada
    boolean existsByFecha(LocalDate fecha);
}
