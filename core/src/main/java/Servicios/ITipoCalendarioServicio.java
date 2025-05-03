package diasfestivos.api.core.servicios;

import diasfestivos.api.dominio.entidades.*;
import java.util.List;

public interface ITipoCalendarioServicio {

    public List<Tipo> listar();

   public  Tipo obtener(Long id);

    public List<Tipo> buscar(String nombreTipoCalendario);

    public Tipo agregar(Tipo tipoCalendario);

    public Tipo modificar(Tipo tipoCalendario);

    public boolean eliminar(Long id);
}
