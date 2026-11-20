package com.hopital.service;

import com.hopital.dto.RendezVousRequest;
import com.hopital.entity.Medecin;
import com.hopital.entity.Patient;
import com.hopital.entity.RendezVous;
import com.hopital.entity.Salle;
import com.hopital.repository.MedecinRepository;
import com.hopital.repository.PatientRepository;
import com.hopital.repository.RendezVousRepository;
import com.hopital.repository.SalleRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Logique métier des rendez-vous.
 */
@Service
public class ConsultationService {

    /** Statut d'un rendez-vous planifié. */
    public static final String STATUT_PLANIFIE = "PLANIFIE";

    private final RendezVousRepository rendezVousRepository;
    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;
    private final SalleRepository salleRepository;

    /**
     * Crée le service avec ses dépôts.
     *
     * @param rendezVousRepository dépôt des rendez-vous
     * @param patientRepository dépôt des patients
     * @param medecinRepository dépôt des médecins
     * @param salleRepository dépôt des salles
     */
    public ConsultationService(RendezVousRepository rendezVousRepository, PatientRepository patientRepository,
            MedecinRepository medecinRepository, SalleRepository salleRepository) {
        this.rendezVousRepository = rendezVousRepository;
        this.patientRepository = patientRepository;
        this.medecinRepository = medecinRepository;
        this.salleRepository = salleRepository;
    }

    /**
     * Enregistre un rendez-vous.
     *
     * @param rdv rendez-vous à enregistrer
     * @return le rendez-vous enregistré
     */
    public RendezVous save(RendezVous rdv) {
        return rendezVousRepository.save(rdv);
    }

    /**
     * Renvoie tous les rendez-vous.
     *
     * @return la liste des rendez-vous
     */
    public List<RendezVous> findAll() {
        return rendezVousRepository.findAll();
    }

    /**
     * Crée un rendez-vous à partir des identifiants reçus.
     *
     * @param request données du rendez-vous
     * @return le rendez-vous créé, ou vide si un identifiant est absent ou inconnu
     */
    public Optional<RendezVous> creerRendezVous(RendezVousRequest request) {
        if (request.getPatientId() == null || request.getMedecinId() == null || request.getSalleId() == null) {
            return Optional.empty();
        }
        Optional<Patient> patient = patientRepository.findById(request.getPatientId());
        Optional<Medecin> medecin = medecinRepository.findById(request.getMedecinId());
        Optional<Salle> salle = salleRepository.findById(request.getSalleId());
        if (patient.isEmpty() || medecin.isEmpty() || salle.isEmpty()) {
            return Optional.empty();
        }
        RendezVous rdv = new RendezVous();
        rdv.setPatient(patient.get());
        rdv.setMedecin(medecin.get());
        rdv.setSalle(salle.get());
        rdv.setDateRdv(request.getDate());
        rdv.setHeureDebut(request.getHeureDebut());
        rdv.setHeureFin(request.getHeureFin());
        rdv.setMotifConsultation(request.getMotif());
        rdv.setStatut(STATUT_PLANIFIE);
        return Optional.of(rendezVousRepository.save(rdv));
    }

    /**
     * Renvoie les rendez-vous planifiés et datés d'un médecin.
     *
     * @param medecinId identifiant du médecin
     * @param max nombre maximum de résultats
     * @return les rendez-vous trouvés
     */
    public List<RendezVous> findRendezVousPlanifiesParMedecin(Long medecinId, int max) {
        return rendezVousRepository.findAll().stream()
                .filter(rdv -> rdv.getMedecin() != null && rdv.getMedecin().getId().equals(medecinId))
                .filter(rdv -> rdv.getDateRdv() != null)
                .filter(rdv -> STATUT_PLANIFIE.equals(rdv.getStatut()))
                .limit(max)
                .toList();
    }
}
