
package diasfestivos.api.core.servicios;

import diasfestivos.api.dominio.entidades.Calendario;

import java.util.List;

public interface ICalendarioServicio {
    boolean procesarAnio(int anio);
    List<Calendario> obtenerCalendario(int anio);
}


