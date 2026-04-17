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

}
