package pret;


import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IPretDao extends JpaRepository<Pret, PretId> {

   public List<Pret> findByEndDateBefore(LocalDate maxEndDate);
    
	   	@Query("SELECT lo "
			     + "FROM Pret lo "
			     + "INNER JOIN lo.pk.client c "
			     + "WHERE UPPER(c.email) = UPPER(?1) "
			     + "AND lo.status = ?2 ")
		public List<Pret> getAllOpenLoansOfThisClient(String email, PretStatus status);
    
       @Query("SELECT lo "
    		     + "FROM Pret lo "
    		     + "INNER JOIN lo.pk.livre b "
    		     + "INNER JOIN lo.pk.client c "
    		     + "WHERE b.id = ?1 "
    		     + "AND c.id = ?2 "
    		     + "AND lo.status = ?3 ")
    public Pret getPretByCriteria(Integer bookId, Integer customerId, PretStatus status);
}