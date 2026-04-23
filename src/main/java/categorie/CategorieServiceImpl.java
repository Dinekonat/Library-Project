package categorie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("CategoryService")
@Transactional
public class CategorieServiceImpl implements ICategorieService {
	
	@Autowired
	public ICategorieDao categoryDao;
	
	@Override
	public List<Categorie> getAllCategories() {
		return categoryDao.findAll();
	}
	

}
