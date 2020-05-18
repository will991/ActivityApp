package app.resources.activity;

import static app.utilities.Utilities.normalizeUUIDString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import app.exceptions.ApiRequestException;
import app.resources.author.Author;

/**
 *	This service class serves as business logic implementation that corresponds to the
 *	{@link ActivityController controller} API.
 */
@Service
public class ActivityService {

	/**
	 * Properties
	 */
	
	@Autowired
	private ActivityRepository activityRepository;
	
	/**
	 * Service API
	 */
	
	/** @return a {@link List list} of all available activities */
	public List<Activity> getActivities() {
		return getActivities("", "", "", "");
	}
	
	/**
	 * @param authoredBy Defines the {@link Author identifier} to filter {@link Activity activities} by.
	 * @param keyword Defines the keyword to filter {@link Activity activities} by.
	 * @param after
	 * @param before
	 * @return a {@link List list} of all available activities filtered by {@link Author identifier} and keyword.
	 */
	public List<Activity> getActivities(final String authoredBy,
										final String keyword,
										final String after,
										final String before) {
		
		final List<Predicate<? super Activity>> predicates = new ArrayList<>();
		
		final Optional<UUID> authorId = normalizeUUIDString(authoredBy);
		if (authorId.isPresent()) {
			predicates.add(a -> a.getAuthor().getIdentifier().equals(authorId.get()));
		} else if ( ! authoredBy.isBlank()) {
			// Invalid author id
			throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Invalid author identifier");
		}
		
		if ( ! keyword.isBlank()) {
			predicates.add(a -> a.hasKeyword(keyword));
		}
		
		if ( ! after.isBlank()) {
			try {
				final long afterTimeInterval = Long.parseLong(after);
				predicates.add(a -> a.getCreatedAt().getTime() > afterTimeInterval);
			} catch (NumberFormatException e) {
				throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Bad request parameter 'createdAfter'. Use time intervals instead.");	
			}
			
		}
		
		if ( ! before.isBlank()) {
			try {
				final long beforeTimeInterval = Long.parseLong(before);
				predicates.add(a -> a.getCreatedAt().getTime() < beforeTimeInterval);
			} catch (NumberFormatException e) {
				throw new ApiRequestException(HttpStatus.BAD_REQUEST, "Bad request parameter 'createdBefore'. Use time intervals instead.");	
			}
		}
		
		return activityRepository
				.findByPredicate(predicates.stream().reduce(Predicate::and).orElse(a -> true))
				.stream()
				.collect(Collectors.toList());
	}
	
	/**
	 * @param activityId Defines the {@link Activity activity} identifier to be looked up.
	 * @return an <b>optional</b> {@link Activity activity} for the corresponding {@link Activity activity} identifier.
	 */
	public Optional<Activity> getActivity(final UUID activityId) {
		return activityRepository.findOne(activityId);
	}
	
	/** @param activity Defines the {@link Activity activity} to be added. */
	public void addActivity(final Activity activity) {
		activityRepository.save(activity);
	}
	
	/** @param activity Defines the {@link Activity activity} to be updated. */
	public void updateActivity(final Activity activity) {
		activityRepository.save(activity);
	}
	
	/**
	 * @param activityId Defines the {@link Activity activity} identifier to be removed.
	 * @return an <b>optional</b> {@link Activity activity} if one is found for the given activityId and removes it. 
	 */
	public Optional<Activity> removeActivity(final UUID activityId) {
		return activityRepository.delete(activityId);
	}
}
