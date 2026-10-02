package com.rica.rica_api;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rica.rica_api.compartido.RecursoNoEncontradoException;
import com.rica.rica_api.investigadores.aplicacion.InvestigadorFactory;
import com.rica.rica_api.investigadores.aplicacion.InvestigadorService;
import com.rica.rica_api.investigadores.aplicacion.RepositorioInvestigadores;
import com.rica.rica_api.investigadores.dominio.CorreoDuplicadoException;
import com.rica.rica_api.investigadores.dominio.CorreoInstitucional;
import com.rica.rica_api.investigadores.dominio.Investigador;

@ExtendWith(MockitoExtension.class)
public class InvestigadorServiceTest {

    @Mock
    private RepositorioInvestigadores investigadorRepository;

    @Mock
    private InvestigadorFactory investigadorFactory;

    @InjectMocks
    private InvestigadorService investigadorService;

    @Test
    void buscarPorIdDevuelveElInvestigadorCuandoExiste() {
        Investigador investigador = new Investigador(1L, "Ana Torres",
                new CorreoInstitucional("ana.torres@uptc.edu.co"), "GIT-UPTC");
        when(investigadorRepository.buscarPorId(1L)).thenReturn(Optional.of(investigador));

        Investigador resultado = investigadorService.buscarPorId(1L);

        assertThat(resultado.getNombreCompleto()).isEqualTo("Ana Torres");
    }

    @Test
    void buscarPorIdLanzaExcepcionCuandoNoExiste() {
        when(investigadorRepository.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> investigadorService.buscarPorId(99L))
                .isInstanceOf(RecursoNoEncontradoException.class)
                .hasMessageContaining("99");
    }

    @Test
    void registrarRechazaCorreoInstitucionalDuplicado() {
        // Simular que la Factory lanza la excepción
        when(investigadorFactory.crear(anyString(), anyString(), anyString()))
                .thenThrow(new CorreoDuplicadoException("Ya existe un investigador registrado con el correo"));

        assertThrows(CorreoDuplicadoException.class,
                () -> investigadorService.registrar("Ana Torres", "ana.torres@uptc.edu.co", "GIT-UPTC"));
    }
}
