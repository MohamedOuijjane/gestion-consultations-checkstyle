package com.hopital.service;

import com.hopital.entity.Medicament;
import com.hopital.repository.MedicamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    private MedicamentRepository medicamentRepository;

    // Violation: nom de variable peu descriptif
    private int s;

    // Violation: méthode trop complexe
    public boolean verifierStock(Long medicamentId, int quantite) {
       if(medicamentId != null) {
          Medicament med = medicamentRepository.findById(medicamentId).orElse(null);
          if(med != null) {
             if(med.getStockActuel() >= quantite) {
                if(quantite > 0) {
                   return true;
                } else {        return false; }
             } else {     return false; }
          } else {     return false;    }
       } else {       return false;    }
    }
}
