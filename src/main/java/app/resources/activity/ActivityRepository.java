package app.resources.activity;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Repository;

import app.resources.author.Author;

/**
 * 
 * This repository service class serves as network data layer that is currently mocked and
 * has no relationship to {@link org.springframework.data.repository.CrudRepository}.
 */
@Repository
public class ActivityRepository {
	
	/**
	 * Properties
	 */
	
	private final Map<UUID, Activity> activities = new HashMap<>();
	
	{
		for (int i = 0; i < 10; i++) {
			final Activity activity = new Activity(
					RandomStringUtils.randomAlphabetic(6), 
					RandomStringUtils.randomAlphabetic(10),
					RandomStringUtils.randomAlphabetic(150),
					"Channel 1",
					new Date(),
					new Author(RandomStringUtils.randomAlphabetic(6), RandomStringUtils.randomAlphabetic(8)),
					1, 
					RandomUtils.nextInt(),
					"initial");
			activities.put(activity.getIdentifier(), activity);
		}
	}

	/**
	 * Data Service API
	 */
	
	/**
	 * @return {@link Map} of all available {@link Activity activities}
	 */ 
	public Map<UUID, Activity> findAll() {
		return Collections.unmodifiableMap(activities);
    }
	
	/**
	 * @return an <b>optional</b> {@link Activity activity} for the corresponding {@link Activity activityId} which may be null if no corresponding {@link Activity activity} was found.
	 */
	public Optional<Activity> findOne(final UUID activityId) {
		return Optional.ofNullable(activities.get(activityId));
	}
	
	/**
	 * @param authorId Defines the {@link Author identifier} by which all activities are filtered by.
	 * @return {@link List list} of {@link Activity activities} that all reference the given {@link Author author}.
	 */
	public List<Activity> findByAuthorId(final UUID authorId) {
		return findByPredicate(activity -> activity.getAuthor().getIdentifier().equals(authorId));
    }
	
	/**
	 * @param authorId Defines the {@link String keyword} by which all activities are filtered by.
	 * @return {@link List list} of {@link Activity activities},
	 * whose {@link Activity#getName() name}, {@link Activity#getDescription() description} or {@link Activity#getHeader() header} contain
	 * the keyword.
	 */
	public List<Activity> findByKeyword(final String keyword) {
		return findByPredicate(activity -> activity.hasKeyword(keyword));
    }
	
	/**
	 * @param predicate Defines the predicate by which the {@link List} of returned {@link Activity activities} is filtered.
	 * @return {@link List list} of {@link Activity activities} that filtered by given {@link Predicate predicate}.
	 */
	public List<Activity> findByPredicate(final Predicate<? super Activity> predicate) {
		return findAll()
				.values()
				.stream()
				.filter(predicate)
				.collect(Collectors.toList());
    }
	
	/**
	 * @param activity Defines the {@link Activity activity} to be persisted.
	 */
	public Activity save(final Activity activity) {
		return activities.put(activity.getIdentifier(), activity);
	}
	
	/**
	 * @param activityId Defines the {@link Activity activityId} to be permanently removed.
	 * @return the <b>optional</b> removed {@link Activity activity} which may be null if no corresponding {@link Activity activity} was found.
	 */
	public Optional<Activity> delete(final UUID activityId) {
		return Optional.ofNullable(activities.remove(activityId));
	}
}
