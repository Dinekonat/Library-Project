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

    private static final long serialVersionUID = 144293603488149743L;

    private PretId pk = new PretId();

    private LocalDate beginDate;
    private LocalDate endDate;
    private PretStatus status;

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
    public PretStatus getStatus() {
        return status;
    }

    public void setStatus(PretStatus status) {
        this.status = status;
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pk == null) ? 0 : pk.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pret other = (Pret) obj;
		if (pk == null) {
			if (other.pk != null)
				return false;
		} else if (!pk.equals(other.pk))
			return false;
		return true;
	}

}