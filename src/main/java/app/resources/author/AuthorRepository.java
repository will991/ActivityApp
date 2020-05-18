package app.resources.author;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Repository;

/**
 * 
 * This repository service class serves as network data layer that is currently mocked and
 * has no relationship to {@link org.springframework.data.repository.CrudRepository}.
 */
@Repository
public class AuthorRepository {

	/**
	 * Properties
	 */
	
	private final Map<UUID, Author> authors = new HashMap<>();
	
	{
		for (int i = 0; i < 5; i++) {
			final Author author = new Author(
					RandomStringUtils.randomAlphabetic(6), 
					RandomStringUtils.randomAlphabetic(10));
			authors.put(author.getIdentifier(), author);
		}
	}

	/**
	 * Data Service API
	 */
	
	/**
	 * @return {@link Map} of all available {@link Author authors}
	 */
	public Map<UUID, Author> findAll() {
		return Collections.unmodifiableMap(authors);
    }
	
	/**
	 * @return an <b>optional</b> {@link Author author} for the corresponding {@link Author authorId} which may be null if no corresponding {@link Author author} was found.
	 */
	public Optional<Author> findOne(final UUID authorId) {
		return Optional.ofNullable(authors.get(authorId));
	}
	
	/**
	 * @param author Defines the {@link Author author} to be persisted.
	 */
	public Author save(final Author author) {
		return authors.put(author.getIdentifier(), author);
	}
	
	/**
	 * @param authorId Defines the {@link Author authorId} to be permanently removed.
	 * @return the <b>optional</b> removed {@link Author author} which may be null if no corresponding {@link Author author} was found.
	 */
	public Optional<Author> delete(final UUID authorId) {
		return Optional.ofNullable(authors.remove(authorId));
	}
}
