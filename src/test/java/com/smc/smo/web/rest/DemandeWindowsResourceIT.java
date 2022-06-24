package com.smc.smo.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.smc.smo.IntegrationTest;
import com.smc.smo.domain.DemandeWindows;
import com.smc.smo.repository.DemandeWindowsRepository;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DemandeWindowsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandeWindowsResourceIT {

    private static final String DEFAULT_NOM_APP = "AAAAAAAAAA";
    private static final String UPDATED_NOM_APP = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEMANDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEMANDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_RETOUR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_RETOUR = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/demande-windows";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandeWindowsRepository demandeWindowsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeWindowsMockMvc;

    private DemandeWindows demandeWindows;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeWindows createEntity(EntityManager em) {
        DemandeWindows demandeWindows = new DemandeWindows()
            .nomApp(DEFAULT_NOM_APP)
            .password(DEFAULT_PASSWORD)
            .action(DEFAULT_ACTION)
            .status(DEFAULT_STATUS)
            .message(DEFAULT_MESSAGE)
            .dateDemande(DEFAULT_DATE_DEMANDE)
            .dateRetour(DEFAULT_DATE_RETOUR)
            .user(DEFAULT_USER);
        return demandeWindows;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeWindows createUpdatedEntity(EntityManager em) {
        DemandeWindows demandeWindows = new DemandeWindows()
            .nomApp(UPDATED_NOM_APP)
            .password(UPDATED_PASSWORD)
            .action(UPDATED_ACTION)
            .status(UPDATED_STATUS)
            .message(UPDATED_MESSAGE)
            .dateDemande(UPDATED_DATE_DEMANDE)
            .dateRetour(UPDATED_DATE_RETOUR)
            .user(UPDATED_USER);
        return demandeWindows;
    }

    @BeforeEach
    public void initTest() {
        demandeWindows = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandeWindows() throws Exception {
        int databaseSizeBeforeCreate = demandeWindowsRepository.findAll().size();
        // Create the DemandeWindows
        restDemandeWindowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeWindows))
            )
            .andExpect(status().isCreated());

        // Validate the DemandeWindows in the database
        List<DemandeWindows> demandeWindowsList = demandeWindowsRepository.findAll();
        assertThat(demandeWindowsList).hasSize(databaseSizeBeforeCreate + 1);
        DemandeWindows testDemandeWindows = demandeWindowsList.get(demandeWindowsList.size() - 1);
        assertThat(testDemandeWindows.getNomApp()).isEqualTo(DEFAULT_NOM_APP);
        assertThat(testDemandeWindows.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testDemandeWindows.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testDemandeWindows.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDemandeWindows.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testDemandeWindows.getDateDemande()).isEqualTo(DEFAULT_DATE_DEMANDE);
        assertThat(testDemandeWindows.getDateRetour()).isEqualTo(DEFAULT_DATE_RETOUR);
        assertThat(testDemandeWindows.getUser()).isEqualTo(DEFAULT_USER);
    }

    @Test
    @Transactional
    void createDemandeWindowsWithExistingId() throws Exception {
        // Create the DemandeWindows with an existing ID
        demandeWindows.setId(1L);

        int databaseSizeBeforeCreate = demandeWindowsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeWindowsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeWindows))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeWindows in the database
        List<DemandeWindows> demandeWindowsList = demandeWindowsRepository.findAll();
        assertThat(demandeWindowsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDemandeWindows() throws Exception {
        // Initialize the database
        demandeWindowsRepository.saveAndFlush(demandeWindows);

        // Get all the demandeWindowsList
        restDemandeWindowsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeWindows.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomApp").value(hasItem(DEFAULT_NOM_APP)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].dateDemande").value(hasItem(DEFAULT_DATE_DEMANDE.toString())))
            .andExpect(jsonPath("$.[*].dateRetour").value(hasItem(DEFAULT_DATE_RETOUR.toString())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)));
    }

    @Test
    @Transactional
    void getDemandeWindows() throws Exception {
        // Initialize the database
        demandeWindowsRepository.saveAndFlush(demandeWindows);

        // Get the demandeWindows
        restDemandeWindowsMockMvc
            .perform(get(ENTITY_API_URL_ID, demandeWindows.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandeWindows.getId().intValue()))
            .andExpect(jsonPath("$.nomApp").value(DEFAULT_NOM_APP))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.dateDemande").value(DEFAULT_DATE_DEMANDE.toString()))
            .andExpect(jsonPath("$.dateRetour").value(DEFAULT_DATE_RETOUR.toString()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER));
    }

    @Test
    @Transactional
    void getNonExistingDemandeWindows() throws Exception {
        // Get the demandeWindows
        restDemandeWindowsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemandeWindows() throws Exception {
        // Initialize the database
        demandeWindowsRepository.saveAndFlush(demandeWindows);

        int databaseSizeBeforeUpdate = demandeWindowsRepository.findAll().size();

        // Update the demandeWindows
        DemandeWindows updatedDemandeWindows = demandeWindowsRepository.findById(demandeWindows.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeWindows are not directly saved in db
        em.detach(updatedDemandeWindows);
        updatedDemandeWindows
            .nomApp(UPDATED_NOM_APP)
            .password(UPDATED_PASSWORD)
            .action(UPDATED_ACTION)
            .status(UPDATED_STATUS)
            .message(UPDATED_MESSAGE)
            .dateDemande(UPDATED_DATE_DEMANDE)
            .dateRetour(UPDATED_DATE_RETOUR)
            .user(UPDATED_USER);

        restDemandeWindowsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemandeWindows.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDemandeWindows))
            )
            .andExpect(status().isOk());

        // Validate the DemandeWindows in the database
        List<DemandeWindows> demandeWindowsList = demandeWindowsRepository.findAll();
        assertThat(demandeWindowsList).hasSize(databaseSizeBeforeUpdate);
        DemandeWindows testDemandeWindows = demandeWindowsList.get(demandeWindowsList.size() - 1);
        assertThat(testDemandeWindows.getNomApp()).isEqualTo(UPDATED_NOM_APP);
        assertThat(testDemandeWindows.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testDemandeWindows.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testDemandeWindows.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDemandeWindows.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testDemandeWindows.getDateDemande()).isEqualTo(UPDATED_DATE_DEMANDE);
        assertThat(testDemandeWindows.getDateRetour()).isEqualTo(UPDATED_DATE_RETOUR);
        assertThat(testDemandeWindows.getUser()).isEqualTo(UPDATED_USER);
    }

    @Test
    @Transactional
    void putNonExistingDemandeWindows() throws Exception {
        int databaseSizeBeforeUpdate = demandeWindowsRepository.findAll().size();
        demandeWindows.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeWindowsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeWindows.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeWindows))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeWindows in the database
        List<DemandeWindows> demandeWindowsList = demandeWindowsRepository.findAll();
        assertThat(demandeWindowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandeWindows() throws Exception {
        int databaseSizeBeforeUpdate = demandeWindowsRepository.findAll().size();
        demandeWindows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeWindowsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeWindows))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeWindows in the database
        List<DemandeWindows> demandeWindowsList = demandeWindowsRepository.findAll();
        assertThat(demandeWindowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandeWindows() throws Exception {
        int databaseSizeBeforeUpdate = demandeWindowsRepository.findAll().size();
        demandeWindows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeWindowsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeWindows))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeWindows in the database
        List<DemandeWindows> demandeWindowsList = demandeWindowsRepository.findAll();
        assertThat(demandeWindowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandeWindowsWithPatch() throws Exception {
        // Initialize the database
        demandeWindowsRepository.saveAndFlush(demandeWindows);

        int databaseSizeBeforeUpdate = demandeWindowsRepository.findAll().size();

        // Update the demandeWindows using partial update
        DemandeWindows partialUpdatedDemandeWindows = new DemandeWindows();
        partialUpdatedDemandeWindows.setId(demandeWindows.getId());

        partialUpdatedDemandeWindows
            .password(UPDATED_PASSWORD)
            .message(UPDATED_MESSAGE)
            .dateDemande(UPDATED_DATE_DEMANDE)
            .dateRetour(UPDATED_DATE_RETOUR);

        restDemandeWindowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeWindows.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeWindows))
            )
            .andExpect(status().isOk());

        // Validate the DemandeWindows in the database
        List<DemandeWindows> demandeWindowsList = demandeWindowsRepository.findAll();
        assertThat(demandeWindowsList).hasSize(databaseSizeBeforeUpdate);
        DemandeWindows testDemandeWindows = demandeWindowsList.get(demandeWindowsList.size() - 1);
        assertThat(testDemandeWindows.getNomApp()).isEqualTo(DEFAULT_NOM_APP);
        assertThat(testDemandeWindows.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testDemandeWindows.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testDemandeWindows.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDemandeWindows.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testDemandeWindows.getDateDemande()).isEqualTo(UPDATED_DATE_DEMANDE);
        assertThat(testDemandeWindows.getDateRetour()).isEqualTo(UPDATED_DATE_RETOUR);
        assertThat(testDemandeWindows.getUser()).isEqualTo(DEFAULT_USER);
    }

    @Test
    @Transactional
    void fullUpdateDemandeWindowsWithPatch() throws Exception {
        // Initialize the database
        demandeWindowsRepository.saveAndFlush(demandeWindows);

        int databaseSizeBeforeUpdate = demandeWindowsRepository.findAll().size();

        // Update the demandeWindows using partial update
        DemandeWindows partialUpdatedDemandeWindows = new DemandeWindows();
        partialUpdatedDemandeWindows.setId(demandeWindows.getId());

        partialUpdatedDemandeWindows
            .nomApp(UPDATED_NOM_APP)
            .password(UPDATED_PASSWORD)
            .action(UPDATED_ACTION)
            .status(UPDATED_STATUS)
            .message(UPDATED_MESSAGE)
            .dateDemande(UPDATED_DATE_DEMANDE)
            .dateRetour(UPDATED_DATE_RETOUR)
            .user(UPDATED_USER);

        restDemandeWindowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeWindows.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeWindows))
            )
            .andExpect(status().isOk());

        // Validate the DemandeWindows in the database
        List<DemandeWindows> demandeWindowsList = demandeWindowsRepository.findAll();
        assertThat(demandeWindowsList).hasSize(databaseSizeBeforeUpdate);
        DemandeWindows testDemandeWindows = demandeWindowsList.get(demandeWindowsList.size() - 1);
        assertThat(testDemandeWindows.getNomApp()).isEqualTo(UPDATED_NOM_APP);
        assertThat(testDemandeWindows.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testDemandeWindows.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testDemandeWindows.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDemandeWindows.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testDemandeWindows.getDateDemande()).isEqualTo(UPDATED_DATE_DEMANDE);
        assertThat(testDemandeWindows.getDateRetour()).isEqualTo(UPDATED_DATE_RETOUR);
        assertThat(testDemandeWindows.getUser()).isEqualTo(UPDATED_USER);
    }

    @Test
    @Transactional
    void patchNonExistingDemandeWindows() throws Exception {
        int databaseSizeBeforeUpdate = demandeWindowsRepository.findAll().size();
        demandeWindows.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeWindowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandeWindows.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeWindows))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeWindows in the database
        List<DemandeWindows> demandeWindowsList = demandeWindowsRepository.findAll();
        assertThat(demandeWindowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandeWindows() throws Exception {
        int databaseSizeBeforeUpdate = demandeWindowsRepository.findAll().size();
        demandeWindows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeWindowsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeWindows))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeWindows in the database
        List<DemandeWindows> demandeWindowsList = demandeWindowsRepository.findAll();
        assertThat(demandeWindowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandeWindows() throws Exception {
        int databaseSizeBeforeUpdate = demandeWindowsRepository.findAll().size();
        demandeWindows.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeWindowsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeWindows))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeWindows in the database
        List<DemandeWindows> demandeWindowsList = demandeWindowsRepository.findAll();
        assertThat(demandeWindowsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandeWindows() throws Exception {
        // Initialize the database
        demandeWindowsRepository.saveAndFlush(demandeWindows);

        int databaseSizeBeforeDelete = demandeWindowsRepository.findAll().size();

        // Delete the demandeWindows
        restDemandeWindowsMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandeWindows.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandeWindows> demandeWindowsList = demandeWindowsRepository.findAll();
        assertThat(demandeWindowsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
