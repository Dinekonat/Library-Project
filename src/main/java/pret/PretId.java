package pret;

import java.io.Serializable;
import java.time.LocalDateTime;

import client.Client;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import livre.Livre;

@Embeddable
public class PretId implements Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = 3912193101593832821L;

    private Livre livre;
    
    private Client client;
    
    private LocalDateTime creationDateTime;
    
    public PretId() {
        super();
    }

    public PretId(Livre livre, Client client) {
        super();
        this.livre = livre;
        this.client = client;
        this.creationDateTime = LocalDateTime.now();
    }

    @ManyToOne
    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre bbok) {
        this.livre = bbok;
    }

    @ManyToOne
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    @Column(name = "CREATION_DATE_TIME")
    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
}
