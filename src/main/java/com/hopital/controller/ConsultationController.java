package com.hopital.controller;

import com.hopital.dto.RendezVousRequest;
import com.hopital.entity.RendezVous;
import com.hopital.service.ConsultationService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API REST de gestion des rendez-vous.
 */
@RestController
@RequestMapping("/api")
public class ConsultationController {

    /** Nombre maximum de rendez-vous renvoyés. */
    public static final int MAX_SIZE = 100;

    private final ConsultationService consultationService;

    /**
     * Crée le contrôleur.
     *
     * @param consultationService service des consultations
     */
    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    /**
     * Crée un rendez-vous.
     *
     * @param request données du rendez-vous
     * @return le rendez-vous créé, ou une réponse 400 si une donnée est invalide
     */
    @PostMapping("/rendezvous")
    public ResponseEntity<RendezVous> creerRendezVous(@RequestBody RendezVousRequest request) {
        return consultationService.creerRendezVous(request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Renvoie les rendez-vous planifiés d'un médecin.
     *
     * @param id identifiant du médecin
     * @return les rendez-vous planifiés
     */
    @GetMapping("/medecins/{id}/rendezvous")
    public List<RendezVous> getRendezVousByMedecin(@PathVariable Long id) {
        return consultationService.findRendezVousPlanifiesParMedecin(id, MAX_SIZE);
    }
}
