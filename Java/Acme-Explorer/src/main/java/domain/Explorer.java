
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Explorer extends Actor {

	//Relationships--------------------------------------------------------------------------------------------------------------------------
	private Collection<Finder>				finders;
	private Collection<Application>			applications;
	private Collection<Story>				stories;
	private Collection<EmergencyContact>	emergencyContacts;


	@OneToMany(cascade = {CascadeType.ALL})
	@NotNull
	@Valid
	public Collection<EmergencyContact> getEmergencyContacts() {
		return this.emergencyContacts;
	}
	public void setEmergencyContacts(final Collection<EmergencyContact> emergencyContacts) {
		this.emergencyContacts = emergencyContacts;
	}

	@OneToMany(cascade = {CascadeType.ALL})
	@Valid
	@NotNull
	public Collection<Finder> getFinders() {
		return this.finders;
	}
	public void setFinders(final Collection<Finder> finder) {
		this.finders = finder;
	}

	@OneToMany(mappedBy = "applicant")
	@Valid
	@NotNull
	public Collection<Application> getApplications() {
		return this.applications;
	}
	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@OneToMany(mappedBy = "writer")
	@Valid
	@NotNull
	public Collection<Story> getStories() {
		return this.stories;
	}
	public void setStories(final Collection<Story> stories) {
		this.stories = stories;
	}

}
