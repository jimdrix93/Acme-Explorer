
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	/* Atributos */
	private String	ticker;


	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^\\d{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])-[A-Z]{4}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String t) {
		this.ticker = t;
	}


	/* Relaciones */
	//	private Ranger							ranger;
	private PersonalRecord					personalRecord;
	private Collection<EducationRecord>		educationRecords;
	private Collection<ProfessionalRecord>	professionalRecords;
	private Collection<EndorserRecord>		endorserRecords;
	private Collection<MiscellaneousRecord>	miscellaneousRecords;


	//	@OneToOne(optional = false)
	//	@Valid
	//	@NotNull
	//	public Ranger getRanger() {
	//		return this.ranger;
	//	}
	//
	//	public void setRanger(final Ranger ranger) {
	//		this.ranger = ranger;
	//	}

	@OneToOne(optional = true, cascade = CascadeType.ALL)
	@Valid
	@NotNull
	public PersonalRecord getPersonalRecord() {
		return this.personalRecord;
	}

	public void setPersonalRecord(final PersonalRecord personalRecord) {
		this.personalRecord = personalRecord;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	@NotNull
	public Collection<EducationRecord> getEducationRecords() {
		return this.educationRecords;
	}

	public void setEducationRecords(final Collection<EducationRecord> educationRecords) {
		this.educationRecords = educationRecords;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	@NotNull
	public Collection<ProfessionalRecord> getProfessionalRecords() {
		return this.professionalRecords;
	}

	public void setProfessionalRecords(final Collection<ProfessionalRecord> professionalRecords) {
		this.professionalRecords = professionalRecords;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	@NotNull
	public Collection<EndorserRecord> getEndorserRecords() {
		return this.endorserRecords;
	}

	public void setEndorserRecords(final Collection<EndorserRecord> endorserRecords) {
		this.endorserRecords = endorserRecords;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@Valid
	@NotNull
	public Collection<MiscellaneousRecord> getMiscellaneousRecords() {
		return this.miscellaneousRecords;
	}

	public void setMiscellaneousRecords(final Collection<MiscellaneousRecord> miscellaneousRecords) {
		this.miscellaneousRecords = miscellaneousRecords;
	}
}
