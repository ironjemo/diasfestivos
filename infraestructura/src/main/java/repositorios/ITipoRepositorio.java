package diasfestivos.api.infraestructura.repositorios;

import diasfestivos.api.dominio.entidades.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

@Repository
public interface ITipoRepositorio extends JpaRepository<Tipo, Long> {

    @Query("SELECT t FROM Tipo t WHERE LOWER(t.tipo) = LOWER(:tipo)")
    Optional<Tipo> findByTipoIgnoreCase(@Param("tipo") String tipo);

}
