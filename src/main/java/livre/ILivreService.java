package livre;

import java.util.List;

public interface ILivreService {
	
	public Livre saveLivre(Livre livre);
	
	public Livre updateLivre(Livre livre);
	
	public void deleteLivre(Integer bookId);
	
	public List<Livre> findLivresByTitleOrPartTitle(String title);
	
	public Livre findLivreByIsbn(String isbn);
	
	public boolean checkIfIdexists(Integer id);
	
	public List<Livre> getLivresByCategory(String codecategory);

}
