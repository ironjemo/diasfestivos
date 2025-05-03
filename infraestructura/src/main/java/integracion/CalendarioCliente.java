package diasfestivos.api.infraestructura.integracion;

import diasfestivos.api.dominio.DTOs.FestivoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class CalendarioCliente {

    @Autowired
    private RestTemplate restTemplate;

    // URL base sin repetir partes del endpoint
    private static final String BASE_URL = "http://localhost:3030/festivos/obtener/";

    /**
     * Consulta todos los días festivos de un año específico desde el microservicio externo apifestivos.
     *
     * @param anio El año del que se quieren obtener los días festivos.
     * @return Lista de objetos FestivoDto.
     */
    public List<FestivoDto> obtenerFestivosPorAnio(int anio) {
        String url = BASE_URL + anio;  // <<--- Correcto: si la ruta es /api/festivos/obtener/{anio}

        try {
            ResponseEntity<List<FestivoDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FestivoDto>>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                System.err.println("Respuesta vacía o inesperada al obtener festivos del año: " + anio);
                return Collections.emptyList();
            }

        } catch (RestClientException e) {
            System.err.println("Error al consumir la API de festivos: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}
