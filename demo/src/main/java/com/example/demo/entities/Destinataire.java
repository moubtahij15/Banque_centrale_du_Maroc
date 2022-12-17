package com.example.demo.entities;

import javax.persistence.*;

@Entity
public class Destinataire {
    private long id;
    private Long ribDestinataire;
    private Transaction transaction;
    @OneToOne(mappedBy = "destinataires")
    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }



     @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "rib_destinataire", nullable = true)
    public Long getRibDestinataire() {
        return ribDestinataire;
    }

    public void setRibDestinataire(Long ribDestinataire) {
        this.ribDestinataire = ribDestinataire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Destinataire that = (Destinataire) o;

        if (id != that.id) return false;
        if (ribDestinataire != null ? !ribDestinataire.equals(that.ribDestinataire) : that.ribDestinataire != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (ribDestinataire != null ? ribDestinataire.hashCode() : 0);
        return result;
    }
}
