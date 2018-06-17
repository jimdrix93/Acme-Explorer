
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	private Date	moment;
	private String	remark;
	private Date	replyDate;
	private String	reply;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm")
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getRemark() {
		return this.remark;
	}
	public void setRemark(final String remark) {
		this.remark = remark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm")
	public Date getReplyDate() {
		return this.replyDate;
	}
	public void setReplyDate(final Date replyDate) {
		this.replyDate = replyDate;
	}

	public String getReply() {
		return this.reply;
	}
	public void setReply(final String reply) {
		this.reply = reply;
	}


	//Relationships----------------------------------------------------------------------------------------

	private Trip	trip;


	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Trip getTrip() {
		return this.trip;
	}
	public void setTrip(final Trip trip) {
		this.trip = trip;
	}

}
