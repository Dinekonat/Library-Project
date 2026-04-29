package livre;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import categorie.Categorie;
import categorie.CategorieDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping("/rest/book/api")
@Api(value = "Book Rest Controller: contains all operations for managing books")
public class LivreRestController {
	public static final Logger LOGGER = LoggerFactory.getLogger(LivreRestController.class);
	
	@Autowired
	private LivreServiceImpl bookService;
	
	@PostMapping("/addBook")
	@ApiOperation(value = "Add a new Book in the Library", response = LivreDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 409, message = "Conflict: the book already exist"),
			@ApiResponse(code = 201, message = "Created: the book is successfully inserted"),
			@ApiResponse(code = 304, message = "Not Modified: the book is unsuccessfully inserted") })
	public ResponseEntity<LivreDTO> createNewLivre(@RequestBody LivreDTO bookDTORequest){
		
		Livre existingLivre = bookService.findLivreByIsbn(bookDTORequest.getIsbn());
		
		if (existingLivre != null) {
			return new ResponseEntity<LivreDTO>(HttpStatus.CONFLICT);
		}
		Livre bookRequest = mapBookDTOToBook(bookDTORequest);
		Livre book = bookService.saveLivre(bookRequest);
		if (book != null && book.getId() != null) {
			LivreDTO bookDTO = mapBookToBookDTO(book);
			return new ResponseEntity<LivreDTO>(bookDTO, HttpStatus.CREATED);
		}
		return new ResponseEntity<LivreDTO>(HttpStatus.NOT_MODIFIED);

		
	}
	
	
	@PutMapping("/updateBook")
	@ApiOperation(value = "Update/Modify an existing Book in the Library", response = LivreDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not Found : the book does not exist"),
			@ApiResponse(code = 200, message = "Ok: the book is successfully updated"),
			@ApiResponse(code = 304, message = "Not Modified: the book is unsuccessfully updated") })
	public ResponseEntity<LivreDTO> updateBook(@RequestBody LivreDTO bookDTORequest) {
		//, UriComponentsBuilder uriComponentBuilder
		if (!bookService.checkIfIdexists(bookDTORequest.getId())) {
			return new ResponseEntity<LivreDTO>(HttpStatus.NOT_FOUND);
		}
		Livre bookRequest = mapBookDTOToBook(bookDTORequest);
		Livre book = bookService.updateLivre(bookRequest);
		if (book != null) {
			LivreDTO bookDTO = mapBookToBookDTO(book);
			return new ResponseEntity<LivreDTO>(bookDTO, HttpStatus.OK);
		}
		return new ResponseEntity<LivreDTO>(HttpStatus.NOT_MODIFIED);
	}
	
	
	@DeleteMapping("/deleteBook/{bookId}")
	@ApiOperation(value = "Delete a Book in the Library, if the book does not exist, nothing is done", response = String.class)
	@ApiResponse(code = 204, message = "No Content: Book sucessfully deleted")
	public ResponseEntity<String> deleteBook(@PathVariable Integer bookId) {
		bookService.deleteLivre(bookId);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping("/searchByTitle")
	@ApiOperation(value="Search Books in the Library by title", response = List.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok: successfull research"),
			@ApiResponse(code = 204, message = "No Content: no result founded"),
	})
	public ResponseEntity<List<LivreDTO>> searchBookByTitle(@RequestParam("title") String title,
			UriComponentsBuilder uriComponentBuilder) {
		List<Livre> books = bookService.findLivresByTitleOrPartTitle(title);
		if (!CollectionUtils.isEmpty(books)) {
			// on retire tous les élts null que peut contenir cette liste => pour éviter les
			// NPE par la suite
			books.removeAll(Collections.singleton(null));
			List<LivreDTO> bookDTOs = books.stream().map(book -> {
				return mapBookToBookDTO(book);
			}).collect(Collectors.toList());
			return new ResponseEntity<List<LivreDTO>>(bookDTOs, HttpStatus.OK);
		}
		return new ResponseEntity<List<LivreDTO>>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/searchByIsbn")
	@ApiOperation(value="Search a Book in the Library by its isbn", response = LivreDTO.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok: successfull research"),
			@ApiResponse(code = 204, message = "No Content: no result founded"),
	})
	public ResponseEntity<LivreDTO> searchBookByIsbn(@RequestParam("isbn") String isbn,
			UriComponentsBuilder uriComponentBuilder) {
		Livre book = bookService.findLivreByIsbn(isbn);
		if (book != null) {
			LivreDTO bookDTO = mapBookToBookDTO(book);
			return new ResponseEntity<LivreDTO>(bookDTO, HttpStatus.OK);
		}
		return new ResponseEntity<LivreDTO>(HttpStatus.NO_CONTENT);
	}



	
	
	/**
	 * Transforme un Book en BookDTO
	 * 
	 * @param book
	 * @return
	 */
	private LivreDTO mapBookToBookDTO(Livre book) {
		ModelMapper mapper = new ModelMapper();
		LivreDTO bookDTO = mapper.map(book, LivreDTO.class);
		if (book.getCategory() != null) {
			bookDTO.setCategory(new CategorieDTO(book.getCategory().getCode(), book.getCategory().getLabel()));
		}
		return bookDTO;
	}

	
	
	/**
	 * Transforme un BookDTO en Book
	 * 
	 * @param bookDTO
	 * @return
	 */
	private Livre mapBookDTOToBook(LivreDTO bookDTO) {
		ModelMapper mapper = new ModelMapper();
		Livre book = mapper.map(bookDTO, Livre.class);
		book.setCategory(new Categorie(bookDTO.getCategory().getCode(), ""));
		book.setRegisterDate(LocalDate.now());
		return book;
	}
	
	

}
