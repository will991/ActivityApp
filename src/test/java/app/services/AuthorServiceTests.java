package app.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import app.factories.AuthorFactory;
import app.resources.author.Author;
import app.resources.author.AuthorRepository;
import app.resources.author.AuthorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorServiceTests {

	/**
	 * Properties
	 */
	
	@Autowired
    AuthorService authorService;
	
	@SpyBean
    AuthorRepository authorRepositoryMock;
	
	/**
	 * Tests
	 */
	
	@Test
	public void testFindAllAuthors() {
        assertEquals(5, authorService.getAuthors().size());
	}
	
	@Test
	public void testFindOneAuthor() {
		final Author expectedAuthor = authorService.getAuthors().get(0);
		final Optional<Author> actualAuthor = authorService.getAuthor(expectedAuthor.getIdentifier());
		
        assertTrue("Expected author to be present", actualAuthor.isPresent());
        assertEquals(expectedAuthor, actualAuthor.get());
	}
	
	@Test
	public void testAddAuthor() {
		final Author savedAuthor = AuthorFactory.buildAuthor();
		
		when(authorRepositoryMock.save(savedAuthor)).thenReturn(savedAuthor);
		
		authorRepositoryMock.save(savedAuthor);
		
		when(authorRepositoryMock.findAll()).thenReturn(Collections.singletonMap(savedAuthor.getIdentifier(), savedAuthor));
		
		assertEquals("Expected incremented number of total authors", 1, authorService.getAuthors().size());
		assertEquals("Expected existing author to be equal saved one", savedAuthor, authorService.getAuthor(savedAuthor.getIdentifier()).get());
	}
	
	@Test
	public void testUpdateAuthor() {
		final String expectedFirstName = "Expected FirstName";
		final Author updatedAuthor = new Author(expectedFirstName, "Some LastName");
		
		authorService.addAuthor(updatedAuthor);
		assertEquals(expectedFirstName, updatedAuthor.getFirstName());
		
		updatedAuthor.setFirstName("New FirstName");
		assertEquals("New FirstName", updatedAuthor.getFirstName());
		
		authorService.updateAuthor(updatedAuthor);
		assertEquals("New FirstName", authorService.getAuthor(updatedAuthor.getIdentifier()).get().getFirstName());
		
		authorService.removeAuthor(updatedAuthor.getIdentifier().toString());
	}
	
	@Test
	public void testValidDeleteAuthor() {
		final Author deletableAuthor = AuthorFactory.buildAuthor();
		
		when(authorRepositoryMock.findAll()).thenReturn(Collections.singletonMap(deletableAuthor.getIdentifier(), deletableAuthor));
		when(authorRepositoryMock.delete(deletableAuthor.getIdentifier())).thenReturn(Optional.of(deletableAuthor));
		
		assertEquals(1, authorService.getAuthors().size());
		
		final Optional<Author> deletedAuthor= authorService.removeAuthor(deletableAuthor.getIdentifier().toString());
		
		assertTrue("Expected present optional", deletedAuthor.isPresent());
		assertTrue("Expected empty optional", authorService.getAuthor(deletableAuthor.getIdentifier()).isEmpty());
	}
}
