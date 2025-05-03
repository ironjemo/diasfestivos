package diasfestivos.api.presentacion;

import org.springframework.web.bind.annotation.*;
import java.util.Map;
import diasfestivos.api.core.servicios.ICalendarioServicio;
import diasfestivos.api.dominio.entidades.Calendario;
import java.util.stream.Collectors;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/calendario")
public class CalendarioControlador {

    private final ICalendarioServicio calendarioServicio;

    // Logger para depuración
    private static final Logger logger = LoggerFactory.getLogger(CalendarioControlador.class);

    public CalendarioControlador(ICalendarioServicio calendarioServicio) {
        this.calendarioServicio = calendarioServicio;
    }

    // Procesa los días festivos para un año dado
    @PostMapping("/procesar/{anio}")
    public boolean procesar(@PathVariable int anio) {
        return calendarioServicio.procesarAnio(anio);
    }

    // Lista todos los registros del calendario para un año dado
    @GetMapping("/listar/{anio}")
    public List<Calendario> listar(@PathVariable int anio) {
        return calendarioServicio.obtenerCalendario(anio);
    }

    // Obtiene solo los días festivos de un año dado
    @GetMapping("/obtener/{anio}")
    public List<Map<String, String>> obtenerFestivos(@PathVariable int anio) {
        List<Calendario> lista = calendarioServicio.obtenerCalendario(anio);

        // Registro de depuración para verificar los valores
        lista.forEach(f -> {
            logger.debug("Fecha: {}, Descripción: {}, Tipo: {}", 
                         f.getFecha(), 
                         f.getDescripcion(), 
                         (f.getTipo() != null ? f.getTipo().getTipo() : "null"));
        });

        // Filtra solo los que sean tipo "Día festivo"
        return lista.stream()
            .filter(f -> f.getTipo() != null && 
                         f.getTipo().getTipo() != null && 
                         "Día festivo".equalsIgnoreCase(f.getTipo().getTipo()))
            .map(f -> Map.of(
                "festivo", f.getDescripcion(),
                "fecha", f.getFecha().toString()
            ))
            .collect(Collectors.toList());
    }
}
