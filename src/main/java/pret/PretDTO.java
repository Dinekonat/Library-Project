package pret;

import java.time.LocalDate;

import client.ClientDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import livre.LivreDTO;

@ApiModel(value = "Loan Model")
public class PretDTO implements Comparable<PretDTO> {
	
	@ApiModelProperty(value = "Book concerned by the loan")
	private LivreDTO bookDTO = new LivreDTO();

	@ApiModelProperty(value = "Customer concerned by the loan")
	private ClientDTO customerDTO = new ClientDTO();

	@ApiModelProperty(value = "Loan begining date")
	private LocalDate loanBeginDate;

	@ApiModelProperty(value = "Loan ending date")
	private LocalDate loanEndDate;
	public LivreDTO getBookDTO() {
		return bookDTO;
	}

	public void setBookDTO(LivreDTO bookDTO) {
		this.bookDTO = bookDTO;
	}

	public ClientDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(ClientDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public LocalDate getLoanBeginDate() {
		return loanBeginDate;
	}

	public void setLoanBeginDate(LocalDate loanBeginDate) {
		this.loanBeginDate = loanBeginDate;
	}

	public LocalDate getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(LocalDate loanEndDate) {
		this.loanEndDate = loanEndDate;
	}
	
	@Override
	public int compareTo(PretDTO o) {
		return o.getLoanBeginDate().compareTo(this.loanBeginDate);
	}

	

}
