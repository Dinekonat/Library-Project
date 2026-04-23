package livre;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("bookService")
@Transactional
public class LivreServiceImpl implements ILivreService {
	
	@Autowired
	private ILivreDao bookDao;
	
	@Override
	public Livre saveLivre(Livre livre) {
		 return bookDao.save(livre);
	}
	
	@Override
	public Livre updateLivre(Livre livre) {
		return bookDao.save(livre);
	}
	
	@Override
	public void deleteLivre(Integer bookId) {
		bookDao.deleteById(bookId);
		
	}
	
	@Override
	public boolean checkIfIdexists(Integer id) {
		return bookDao.existsById(id);
	}
	
	@Override
    public List<Livre> findLivresByTitleOrPartTitle(String title) {
        return bookDao.findByTitleLikeIgnoreCase((new StringBuilder()).append("%").append(title).append("%").toString());
    }
	
	@Override
	public Livre findLivreByIsbn(String isbn) {
		return bookDao.findByIsbnIgnoreCase(isbn);
	}
	
	
	@Override
	public List<Livre> getLivresByCategory(String codecategory) {
		return bookDao.findByCategory(codecategory);
	}
}
