
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Trip extends DomainEntity {

	private String	ticker;
	private String	title;
	private String	description;
	private String	requirements;
	private Date	publicationDate;
	private Date	startTrip;
	private Date	endTrip;
	private String	cancelationReason;
	private boolean	cancelated;


	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])-[A-Z]{4}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getRequirements() {
		return this.requirements;
	}

	public void setRequirements(final String requirements) {
		this.requirements = requirements;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getPublicationDate() {
		return this.publicationDate;
	}

	public void setPublicationDate(final Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getStartTrip() {
		return this.startTrip;
	}

	public void setStartTrip(final Date startTrip) {
		this.startTrip = startTrip;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getEndTrip() {
		return this.endTrip;
	}

	public void setEndTrip(final Date endTrip) {
		this.endTrip = endTrip;
	}

	public String getCancelationReason() {
		return this.cancelationReason;
	}

	public void setCancelationReason(final String cancelationReason) {
		this.cancelationReason = cancelationReason;
	}

	public boolean getCancelated() {
		return this.cancelated;
	}

	public void setCancelated(final boolean cancelated) {
		this.cancelated = cancelated;
	}


	//Relationships---------------------------------------------------------------------------------------------------

	private Category					category;
	private Collection<Application>		applications;
	private Collection<Note>			notes;
	private Collection<Audit>			audits;
	//	private Sponsorship					sponsorship;
	private Manager						manager;
	private Ranger						ranger;
	private Collection<Stage>			stages;
	private Collection<SurvivalClass>	survivalClasses;
	private Collection<Story>			stories;
	private LegalText					legalText;
	private Collection<HasValue>		hasValues;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "trip")
	public Collection<HasValue> getHasValues() {
		return this.hasValues;
	}

	public void setHasValues(final Collection<HasValue> hasValues) {
		this.hasValues = hasValues;
	}

	@ManyToOne(optional = false)
	@Valid
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@OneToMany(mappedBy = "trip")
	@Valid
	@NotNull
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> application) {
		this.applications = application;
	}

	@OneToMany(mappedBy = "trip")
	@Valid
	@NotNull
	public Collection<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(final Collection<Note> note) {
		this.notes = note;
	}

	@OneToMany(mappedBy = "trip")
	@Valid
	@NotNull
	public Collection<Audit> getAudits() {
		return this.audits;
	}

	public void setAudits(final Collection<Audit> audit) {
		this.audits = audit;
	}

	//	@ManyToOne(optional = false)
	//	@Valid
	//	@NotNull
	//	public Sponsorship getSponsorship() {
	//		return this.sponsorship;
	//	}
	//
	//	public void setSponsorship(final Sponsorship sponsorship) {
	//		this.sponsorship = sponsorship;
	//	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Manager getManager() {
		return this.manager;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Ranger getRanger() {
		return this.ranger;
	}

	public void setRanger(final Ranger ranger) {
		this.ranger = ranger;
	}

	@OneToMany(cascade = CascadeType.ALL)
	//, mappedBy = "trip"
	@Valid
	@NotEmpty
	public Collection<Stage> getStages() {
		return this.stages;
	}

	public void setStages(final Collection<Stage> stages) {
		this.stages = stages;
	}

	@OneToMany(mappedBy = "trip")
	@Valid
	@NotNull
	public Collection<SurvivalClass> getSurvivalClasses() {
		return this.survivalClasses;
	}

	public void setSurvivalClasses(final Collection<SurvivalClass> survivalClass) {
		this.survivalClasses = survivalClass;
	}

	@OneToMany(mappedBy = "trip")
	@Valid
	@NotNull
	public Collection<Story> getStories() {
		return this.stories;
	}

	public void setStories(final Collection<Story> stories) {
		this.stories = stories;
	}

	@ManyToOne(optional = false, cascade = {
		CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
	})
	@Valid
	@NotNull
	public LegalText getLegalText() {
		return this.legalText;
	}

	public void setLegalText(final LegalText legalText) {
		this.legalText = legalText;
	}


	// Derived --------------------------------------------------

	private double	price;


	@Min(0)
	public double getPrice() {

		return this.price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}
}
