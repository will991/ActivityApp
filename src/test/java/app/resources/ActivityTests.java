package app.resources;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import app.factories.AuthorFactory;
import app.resources.activity.Activity;

public class ActivityTests {

	@Test
	public void testActivityKeywords() {
		final Activity activity = new Activity(
				"Example Header", 
				"Example Name", 
				"Example Description", 
				"Channel A", 
				new Date(), 
				AuthorFactory.buildAuthor(), 
				0, 
				0, 
				"none");
		
		assertTrue("Expected activity to contain 'Example'", activity.hasKeyword("Example"));
		assertFalse("Expected activity not to contain 'Example1'", activity.hasKeyword("Example1"));
		activity.setName("Hello there test!");
		assertFalse("Expected activity not to contain 'Name'", activity.hasKeyword("Name"));
		assertTrue("Expected activity to contain 'test!'", activity.hasKeyword("test!"));
	}
}
