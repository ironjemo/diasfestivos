package diasfestivos.api.aplicacion.servicios;

import diasfestivos.api.core.servicios.ICalendarioServicio;
import diasfestivos.api.dominio.entidades.Calendario;
import diasfestivos.api.dominio.entidades.Tipo;
import diasfestivos.api.infraestructura.repositorios.ICalendarioRepositorio;
import diasfestivos.api.infraestructura.repositorios.ITipoRepositorio;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalendarioServicio implements ICalendarioServicio {

    private final ICalendarioRepositorio calendarioRepositorio;
    private final ITipoRepositorio tipoRepositorio;
    private final RestTemplate restTemplate;

    public CalendarioServicio(ICalendarioRepositorio calendarioRepositorio, ITipoRepositorio tipoRepositorio) {
        this.calendarioRepositorio = calendarioRepositorio;
        this.tipoRepositorio = tipoRepositorio;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public boolean procesarAnio(int anio) {
        String url = "http://localhost:3030/festivos/" + anio;
        FestivosResponse respuesta = restTemplate.getForObject(url, FestivosResponse.class);

        Map<LocalDate, String> mapaFestivos = new HashMap<>();
        if (respuesta != null && respuesta.festivos != null) {
            for (Map.Entry<String, String> entry : respuesta.festivos.entrySet()) {
                try {
                    String nombreFestivo = entry.getKey();
                    LocalDate fecha = LocalDate.parse(entry.getValue().substring(0, 10));
                    mapaFestivos.put(fecha, nombreFestivo);
                } catch (Exception e) {
                    System.err.println("⚠️ Error parseando fecha: " + entry.getValue() + " - " + e.getMessage());
                }
            }
        }

        for (Month mes : Month.values()) {
            int diasMes = mes.length(LocalDate.of(anio, mes, 1).isLeapYear());
            for (int dia = 1; dia <= diasMes; dia++) {
                LocalDate fecha = LocalDate.of(anio, mes, dia);

                if (!calendarioRepositorio.existsByFecha(fecha)) {
                    Calendario calendario = new Calendario();
                    calendario.setFecha(fecha);

                    if (mapaFestivos.containsKey(fecha)) {
                        Tipo tipoFestivo = tipoRepositorio.findByTipoIgnoreCase("Día festivo")
                                .orElseThrow(() -> new RuntimeException("Tipo no encontrado: Día festivo"));
                        calendario.setTipo(tipoFestivo);
                        calendario.setDescripcion(mapaFestivos.get(fecha));
                        System.out.println("🎉 Es festivo: " + fecha + " - " + mapaFestivos.get(fecha));
                    } else if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY || fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
                        Tipo tipoFinde = tipoRepositorio.findByTipoIgnoreCase("Fin de Semana")
                                .orElse(null);
                        if (tipoFinde == null) {
                            System.err.println("❌ Tipo 'Fin de Semana' no encontrado.");
                            continue;
                        }
                        calendario.setTipo(tipoFinde);
                        calendario.setDescripcion("Fin de semana");
                        System.out.println("📅 Fin de semana: " + fecha);
                    } else {
                        Tipo tipoLaboral = tipoRepositorio.findByTipoIgnoreCase("Día laboral")
                                .orElse(null);
                        if (tipoLaboral == null) {
                            System.err.println("❌ Tipo 'Día laboral' no encontrado.");
                            continue;
                        }
                        calendario.setTipo(tipoLaboral);
                        calendario.setDescripcion("Día laboral normal");
                        System.out.println("💼 Día laboral: " + fecha);
                    }

                    System.out.println("✅ Guardando fecha: " + calendario.getFecha() + " - " + calendario.getDescripcion());
                    calendarioRepositorio.save(calendario);
                }
            }
        }
        return true;
    }

    @Override
    public List<Calendario> obtenerCalendario(int anio) {
        LocalDate inicio = LocalDate.of(anio, 1, 1);
        LocalDate fin = LocalDate.of(anio, 12, 31);
        return calendarioRepositorio.findByFechaBetween(inicio, fin);
    }

    // ✅ Clase para mapear la respuesta del endpoint externo
    private static class FestivosResponse {
        public String año;
        public Map<String, String> festivos = new HashMap<>();
    }
}
