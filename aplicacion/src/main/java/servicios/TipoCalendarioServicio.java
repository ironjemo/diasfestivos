package diasfestivos.api.aplicacion.servicios;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import diasfestivos.api.core.servicios.*;
import diasfestivos.api.dominio.entidades.*;
import diasfestivos.api.infraestructura.repositorios.*;

@Service
public class TipoCalendarioServicio implements ITipoCalendarioServicio {

    @Autowired
    private ITipoRepositorio repositorio;

    @Override
    public List<Tipo> listar() {
        return repositorio.findAll();
    }

    @Override
    public Tipo obtener(Long id) {  // Cambiado de Integer a Long
        return repositorio.findById(id).orElse(null);
    }

    @Override
    public List<Tipo> buscar(String nombreTipoCalendario) {
        // Cambiar el nombre del método a findByTipoIgnoreCase
        Optional<Tipo> tipo = repositorio.findByTipoIgnoreCase(nombreTipoCalendario);
        return tipo.map(List::of).orElseGet(List::of);  // Retornar una lista vacía si no se encuentra
    }

    @Override
    public Tipo agregar(Tipo tipoCalendario) {
        tipoCalendario.setId(0L);  // Cambiado de 0 a 0L (Long)
        return repositorio.save(tipoCalendario);
    }

    @Override
    public Tipo modificar(Tipo tipoCalendario) {
        return repositorio.findById(tipoCalendario.getId())
                          .map(existingTipo -> repositorio.save(tipoCalendario))
                          .orElse(null);
    }

    @Override
    public boolean eliminar(Long id) {  // Cambiado de Integer a Long
        try {
            repositorio.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
