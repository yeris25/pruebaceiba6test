package com.ceiba.biblioteca.servicio;


import com.ceiba.biblioteca.entidad.Prestamo;
import com.ceiba.biblioteca.repositorio.PrestamoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class PrestamoServicio implements PrestamoServicioInterface {
    @Autowired
    protected PrestamoRepositorio prestamoRepositorio;
    @Override
    public Prestamo crearPrestamo(Prestamo prestamo) throws Exception{
        try {
            if (prestamo.getTipoUsuario() == 3 && prestamoRepositorio.existsByIdentificacionUsuario(prestamo.getIdentificacionUsuario())) {
                throw new Exception("El usuario con identificación " + prestamo.getIdentificacionUsuario() + " ya tiene un libro prestado por lo cual no se le puede realizar otro préstamo");
            }
            prestamo.setFechaMaximaDevolucion(calcularFechaMaximaDevolucion(prestamo));
            return prestamoRepositorio.save(prestamo);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Prestamo buscarPorId(Integer id) throws Exception{
        try {
            Optional<Prestamo> prestamoOptional = prestamoRepositorio.findById(id);
            if (prestamoOptional.isPresent()) {
                return prestamoOptional.get();
            } else {
                throw new Exception("Usuario no encontrado.");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public LocalDate calcularFechaMaximaDevolucion(Prestamo prestamo) {
        Integer tipoUsuario = prestamo.getTipoUsuario();
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaMaximaDevolucion;
        switch (tipoUsuario) {
            case 1:
                fechaMaximaDevolucion = calcularFechaMaximaDevolucionAfiliado(fechaActual);
                return fechaMaximaDevolucion;
            case 2:
                fechaMaximaDevolucion = calcularFechaMaximaDevolucionEmpleado(fechaActual);
                return fechaMaximaDevolucion;
            case 3:
                fechaMaximaDevolucion = calcularFechaMaximaDevolucionInvitado(fechaActual);
                return fechaMaximaDevolucion;
            default:
                throw new IllegalArgumentException("Tipo de usuario no permitido en la biblioteca");
        }
    }

    @Override
    public LocalDate calcularFechaMaximaDevolucionBase(LocalDate fechaActual, Integer diasSegunTipoUsuario) {
        LocalDate fechaMaximaDevolucionBase = fechaActual;
        Integer i = 0;
        while (i < diasSegunTipoUsuario) {
            fechaMaximaDevolucionBase = fechaMaximaDevolucionBase.plusDays(1);
            if (fechaMaximaDevolucionBase.getDayOfWeek() != DayOfWeek.SATURDAY && fechaMaximaDevolucionBase.getDayOfWeek() != DayOfWeek.SUNDAY){
                i++;
            }
        }

        return fechaMaximaDevolucionBase;
    }

    public LocalDate calcularFechaMaximaDevolucionAfiliado(LocalDate fechaActual) {
        LocalDate fechaMaximaDevolucionAfiliado = fechaActual;
        fechaMaximaDevolucionAfiliado = calcularFechaMaximaDevolucionBase(fechaMaximaDevolucionAfiliado, 10);
        return fechaMaximaDevolucionAfiliado;
    }

    public LocalDate calcularFechaMaximaDevolucionEmpleado(LocalDate fechaActual) {
        LocalDate fechaMaximaDevolucionEmpleado = fechaActual;
        fechaMaximaDevolucionEmpleado = calcularFechaMaximaDevolucionBase(fechaMaximaDevolucionEmpleado, 8);
        return fechaMaximaDevolucionEmpleado;
    }

    public LocalDate calcularFechaMaximaDevolucionInvitado(LocalDate fechaActual) {
        LocalDate fechaMaximaDevolucionInvitado = fechaActual;
        fechaMaximaDevolucionInvitado = calcularFechaMaximaDevolucionBase(fechaMaximaDevolucionInvitado, 7);
        return fechaMaximaDevolucionInvitado;
    }

    public String formatearFecha(LocalDate fechaParaFormatear){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaParaFormatear.format(formatter);
        return fechaFormateada;
    }
}