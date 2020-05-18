package app.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import app.factories.ActivityFactory;
import app.factories.AuthorFactory;
import app.resources.activity.Activity;
import app.resources.activity.ActivityRepository;
import app.resources.activity.ActivityService;
import app.resources.author.Author;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityServiceTests {
	
	/**
	 * Properties
	 */
	
	@Autowired
    ActivityService activityService;
	
	// TODO: Change SpyBean to MockBean if real database is connected!
	@SpyBean
    ActivityRepository activityRepositoryMock;

	/**
	 * Tests
	 */
	
	@Test
	public void testFindAllActivities() {
        assertEquals(10, activityService.getActivities().size());
	}
	
	@Test
	public void testFindActivitiesFilteredByAuthor() {
		final Author author = AuthorFactory.buildAuthor();
		final Activity activity1 = ActivityFactory.buildActivity();
		final Activity activity2 = ActivityFactory.buildActivity();
		
		activity1.setAuthor(author);
		activity2.setAuthor(author);
		
		activityService.addActivity(activity1);
		activityService.addActivity(activity2);
		
		final List<Activity> expected = Arrays.asList(activity1, activity2);
		final List<Activity> activitiesFilteredByAuthor = activityService.getActivities(author.getIdentifier().toString(), "", "", "");
		assertEquals(2, activitiesFilteredByAuthor.size());
		assertTrue(activitiesFilteredByAuthor.stream().filter(a -> ! expected.contains(a)).collect(Collectors.toList()).isEmpty());
		assertTrue(expected.stream().filter(a -> ! activitiesFilteredByAuthor.contains(a)).collect(Collectors.toList()).isEmpty());

		// clean up
		activityService.removeActivity(activity1.getIdentifier());
		activityService.removeActivity(activity2.getIdentifier());
	}
	
	@Test
	public void testFindActivitiesFilteredByKeyword() {
		final Activity activity = ActivityFactory.buildActivity();
		activity.setDescription("Test description");
		
		activityService.addActivity(activity);
		final List<Activity> activitiesFilteredByKeyword = activityService.getActivities("", "Test", "", "");
		assertEquals(1, activitiesFilteredByKeyword.size());
		assertEquals(activity.getIdentifier(), activitiesFilteredByKeyword.get(0).getIdentifier());
		
		// clean up
		activityService.removeActivity(activity.getIdentifier());
	}
	
	@Test
	public void testFindActivitiesFilteredByTimeInterval() {
		final LocalDate localDate = LocalDate.of(2000, 2, 23);
		final Date oldDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		final Activity activity = new Activity("header", 
											   "name", 
											   "description", 
											   "channel", 
											   oldDate,
											   AuthorFactory.buildAuthor(), 
											   1, 
											   100, 
											   "initial");
		
		activityService.addActivity(activity);
		
		List<Activity> filteredActivities = activityService.getActivities("", "", "", String.valueOf(oldDate.getTime() - 5L)); // minus some offset
		assertTrue(filteredActivities.isEmpty());
		
		
		filteredActivities = activityService.getActivities("", "", "", String.valueOf(oldDate.getTime() + 5L)); // plus some offset
		assertFalse(filteredActivities.isEmpty());
		assertEquals(1, filteredActivities.size());
		assertEquals(activity, filteredActivities.get(0));
		
		// clean up
		activityService.removeActivity(activity.getIdentifier());
	}
	
	@Test
	public void testFindOneActivity() {
		final Activity expectedActivity = activityService.getActivities().get(0);
		final Optional<Activity> actualActivity = activityService.getActivity(expectedActivity.getIdentifier());
		
        assertTrue("Expected activity to be present", actualActivity.isPresent());
        assertEquals(expectedActivity, actualActivity.get());
	}
	
	@Test
	public void testInvalidFindOneActivity() {
		final Activity nonPersistedActivity = ActivityFactory.buildActivity();
		final Optional<Activity> actualActivity = activityService.getActivity(nonPersistedActivity.getIdentifier());
		
        assertTrue("Expected activity to be empty", actualActivity.isEmpty());
	}
	
	@Test
	public void testAddActivity() {
		final Activity savedActivity = ActivityFactory.buildActivity();
		
		when(activityRepositoryMock.save(savedActivity)).thenReturn(savedActivity);
		
		activityRepositoryMock.save(savedActivity);
		
		when(activityRepositoryMock.findAll()).thenReturn(Collections.singletonMap(savedActivity.getIdentifier(), savedActivity));

		assertEquals("Expected incremented number of total activities", 1, activityService.getActivities().size());
		assertEquals("Expected existing activity to be equal saved one", savedActivity, activityService.getActivity(savedActivity.getIdentifier()).get());
	}
	
	@Test
	public void testUpdateActivity() {
		final String expectedName = "Expected Name";
		final Activity updatedActivity = new Activity("header",
													  expectedName,
													  "some description",
													  "channel",
													  new Date(), 
													  AuthorFactory.buildAuthor(), 
													  5,
													  500,
													  "status");
		
		activityService.addActivity(updatedActivity);
		assertEquals(expectedName, updatedActivity.getName());
		
		updatedActivity.setName("New Name");
		assertEquals("New Name", updatedActivity.getName());
		
		activityService.updateActivity(updatedActivity);
		assertEquals("New Name", activityService.getActivity(updatedActivity.getIdentifier()).get().getName());
		
		// clean up
		activityService.removeActivity(updatedActivity.getIdentifier());
	}

	@Test
	public void testNonExistingDeleteActivity() {
		final Activity deletableActivity = ActivityFactory.buildActivity();
		final Optional<Activity> deletedActivity = activityService.removeActivity(deletableActivity.getIdentifier());
		
		assertTrue("Expected empty optional", deletedActivity.isEmpty());
		assertTrue(activityService.getActivity(deletableActivity.getIdentifier()).isEmpty());
	}
	
	@Test
	public void testValidDeleteActivity() {
		final Activity deletableActivity = activityService.getActivities().get(0);
		when(activityRepositoryMock.findAll()).thenReturn(Collections.singletonMap(deletableActivity.getIdentifier(), deletableActivity));
		when(activityRepositoryMock.delete(deletableActivity.getIdentifier())).thenReturn(Optional.of(deletableActivity));
		
		assertEquals(1, activityService.getActivities().size());
		
		final Optional<Activity> deletedActivity = activityService.removeActivity(deletableActivity.getIdentifier());
		
		assertTrue("Expected present optional", deletedActivity.isPresent());
		assertTrue("Expected empty optional", activityService.getActivity(deletableActivity.getIdentifier()).isEmpty());
	}
}
