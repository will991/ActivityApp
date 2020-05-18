package app.resources.activity;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import app.resources.author.Author;

public final class Activity {

	/**
	 * Properties
	 */
	
	// Defines the unique activity identifier
	private final UUID identifier;
	
	private String header;
	
	// Defines the name of the activity
	private String name;
	
	// Short description
	private String description;
	
	// References the procurement channel
	private String channel;
	
	// Defines the date of creation.
	private final Date createdAt;
	
	// References the author.
	private Author author;
	
	//private final Author author;
	
	private Integer quantity;
	
	// Price is defined in cents
	private Integer price;
	
	// Defines the status of the activity
	private String status;
	
	// Contains all keywords of this activity
	private Set<String> keywords;
	
	/**
	 * Constructors
	 */
	
	public Activity(final String header,
					final String name,
					final String description,
					final String channel,
					final Date createdAt,
					final Author author,
					final Integer quantity,
					final Integer price,
					final String status) {
		this.identifier = UUID.randomUUID();
		this.header = header;
		this.name = name;
		this.description = description;
		this.channel = channel;
		this.createdAt = createdAt;
		this.author = author;
		this.quantity = quantity;
		this.price = price;
		this.status = status;
		
		indexKeywords();
	}

	/**
	 * Public API
	 */
	public boolean hasKeyword(final String keyword) {
		return keywords.contains(keyword);
	}
	
	/**
	 * API (Getter & Setter)
	 */
	
	public UUID getIdentifier() {
		return identifier;
	}
	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
		indexKeywords();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		indexKeywords();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		indexKeywords();
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public Author getAuthor() {
		return author;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	
	/**
	 * Private
	 */
	
	private void indexKeywords() {
		final List<String> keywordSensitiveFields = Arrays.asList(header, name, description);
		keywords = new HashSet<String>(Arrays.asList(String.join(" ", keywordSensitiveFields).split(" ")));
	}
	
}
