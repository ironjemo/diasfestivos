package diasfestivos.api.dominio.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "tipo")
public class Tipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; // Cambiado de Integer a Long

    @Column(name = "tipo", length = 100, nullable = false)
    @NotNull
    @Size(min = 1, max = 100)
    private String tipo;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Método toString para depuración
    @Override
    public String toString() {
        return "Tipo{id=" + id + ", tipo='" + tipo + "'}";
    }
}
