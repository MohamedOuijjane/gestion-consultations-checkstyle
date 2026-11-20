package com.hopital.service;

import com.hopital.repository.MedicamentRepository;
import org.springframework.stereotype.Service;

/**
 * Gestion du stock des médicaments.
 */
@Service
public class StockService {

    private final MedicamentRepository medicamentRepository;

    /**
     * Crée le service de stock.
     *
     * @param medicamentRepository dépôt des médicaments
     */
    public StockService(MedicamentRepository medicamentRepository) {
        this.medicamentRepository = medicamentRepository;
    }

    /**
     * Vérifie si la quantité demandée est disponible en stock.
     *
     * @param medicamentId identifiant du médicament
     * @param quantite quantité demandée
     * @return vrai si le médicament existe et que le stock suffit
     */
    public boolean verifierStock(Long medicamentId, int quantite) {
        if (medicamentId == null || quantite <= 0) {
            return false;
        }
        return medicamentRepository.findById(medicamentId)
                .map(med -> med.getStockActuel() >= quantite)
                .orElse(false);
    }
}
