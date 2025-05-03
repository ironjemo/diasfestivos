package diasfestivos.api.dominio.entidades;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad Calendario que representa los días del año clasificados
 * por tipo (laboral, festivo, fin de semana, etc.)
 */
@Entity
@Table(name = "calendario")
public class Calendario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Clave primaria autoincremental
    @Column(name = "id")
    private Long id;

    @Column(name = "fecha", nullable = false, unique = true) // Fecha única obligatoria
    private LocalDate fecha;

    /**
     * Relación muchos-a-uno con la entidad Tipo.
     * El campo 'idtipo' en la base de datos es la clave foránea que apunta a la tabla 'tipo'.
     */
    @ManyToOne
    @JoinColumn(name = "idtipo", nullable = false) // Define la columna de unión en la tabla
    private Tipo tipo;

    @Column(name = "descripcion", length = 100) // Descripción opcional del día
    private String descripcion;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
