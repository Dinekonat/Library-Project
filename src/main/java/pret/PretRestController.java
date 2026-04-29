package pret;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import client.Client;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import livre.Livre;

@RestController
@RequestMapping("/rest/loan/api")
@Api(value = "Loan Rest Controller: contains all operations for managing loans")
public class PretRestController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(PretRestController.class);

	@Autowired
	private PretServiceImpl loanService;
	
	/**
	 * Retourne l'historique des prêts en cours dans la bibliothèque jusqu'à une certaine date maximale. 
	 * @param maxEndDateStr
	 * @return
	 */
	@GetMapping("/maxEndDate")
	@ApiOperation(value="List loans realized before the indicated date", response = List.class)
	@ApiResponse(code = 200, message = "Ok: successfully listed")
	public ResponseEntity<List<PretDTO>> searchAllBooksLoanBeforeThisDate(@RequestParam("date") String  maxEndDateStr) {
		List<Pret> loans = loanService.findAllPretsByEndDateBefore(LocalDate.parse(maxEndDateStr));
		// on retire tous les élts null que peut contenir cette liste => pour éviter les NPE par la suite
		loans.removeAll(Collections.singleton(null));
		List<PretDTO> loanInfosDtos = mapLoanDtosFromLoans(loans);
		return new ResponseEntity<List<PretDTO>>(loanInfosDtos, HttpStatus.OK);
	}
	
	
	/**
	 * Retourne la liste des prêts en cours d'un client. 
	 * @param email
	 * @return
	 */
	@GetMapping("/customerLoans")
	@ApiOperation(value="List loans realized before the indicated date", response = List.class)
	@ApiResponse(code = 200, message = "Ok: successfully listed")
	public ResponseEntity<List<PretDTO>> searchAllOpenedLoansOfThisCustomer(@RequestParam("email") String email) {
		List<Pret> loans = loanService.getAllOpenPretsOfThisClient(email, PretStatus.OPEN);
		// on retire tous les élts null que peut contenir cette liste => pour éviter les NPE par la suite
		loans.removeAll(Collections.singleton(null));
		List<PretDTO> loanInfosDtos = mapLoanDtosFromLoans(loans);
		return new ResponseEntity<List<PretDTO>>(loanInfosDtos, HttpStatus.OK);
	}
	
	
	/**
	 * Ajoute un nouveau prêt dans la base de données H2.
	 * @param simpleLoanDTORequest
	 * @param uriComponentBuilder
	 * @return
	 */
	@PostMapping("/addLoan")
	@ApiOperation(value = "Add a new Loan in the Library", response = PretDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 409, message = "Conflict: the loan already exist"),
			@ApiResponse(code = 201, message = "Created: the loan is successfully inserted"),
			@ApiResponse(code = 304, message = "Not Modified: the loan is unsuccessfully inserted") })
	public ResponseEntity<Boolean> createNewLoan(@RequestBody SimplePretDTO simpleLoanDTORequest,
			UriComponentsBuilder uriComponentBuilder) {
		boolean isLoanExists = loanService.checkIfLoanExists(simpleLoanDTORequest);
		if (isLoanExists) {
			return new ResponseEntity<Boolean>(false, HttpStatus.CONFLICT);
		}
		Pret LoanRequest = mapSimpleLoanDTOToLoan(simpleLoanDTORequest);
		Pret loan = loanService.savePret(LoanRequest);
		if (loan != null) {
			return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.NOT_MODIFIED);

	}
	
	
	/**
	 * Cloture le prêt de livre d'un client.
	 * @param simpleLoanDTORequest
	 * @param uriComponentBuilder
	 * @return
	 */
	@PostMapping("/closeLoan")
	@ApiOperation(value = "Marks as close a Loan in the Library", response = Boolean.class)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "No Content: no loan founded"),
			@ApiResponse(code = 200, message = "Ok: the loan is successfully closed"),
			@ApiResponse(code = 304, message = "Not Modified: the loan is unsuccessfully closed") })
	public ResponseEntity<Boolean> closeLoan(@RequestBody SimplePretDTO simpleLoanDTORequest,
			UriComponentsBuilder uriComponentBuilder) {
		Pret existingLoan = loanService.getOpenedPret(simpleLoanDTORequest);
		if (existingLoan == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.NO_CONTENT);
		}
		existingLoan.setStatus(PretStatus.CLOSED);
		Pret loan = loanService.savePret(existingLoan);
		if (loan != null) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(HttpStatus.NOT_MODIFIED);

	}
	
	/**
	 * Transforme a Loan List to LoanDTO List.
	 * 
	 * @param loans
	 * @return
	 */
	private List<PretDTO> mapLoanDtosFromLoans(List<Pret> loans) {

		Function<Pret, PretDTO> mapperFunction = (loan) -> {
			// dans loanDTO on ajoute que les données nécessaires
			PretDTO loanDTO = new PretDTO();
			loanDTO.getBookDTO().setId(loan.getPk().getLivre().getId());
			loanDTO.getBookDTO().setIsbn(loan.getPk().getLivre().getIsbn());
			loanDTO.getBookDTO().setTitle(loan.getPk().getLivre().getTitle());

			loanDTO.getCustomerDTO().setId(loan.getPk().getClient().getId());
			loanDTO.getCustomerDTO().setFirstName(loan.getPk().getClient().getFirstName());
			loanDTO.getCustomerDTO().setLastName(loan.getPk().getClient().getLastName());
			loanDTO.getCustomerDTO().setEmail(loan.getPk().getClient().getEmail());

			loanDTO.setLoanBeginDate(loan.getBeginDate());
			loanDTO.setLoanEndDate(loan.getEndDate());
			return loanDTO;
		};

		if (!CollectionUtils.isEmpty(loans)) {
			return loans.stream().map(mapperFunction).sorted().collect(Collectors.toList());
		}
		return null;
	}
	
	
	/**
	 * Transforme un SimpleLoanDTO en Loan avec les données minimalistes nécessaires
	 * 
	 * @param loanDTORequest
	 * @return
	 */
	private Pret mapSimpleLoanDTOToLoan(SimplePretDTO simpleLoanDTO) {
		Pret loan = new Pret();
		Livre book = new Livre();
		book.setId(simpleLoanDTO.getLivreId());
		Client customer = new Client();
		customer.setId(simpleLoanDTO.getClientId());
		PretId loanId = new PretId(book, customer);
		loan.setPk(loanId);
		loan.setBeginDate(simpleLoanDTO.getBeginDate());
		loan.setEndDate(simpleLoanDTO.getEndDate());
		loan.setStatus(PretStatus.OPEN);
		return loan;
	}

	







}
