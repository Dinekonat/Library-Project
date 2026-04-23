package pret;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("LoanService")
@Transactional
public class PretServiceImpl implements IPretService {
	@Autowired
	public IPretDao loanDao;
	
	@Override
	public List<Pret> findAllPretsByEndDateBefore(LocalDate maxEndDate) {
		return loanDao.findByEndDateBefore(maxEndDate);
	}
	
	@Override
	public List<Pret> getAllOpenPretsOfThisClient(String emails, PretStatus Status) {
		return loanDao.getAllOpenLoansOfThisClient(emails, Status);
	}
	
	@Override
	public Pret getOpenedPret(SimplePretDTO simplePretDTO) {
		return loanDao.getPretByCriteria(simplePretDTO.getLivreId(), simplePretDTO.getClientId(), PretStatus.OPEN);

	}
	
	@Override
	public boolean checkIfLoanExists(SimplePretDTO simplePretDTO) {
		Pret pret=loanDao.getPretByCriteria(simplePretDTO.getLivreId(), simplePretDTO.getClientId(), PretStatus.OPEN);
		if(pret != null) {
			return true;
		}
		return false;

	}
	
	@Override
	public Pret savePret(Pret pret) {
		return loanDao.save(pret);
	}
	
	@Override
	public void closePret(Pret pret) {
		loanDao.save(pret);
		
	}


}
