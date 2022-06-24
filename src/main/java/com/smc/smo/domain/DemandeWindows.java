package com.smc.smo.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DemandeWindows.
 */
@Entity
@Table(name = "demande_windows")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DemandeWindows implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_app")
    private String nomApp;

    @Column(name = "password")
    private String password;

    @Column(name = "action")
    private String action;

    @Column(name = "status")
    private String status;

    @Column(name = "message")
    private String message;

    @Column(name = "date_demande")
    private LocalDate dateDemande;

    @Column(name = "date_retour")
    private LocalDate dateRetour;

    @Column(name = "user")
    private String user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemandeWindows id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomApp() {
        return this.nomApp;
    }

    public DemandeWindows nomApp(String nomApp) {
        this.setNomApp(nomApp);
        return this;
    }

    public void setNomApp(String nomApp) {
        this.nomApp = nomApp;
    }

    public String getPassword() {
        return this.password;
    }

    public DemandeWindows password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAction() {
        return this.action;
    }

    public DemandeWindows action(String action) {
        this.setAction(action);
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStatus() {
        return this.status;
    }

    public DemandeWindows status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public DemandeWindows message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDateDemande() {
        return this.dateDemande;
    }

    public DemandeWindows dateDemande(LocalDate dateDemande) {
        this.setDateDemande(dateDemande);
        return this;
    }

    public void setDateDemande(LocalDate dateDemande) {
        this.dateDemande = dateDemande;
    }

    public LocalDate getDateRetour() {
        return this.dateRetour;
    }

    public DemandeWindows dateRetour(LocalDate dateRetour) {
        this.setDateRetour(dateRetour);
        return this;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

    public String getUser() {
        return this.user;
    }

    public DemandeWindows user(String user) {
        this.setUser(user);
        return this;
    }

    public void setUser(String user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandeWindows)) {
            return false;
        }
        return id != null && id.equals(((DemandeWindows) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeWindows{" +
            "id=" + getId() +
            ", nomApp='" + getNomApp() + "'" +
            ", password='" + getPassword() + "'" +
            ", action='" + getAction() + "'" +
            ", status='" + getStatus() + "'" +
            ", message='" + getMessage() + "'" +
            ", dateDemande='" + getDateDemande() + "'" +
            ", dateRetour='" + getDateRetour() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }
}
