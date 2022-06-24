package com.smc.smo.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.smc.smo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandeWindowsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeWindows.class);
        DemandeWindows demandeWindows1 = new DemandeWindows();
        demandeWindows1.setId(1L);
        DemandeWindows demandeWindows2 = new DemandeWindows();
        demandeWindows2.setId(demandeWindows1.getId());
        assertThat(demandeWindows1).isEqualTo(demandeWindows2);
        demandeWindows2.setId(2L);
        assertThat(demandeWindows1).isNotEqualTo(demandeWindows2);
        demandeWindows1.setId(null);
        assertThat(demandeWindows1).isNotEqualTo(demandeWindows2);
    }
}
