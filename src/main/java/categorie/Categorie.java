package categorie;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "CATEGORIE")
public class Categorie {
	private String code;
    private String label;
    
    public Categorie() {
    	
    }
    
    public Categorie(String code, String label) {
    	super();
    	this.code=code;
    	this.label=label;
    }
    
    @Id
    @Column(name = "CODE")
    public String getCode() {
    	return code;
    }
    
    public void setCode(String code) {
    	this.code=code;
    }
    
    @Column(name = "LABEL", nullable = false)
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    @Override
    public int hashCode() {
    	final int prime=31;
    	int result=1;
    	result = prime * result + ((code == null) ? 0 : code.hashCode());
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
    	Categorie other = (Categorie) obj;
    	if (code == null) {
    		if (other.code != null)
    			return false;
    	} else if (!code.equals(other.code))
    		return false;
    	return true;
    }

}



