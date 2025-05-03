/*package festivos.api.presentacion.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import festivos.api.aplicacion.servicios.TipoFestivoServicio;
import festivos.api.dominio.dto.TipoFestivoDto;

import java.util.List;

@RestController
@RequestMapping("/api/tiposfestivos")
public class TipoFestivoControlador {

    @Autowired
    private TipoFestivoServicio servicio;

    @GetMapping("/listar")
    public List<TipoFestivo> listarTiposFestivos() {
        return servicio.listarTiposFestivos();
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<TipoFestivo> obtenerTipoFestivoPorId(@PathVariable long id) {
        return ResponseEntity.ok(servicio.obtenerTipoFestivoPorId(id));
    }
}*/