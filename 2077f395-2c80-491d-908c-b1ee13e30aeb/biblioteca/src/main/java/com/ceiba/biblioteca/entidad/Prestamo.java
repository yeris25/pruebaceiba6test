package com.ceiba.biblioteca.entidad;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name ="prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "isbn", length = 10)
    private String isbn;

    @Column(name = "identificacion_usuario", length = 10)
    private String identificacionUsuario;

    @Column(name = "fecha_maxima_devolucion")
    private LocalDate fechaMaximaDevolucion;

    @Column(name = "tipo_usuario")
    private Integer tipoUsuario;

    public Prestamo() {
    }

    public Prestamo(Integer id, String isbn, String identificacionUsuario, LocalDate fechaMaximaDevolucion, Integer tipoUsuario) {
        this.id = id;
        this.isbn = isbn;
        this.identificacionUsuario = identificacionUsuario;
        this.fechaMaximaDevolucion = fechaMaximaDevolucion;
        this.tipoUsuario = tipoUsuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIdentificacionUsuario() {
        return identificacionUsuario;
    }

    public void setIdentificacionUsuario(String identificacionUsuario) {
        this.identificacionUsuario = identificacionUsuario;
    }

    public LocalDate getFechaMaximaDevolucion() {
        return fechaMaximaDevolucion;
    }

    public void setFechaMaximaDevolucion(LocalDate fechaMaximaDevolucion) {
        this.fechaMaximaDevolucion = fechaMaximaDevolucion;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Integer tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Transient
    private String mensajeError;

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
}
