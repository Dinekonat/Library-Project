package livre;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ILivreDao extends JpaRepository<Livre, Integer> {
	
	Livre findByIsbnIgnoreCase(String isbn);
	
	public List<Livre> findByTitleLikeIgnoreCase(String title);
    
    @Query("SELECT b "
         + "FROM Livre b "
         + "INNER JOIN b.category cat "
         + "WHERE cat.code = :code"
       )
    
    public List<Livre> findByCategory(@Param("code") String codeCategory);
}
