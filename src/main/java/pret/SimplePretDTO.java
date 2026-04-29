package pret;

import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Simple Loan Model")
public class SimplePretDTO {
	@ApiModelProperty(value = "Book id concerned by the loan")
    private Integer livreId;
	
	@ApiModelProperty(value = "Customer id concerned by the loan")
    private Integer clientId;
	
	@ApiModelProperty(value = "Loan begining date")
	private LocalDate beginDate;
	
	@ApiModelProperty(value = "Loan ending date")
	private LocalDate endDate;


    public Integer getLivreId() {
        return livreId;
    }

    public void setLivreId(Integer livreId) {
        this.livreId = livreId;
    }
    
    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }
    
    public LocalDate getBeginDate() {
		return beginDate;
	}
    
    public LocalDate getEndDate() {
		return endDate;
	}
}