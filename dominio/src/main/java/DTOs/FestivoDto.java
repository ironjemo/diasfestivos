package diasfestivos.api.dominio.DTOs;

import java.time.LocalDate;

public class FestivoDto {
    private LocalDate fecha;
    private String nombreFestivo;
    private String descripcion;



    public FestivoDto(LocalDate fecha, String nombreFestivo, String descripcion) {
        this.fecha = fecha;
        this.nombreFestivo = nombreFestivo;
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNombreFestivo() {
        return nombreFestivo;
    }

    public void setNombreFestivo(String nombreFestivo) {
        this.nombreFestivo = nombreFestivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
