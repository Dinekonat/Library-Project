package pret;

import java.time.LocalDate;
import java.util.List;

public interface IPretService {
	
	public List<Pret> findAllPretsByEndDateBefore(LocalDate maxEndDate);
	
	public List<Pret> getAllOpenPretsOfThisClient(String emails, PretStatus Status);
	
	public Pret getOpenedPret(SimplePretDTO simpleLoanDTO);
	    
	public boolean checkIfLoanExists(SimplePretDTO simplePretDTO);
	    
	public Pret savePret(Pret pret);
	    
	public void closePret(Pret pret);

}
