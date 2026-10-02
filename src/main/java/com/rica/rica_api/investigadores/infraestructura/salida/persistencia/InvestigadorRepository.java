package com.rica.rica_api.investigadores.infraestructura.salida.persistencia;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rica.rica_api.investigadores.dominio.Investigador;

public interface InvestigadorRepository extends JpaRepository<Investigador, Long> {

    boolean existsByCorreoInstitucional_Valor(String valor);
    Optional<Investigador> findByCorreoInstitucional_Valor(String valor);
}
