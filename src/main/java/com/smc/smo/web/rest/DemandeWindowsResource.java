package com.smc.smo.web.rest;

import com.smc.smo.domain.DemandeWindows;
import com.smc.smo.repository.DemandeWindowsRepository;
import com.smc.smo.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import java.time.LocalDate; 
import org.json.JSONObject;

/**
 * REST controller for managing {@link com.smc.smo.domain.DemandeWindows}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DemandeWindowsResource {

    LocalDate date = LocalDate.now();
    private final Logger log = LoggerFactory.getLogger(DemandeWindowsResource.class);

    private static final String ENTITY_NAME = "windowsDemandeWindows";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandeWindowsRepository demandeWindowsRepository;

    public DemandeWindowsResource(DemandeWindowsRepository demandeWindowsRepository) {
        this.demandeWindowsRepository = demandeWindowsRepository;
    }

    /**
     * {@code POST  /demande-windows} : Create a new demandeWindows.
     *
     * @param demandeWindows the demandeWindows to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandeWindows, or with status {@code 400 (Bad Request)} if the demandeWindows has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demande-windows")
    public ResponseEntity<DemandeWindows> createDemandeWindows(@RequestBody DemandeWindows demandeWindows) throws URISyntaxException {
        demandeWindows.setDateDemande(date);
        log.debug("REST request to save DemandeWindows : {}", demandeWindows);
        if (demandeWindows.getId() != null) {
            throw new BadRequestAlertException("A new demandeWindows cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeWindows result = demandeWindowsRepository.save(demandeWindows);
        return ResponseEntity
            .created(new URI("/api/demande-windows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demande-windows/:id} : Updates an existing demandeWindows.
     *
     * @param id the id of the demandeWindows to save.
     * @param demandeWindows the demandeWindows to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeWindows,
     * or with status {@code 400 (Bad Request)} if the demandeWindows is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandeWindows couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demande-windows/{id}")
    public ResponseEntity<DemandeWindows> updateDemandeWindows(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandeWindows demandeWindows
    ) throws URISyntaxException {
        log.debug("REST request to update DemandeWindows : {}, {}", id, demandeWindows);
        if (demandeWindows.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeWindows.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeWindowsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandeWindows result = demandeWindowsRepository.save(demandeWindows);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeWindows.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demande-windows/:id} : Partial updates given fields of an existing demandeWindows, field will ignore if it is null
     *
     * @param id the id of the demandeWindows to save.
     * @param demandeWindows the demandeWindows to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeWindows,
     * or with status {@code 400 (Bad Request)} if the demandeWindows is not valid,
     * or with status {@code 404 (Not Found)} if the demandeWindows is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandeWindows couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demande-windows/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemandeWindows> partialUpdateDemandeWindows(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandeWindows demandeWindows
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandeWindows partially : {}, {}", id, demandeWindows);
        if (demandeWindows.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeWindows.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeWindowsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandeWindows> result = demandeWindowsRepository
            .findById(demandeWindows.getId())
            .map(existingDemandeWindows -> {
                if (demandeWindows.getNomApp() != null) {
                    existingDemandeWindows.setNomApp(demandeWindows.getNomApp());
                }
                if (demandeWindows.getPassword() != null) {
                    existingDemandeWindows.setPassword(demandeWindows.getPassword());
                }
                if (demandeWindows.getAction() != null) {
                    existingDemandeWindows.setAction(demandeWindows.getAction());
                }
                if (demandeWindows.getStatus() != null) {
                    existingDemandeWindows.setStatus(demandeWindows.getStatus());
                }
                if (demandeWindows.getMessage() != null) {
                    existingDemandeWindows.setMessage(demandeWindows.getMessage());
                }
                if (demandeWindows.getDateDemande() != null) {
                    existingDemandeWindows.setDateDemande(demandeWindows.getDateDemande());
                }
                if (demandeWindows.getDateRetour() != null) {
                    existingDemandeWindows.setDateRetour(demandeWindows.getDateRetour());
                }
                if (demandeWindows.getUser() != null) {
                    existingDemandeWindows.setUser(demandeWindows.getUser());
                }

                return existingDemandeWindows;
            })
            .map(demandeWindowsRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeWindows.getId().toString())
        );
    }

    /**
     * {@code GET  /demande-windows} : get all the demandeWindows.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeWindows in body.
     */
    @GetMapping("/demande-windows")
    public List<DemandeWindows> getAllDemandeWindows() {
        log.debug("REST request to get all DemandeWindows");
        return demandeWindowsRepository.findAll();
    }

        /**
     * {@code POST  /demande-windows-login} : get all the demande-windows-login.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandes in body.
     */
    @PostMapping("/demande-windows-login")
    public List<DemandeWindows> getDemande(@RequestBody String request) {
        log.debug("REST request to get all Demandes");
        JSONObject object=new JSONObject(request);
        String username=object.getString("user");
        return demandeWindowsRepository.GetDemande(username);
    }

    /**
     * {@code POST  /demande-windows-delete} : get the demande delete.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandes in body.
     */
    @PostMapping("/demande-windows-delete")
    public ResponseEntity<Void> delete(@RequestBody String request) {
        JSONObject object=new JSONObject(request);
        Long id=object.getLong("id");
        log.debug("REST request to delete DemandeOracle : {}", id);
        demandeWindowsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code GET  /demande-windows/:id} : get the "id" demandeWindows.
     *
     * @param id the id of the demandeWindows to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeWindows, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demande-windows/{id}")
    public ResponseEntity<DemandeWindows> getDemandeWindows(@PathVariable Long id) {
        log.debug("REST request to get DemandeWindows : {}", id);
        Optional<DemandeWindows> demandeWindows = demandeWindowsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(demandeWindows);
    }

    /**
     * {@code DELETE  /demande-windows/:id} : delete the "id" demandeWindows.
     *
     * @param id the id of the demandeWindows to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demande-windows/{id}")
    public ResponseEntity<Void> deleteDemandeWindows(@PathVariable Long id) {
        log.debug("REST request to delete DemandeWindows : {}", id);
        demandeWindowsRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
