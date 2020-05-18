package app.resources.author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.exceptions.ApiRequestException;

@RestController
@RequestMapping(path = "/authors")
public class AuthorController {

	/**
	 * Properties
	 */
	
	@Autowired
	private AuthorService authorService;

	/**
	 * API end point declaration
	 */
	
	/** @return {@link List list} of {@link Author authors} */
	@GetMapping
	public List<Author> getAuthors() {
		return authorService.getAuthors();
	}

	/**
	 * @param authorId Defines the {@link Author author} identifier to be looked up.
	 * @return the respective {@link Author author} for the given identifier 
	 * or defaults to {@link HttpStatus#NOT_FOUND}.
	 */
	@GetMapping("/{authorId}")
	public Author getAuthor(@PathVariable final UUID authorId) {
		final Optional<Author> author = authorService.getAuthor(authorId);
		
		if (author.isEmpty()) {
			throw new ApiRequestException(HttpStatus.NOT_FOUND, "Author Not Found");
		}
		
		return author.get();
	}
	
	@PostMapping
	public void createAuthor(@RequestBody Author author) {
		authorService.addAuthor(author);
	}
	
	@PutMapping("/{authorId}")
	public void updateAuthor(@RequestBody final Author author, @PathVariable final String authorId) {
		authorService.updateAuthor(author);
	}
	
	/**
	 * @param authorId Defines the {@link Author author} identifier to be removed.
	 * @return the respective {@link Author author} after successful removal 
	 * or defaults to {@link HttpStatus#NOT_FOUND}.
	 */
	@DeleteMapping("{authorId}")
	public Author deleteAuthor(@PathVariable final String authorId) {
		final Optional<Author> author = authorService.removeAuthor(authorId);
		
		if (author.isEmpty()) {
			throw new ApiRequestException(HttpStatus.NOT_FOUND, "Author Not Found");
		}
		
		return author.get();
	}
}
