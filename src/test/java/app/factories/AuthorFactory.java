package app.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

import app.resources.author.Author;

public final class AuthorFactory {

	public static final Author buildAuthor() {
		return new Author(RandomStringUtils.randomAlphabetic(6), RandomStringUtils.randomAlphabetic(8));
	}
	
	public static final Map<UUID, Author> buildAuthors() {
		final Map<UUID, Author> authors  = new HashMap<>();
		final Author author = new Author(RandomStringUtils.randomAlphabetic(6), RandomStringUtils.randomAlphabetic(8));
		authors.put(author.getIdentifier(), author);
		return Collections.unmodifiableMap(authors);
	}
	
}
