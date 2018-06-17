
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity {

	private String		name;
	private FolderType	folderType;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	@Valid
	public FolderType getFolderType() {
		return this.folderType;
	}

	public void setFolderType(final FolderType folderType) {
		this.folderType = folderType;
	}


	//Relationships-------------------------------------------------------------------------------------------------------

	private Collection<MyMessage>	messages;
	private Collection<Folder>	childs;


	@OneToMany
	@Valid
	@NotNull
	public Collection<MyMessage> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<MyMessage> messages) {
		this.messages = messages;
	}

	@OneToMany
	@Valid
	@NotNull
	public Collection<Folder> getChilds() {
		return this.childs;
	}

	public void setChilds(final Collection<Folder> childs) {
		this.childs = childs;
	}

}
