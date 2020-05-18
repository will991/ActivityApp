package app.resources.activity;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.exceptions.ApiRequestException;

@RestController
@RequestMapping(value = "/activities")
public class ActivityController {
	
	/**
	 * Properties
	 */
	@Autowired
	private ActivityService activityService;
	
	/**
	 * API end point declaration
	 */
	
	/**
	 *  @Returns the a list of all existing activities.
	 */
	@GetMapping
	public List<Activity> getActivities(@RequestParam(value = "authoredBy", required = false, defaultValue = "") String authorId,
										@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
										@RequestParam(value = "createdAfter", required = false, defaultValue = "") String after,
										@RequestParam(value = "createdBefore", required = false, defaultValue = "") String before) {
		return activityService.getActivities(authorId, keyword, after, before);
	}
	
	/**
	 * @param authorId Defines the {@link Activity activity} identifier to be looked up.
	 * @return the respective {@link Activity activity} for the given identifier 
	 * or defaults to {@link HttpStatus#NOT_FOUND}.
	 */
	@GetMapping("/{activityId}")
	public Activity getActivity(@PathVariable final UUID activityId) {
		final Optional<Activity> activity = activityService.getActivity(activityId);
		
		if (activity.isEmpty()) {
			throw new ApiRequestException(HttpStatus.NOT_FOUND, "Activity Not Found");
		}
		return activity.get();
	}
	
	@PostMapping
	public void createAuthor(@RequestBody Activity activity) {
		activityService.addActivity(activity);
	}
	
	@PutMapping("/{activityId}")
	public void updateAuthor(@RequestBody final Activity activity, @PathVariable final UUID activityId) {
		activityService.updateActivity(activity);
	}
	
	/**
	 * @param activityId Defines the {@link Activity activity} identifier to be removed.
	 * @return the respective {@link Activity activity} after successful removal 
	 * or defaults to {@link HttpStatus#NOT_FOUND}.
	 */
	@DeleteMapping("/{activityId}")
	public Activity deleteAuthor(@PathVariable final UUID activityId) {
		final Optional<Activity> activity = activityService.removeActivity(activityId);
		
		if (activity.isEmpty()) {
			throw new ApiRequestException(HttpStatus.NOT_FOUND, "Activity Not Found");
		}
		
		return activity.get();
	}

}
