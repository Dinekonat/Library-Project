package categorie;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@RequestMapping("/rest/category/api")
@Api(value = "Book Category Rest Controller")
public class CategorieRestController {
	
	@Autowired
	private CategorieServiceImpl categoryService;
	
	@GetMapping("/allCategories")
	@ApiOperation(value="List all book categories of the Library", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok: successfully listed"),
			@ApiResponse(code = 204, message = "No Content: no result founded"),
	})
	public ResponseEntity<List<CategorieDTO>> getAllBookCategories(){
		List<Categorie> categories = categoryService.getAllCategories();
		if(!CollectionUtils.isEmpty(categories)) {
			//on retire tous les élts null que peut contenir cette liste
			categories.removeAll(Collections.singleton(null));
			List<CategorieDTO> categoryDTOs = categories.stream().map(category -> {
				return mapCategoryToCategoryDTO(category);
			}).collect(Collectors.toList());
			return new ResponseEntity<List<CategorieDTO>>(categoryDTOs, HttpStatus.OK);
		}
		return new ResponseEntity<List<CategorieDTO>>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Transforme un Category en CategoryDTO
	 * 
	 * @param book
	 * @return
	 */
	private CategorieDTO mapCategoryToCategoryDTO(Categorie category) {
		ModelMapper mapper = new ModelMapper();
		CategorieDTO categoryDTO = mapper.map(category, CategorieDTO.class);
		return categoryDTO;
	}







}
