package app.factories;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import app.resources.activity.Activity;

public final class ActivityFactory {

	public static final Map<UUID, Activity> buildActivities() {
		return buildActivities(10);
	}
	
	public static final Map<UUID, Activity> buildActivities(int number) {
		if (number <= 0) {
			return Collections.emptyMap();
		}
		
		final Map<UUID, Activity> activities = new HashMap<>();
		for (int i = 0; i < number; i++) {
			final Activity activity = buildActivity();
			activities.put(activity.getIdentifier(), activity);
		}
		return activities;
	}
	
	public static final Activity buildActivity() {
		return new Activity(
				RandomStringUtils.randomAlphabetic(6), 
				RandomStringUtils.randomAlphabetic(10),
				RandomStringUtils.randomAlphabetic(150),
				"Channel 1",
				new Date(),
				AuthorFactory.buildAuthor(), 
				1, 
				RandomUtils.nextInt(),
				"initial");
	}
}
