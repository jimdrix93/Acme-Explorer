
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Location {

	private String	name;
	private String	coordinates;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@Pattern(regexp = "^([-+]?[\\d]{1,2}.\\d+),\\s*([-+]?[\\d]{1,3}.\\d+)")
	public String getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(final String coordinates) {
		this.coordinates = coordinates;
	}

}
