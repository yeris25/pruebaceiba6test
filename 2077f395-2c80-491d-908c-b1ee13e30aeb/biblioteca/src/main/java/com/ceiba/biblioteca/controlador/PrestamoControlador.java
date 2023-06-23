package com.ceiba.biblioteca.controlador;

import com.ceiba.biblioteca.dto.PrestamoDTO;
import com.ceiba.biblioteca.dto.PrestamoErrorDTO;
import com.ceiba.biblioteca.dto.PrestamoAceptadoDTO;
import com.ceiba.biblioteca.dto.PrestamosDTO;

import com.ceiba.biblioteca.entidad.Prestamo;
import com.ceiba.biblioteca.servicio.PrestamoServicio;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("prestamo")
public class PrestamoControlador {
    @Autowired
    protected PrestamoServicio prestamoServicio;
    @PostMapping
    @ApiOperation("Crea un prestamo en la base de datos")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    public ResponseEntity<PrestamoDTO> crearPrestamo(@RequestBody Prestamo prestamo) {
        try {
            prestamoServicio.crearPrestamo(prestamo);
            PrestamoAceptadoDTO prestamoResponse = new PrestamoAceptadoDTO();
            prestamoResponse.setId(prestamo.getId());
            prestamoResponse.setFechaMaximaDevolucion(prestamoServicio.formatearFecha(prestamo.getFechaMaximaDevolucion()));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(prestamoResponse);
        } catch (Exception e) {
            PrestamoErrorDTO errorMessage = new PrestamoErrorDTO();
            errorMessage.setMensaje(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMessage);
        }
    }

    @GetMapping("/{id}")
    @ApiOperation("Buscar un prestamo por Id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    public ResponseEntity<PrestamoDTO> buscarPorId(@ApiParam(value = "Id del prestamo", required = true, example = "1") @PathVariable(name = "id") Integer id) {
        try {
            Prestamo prestamoEncontrado = prestamoServicio.buscarPorId(id);
            PrestamosDTO prestamoResponse = new PrestamosDTO();
            prestamoResponse.setId(prestamoEncontrado.getId());
            prestamoResponse.setIsbn(prestamoEncontrado.getIsbn());
            prestamoResponse.setIdentificacionUsuario(prestamoEncontrado.getIdentificacionUsuario());
            prestamoResponse.setFechaMaximaDevolucion(prestamoServicio.formatearFecha(prestamoEncontrado.getFechaMaximaDevolucion()));
            prestamoResponse.setTipoUsuario(prestamoEncontrado.getTipoUsuario());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(prestamoResponse);
        } catch (Exception e) {
            PrestamoErrorDTO errorMessage = new PrestamoErrorDTO();
            errorMessage.setMensaje(e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorMessage);
        }
    }
}

