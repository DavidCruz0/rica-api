package com.rica.rica_api.publicaciones;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rica.rica_api.compartido.RecursoNoEncontradoException;
import com.rica.rica_api.investigadores.Investigador;
import com.rica.rica_api.investigadores.InvestigadorRepository;

@Service
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final InvestigadorRepository investigadorRepository;
    private final LimitePublicacionesAnualesService limiteService;

    public PublicacionService(PublicacionRepository publicacionRepository,
                              InvestigadorRepository investigadorRepository,
                              LimitePublicacionesAnualesService limiteService) {
        this.publicacionRepository = publicacionRepository;
        this.investigadorRepository = investigadorRepository;
        this.limiteService = limiteService;
    }

    public Publicacion registrar(Publicacion publicacion) {
        // Validar que el investigador exista
        if (!investigadorRepository.existsByCorreoInstitucional_Valor(publicacion.getInvestigadorCorreo())) {
            throw new RecursoNoEncontradoException(
                "No existe un investigador con correo " + publicacion.getInvestigadorCorreo());
        }

        // Recuperar el investigador para pasarlo al servicio de dominio
        Investigador investigador = investigadorRepository
            .findByCorreoInstitucional_Valor(publicacion.getInvestigadorCorreo())
            .orElseThrow(() -> new RecursoNoEncontradoException(
                "No existe un investigador con correo " + publicacion.getInvestigadorCorreo()));

        // Validar límite anual
        if (!limiteService.puedeRegistrar(investigador, publicacion)) {
            throw new LimiteAnualExcedidoException(
                "El investigador " + investigador.getNombreCompleto() +
                " ya tiene 5 publicaciones registradas en el año " + publicacion.getAnio());
        }

        return publicacionRepository.save(publicacion);
    }

    public List<Publicacion> listarPorInvestigador(String investigadorCorreo) {
        return publicacionRepository.findByInvestigadorCorreo(investigadorCorreo);
    }

    public Publicacion buscarPorId(String id) {
        return publicacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe una publicación con id " + id));
    }
}
