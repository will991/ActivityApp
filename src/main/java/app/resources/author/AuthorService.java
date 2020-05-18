package app.resources.author;

import static app.utilities.Utilities.normalizeUUIDString;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *	This service class serves as business logic implementation that corresponds to the
 *	{@link AuthorController controller} API.
 */
@Service
public class AuthorService {
	
	/**
	 * Properties
	 */
	
	@Autowired
	private AuthorRepository authorRepository;
	
	/**
	 * Service API
	 */
	
	/** @return a {@link List list} of all available authors */ 
	public List<Author> getAuthors() {
		return authorRepository.findAll().values().stream().collect(Collectors.toList());
	}
	
	/**
	 * @param authorId Defines the {@link Author author} identifier to be looked up.
	 * @return an <b>optional</b> {@link Author author} for the corresponding {@link Author author} identifier.
	 */
	public Optional<Author> getAuthor(final UUID authorId) {
		return authorRepository.findOne(authorId);
	}
	
	/** @param author Defines the {@link Author author} to be added. */
	public void addAuthor(final Author author) {
		authorRepository.save(author);
	}
	
	/** @param author Defines the {@link Author author} to be updated. */
	public void updateAuthor(final Author author) {
		authorRepository.save(author);
	}
	
	/**
	 * @param authorId Defines the {@link Author author} identifier to be removed.
	 * @return an <b>optional</b> {@link Author author} if one is found for the given authorId and removes it. 
	 */
	public Optional<Author> removeAuthor(final String authorId) {
		final Optional<UUID> id = normalizeUUIDString(authorId);
		if (id.isEmpty()) {
			return Optional.empty();
		}
		
		return authorRepository.delete(id.get());
	}
	
}
