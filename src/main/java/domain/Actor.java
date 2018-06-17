
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	// Attributes -------------------------------------------------------------

	private String	name;
	private String	surname;
	private String	email;
	private String	phone;
	private String	address;
	private boolean	accountActivated;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	//@Pattern(regexp = "(^\\+\\d{1,3})?\\s?(\\(\\d{1,3}\\))?\\s?(\\d{4,})")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public boolean getAccountActivated() {
		return this.accountActivated;
	}

	public void setAccountActivated(final boolean accountActivated) {
		this.accountActivated = accountActivated;
	}


	// Relationships ----------------------------------------------------------

	private Collection<SocialIdentity>	socialIdentities;
	private UserAccount					userAccount;
	private Collection<Folder>			folders;
	// mensages recibidos
	private Collection<MyMessage>		receivedMessages;
	// mensages enviados
	private Collection<MyMessage>		sendedMessages;


	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@Valid
	@NotNull
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@OneToMany
	@Valid
	@NotNull
	public Collection<SocialIdentity> getSocialIdentities() {
		return this.socialIdentities;
	}

	public void setSocialIdentities(final Collection<SocialIdentity> socialIdentities) {
		this.socialIdentities = socialIdentities;
	}

	@OneToMany
	@Valid
	@NotEmpty
	public Collection<Folder> getFolders() {
		return this.folders;
	}

	public void setFolders(final Collection<Folder> folders) {
		this.folders = folders;
	}

	@OneToMany(mappedBy = "recipient")
	@Valid
	@NotNull
	public Collection<MyMessage> getReceivedMessages() {
		return this.receivedMessages;
	}

	public void setReceivedMessages(final Collection<MyMessage> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	@OneToMany(mappedBy = "sender")
	@Valid
	@NotNull
	public Collection<MyMessage> getSendedMessages() {
		return this.sendedMessages;
	}

	public void setSendedMessages(final Collection<MyMessage> sendedMessages) {
		this.sendedMessages = sendedMessages;
	}

}
