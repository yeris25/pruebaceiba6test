package com.ceiba.biblioteca.repositorio;

import com.ceiba.biblioteca.entidad.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, Integer> {
    boolean existsByIdentificacionUsuario(String identificacionUsuario);

}