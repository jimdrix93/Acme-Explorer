
package domain;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	private String	comments;
	private String	rejectedReason;
	private Status	status;
	private Date	moment;


	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public String getRejectedReason() {
		return this.rejectedReason;
	}

	public void setRejectedReason(final String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	@NotNull
	@Valid
	public Status getStatus() {
		return this.status;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}


	// Relationships----------------------------------------------------------------------

	private Trip		trip;
	private Explorer	applicant;
	private CreditCard	creditCard;


	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Trip getTrip() {
		return this.trip;
	}

	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Explorer getApplicant() {
		return this.applicant;
	}

	public void setApplicant(final Explorer applicant) {
		this.applicant = applicant;
	}

	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	@Transient
	public String getColor() {
		String backgroundCssClass = "background-white";

		final Calendar cal = Calendar.getInstance();
		cal.setTime(this.getTrip().getStartTrip());
		cal.add(Calendar.MONTH, -1);
		final Date unMesAntesInicioTrip = cal.getTime();

		switch (this.getStatus()) {
		case PENDING:
			if (unMesAntesInicioTrip.after(new Date()))
				backgroundCssClass = "background-pending-normal";
			else
				backgroundCssClass = "background-pending-alert";
			break;
		case ACCEPTED:
			backgroundCssClass = "background-accepted";
			break;
		case CANCELLED:
			backgroundCssClass = "background-cancelled";
			break;
		case DUE:
			backgroundCssClass = "background-due";
			break;
		case REJECTED:
			backgroundCssClass = "background-rejected";
			break;
		}

		return backgroundCssClass;
	}

}
