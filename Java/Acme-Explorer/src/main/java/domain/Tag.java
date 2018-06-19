
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
public class Tag extends DomainEntity {

	private String	name;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}


	//RELATIONSHIPS ---------------------------------------------------

	private Collection<HasValue>	hasValues;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "tag")
	public Collection<HasValue> getHasValues() {
		return this.hasValues;
	}

	public void setHasValues(final Collection<HasValue> hasValues) {
		this.hasValues = hasValues;
	}

}
