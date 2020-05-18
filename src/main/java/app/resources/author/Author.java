package app.resources.author;

import java.util.UUID;

public class Author {

	/**
	 * Properties
	 */
	
	private final UUID identifier;
	
	private String firstName;
	
	private String lastName;
	
	/**
	 * Constructors
	 */
	
	public Author(final String firstName,
				  final String lastName) {
		this.identifier = UUID.randomUUID();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * API (Getter & Setter)
	 */
	
	public UUID getIdentifier() {
		return identifier;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
