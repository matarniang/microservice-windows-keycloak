package com.smc.smo.repository;

import com.smc.smo.domain.DemandeWindows;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Spring Data SQL repository for the DemandeWindows entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeWindowsRepository extends JpaRepository<DemandeWindows, Long> {
    @Query(value="SELECT * FROM demande_windows p WHERE user=?1",nativeQuery =true)
    List<DemandeWindows> GetDemande(String login);
}
