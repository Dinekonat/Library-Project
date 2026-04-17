package pret;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name = "LOAN")
@AssociationOverrides({
@AssociationOverride(name = "pk.book", joinColumns = @JoinColumn(name = "BOOK_ID")),
@AssociationOverride(name = "pk.customer", joinColumns = @JoinColumn(name = "CUSTOMER_ID"))
})
public class Pret implements Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = 144293603488149743L;

    private PretId pk = new PretId();
    
    private LocalDate beginDate;
    
    private LocalDate endDate;
    
    private Pret status;

    @EmbeddedId
    public PretId getPk() {
        return pk;
    }

    public void setPk(PretId pk) {
        this.pk = pk;
    }

    @Column(name = "BEGIN_DATE", nullable = false)
    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    @Column(name = "END_DATE", nullable = false)
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    public Pret getStatus() {
        return status;
    }

    public void setStatus(Pret status) {
        this.status = status;
    }
}
