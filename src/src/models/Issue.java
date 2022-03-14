package src.models;

import java.sql.Date;
import java.sql.Timestamp;

public class Issue {

	private Long id;
	private String title;
	private String description;
	private String category;
	private String timeCreated;
	private String timeModified;
	private String createdBy;
	private String status;
	private String assignedTo;
	private String modifiedBy;

	public Issue() {
		super();
	}

	public Issue(Long id, String title, String description, String category, String timeCreated, String timeModified,
			String createdBy, String status, String assignedTo, String modifiedBy) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.category = category;
		this.timeCreated = timeCreated;
		this.timeModified = timeModified;
		this.createdBy = createdBy;
		this.status = status;
		this.assignedTo = assignedTo;
		this.modifiedBy = modifiedBy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(String timeCreated) {
		this.timeCreated = timeCreated;
	}

	public String getTimeModified() {
		return timeModified;
	}

	public void setTimeModified(String timeModified) {
		this.timeModified = timeModified;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
