
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private String				banner;
	private String				welcomeMessageEs;
	private String				welcomeMessageEn;
	private Collection<String>	spamWord;
	private double				vatTax;
	private String				countryCode;
	private int					finderCached;
	private int					finderReturn;


	@URL
	@NotBlank
	public String getBanner() {
		return this.banner;
	}
	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	public String getWelcomeMessageEs() {
		return this.welcomeMessageEs;
	}
	public void setWelcomeMessageEs(final String welcomeMessageEs) {
		this.welcomeMessageEs = welcomeMessageEs;
	}

	@NotBlank
	public String getWelcomeMessageEn() {
		return this.welcomeMessageEn;
	}
	public void setWelcomeMessageEn(final String welcomeMessageEn) {
		this.welcomeMessageEn = welcomeMessageEn;
	}

	@ElementCollection
	@NotNull
	public Collection<String> getSpamWord() {
		return this.spamWord;
	}
	public void setSpamWord(final Collection<String> spamWord) {
		this.spamWord = spamWord;
	}

	@Min(0)
	@Max(100)
	public double getVatTax() {
		return this.vatTax;
	}
	public void setVatTax(final Double vatTax) {
		this.vatTax = vatTax;
	}

	@NotBlank
	@Pattern(regexp = "^\\+\\d{1,3}$")
	public String getCountryCode() {
		return this.countryCode;
	}
	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@Min(1)
	@Max(24)
	public int getFinderCached() {
		return this.finderCached;
	}
	public void setFinderCached(final Integer finderCached) {
		this.finderCached = finderCached;
	}

	@Min(1)
	@Max(100)
	public int getFinderReturn() {
		return this.finderReturn;
	}
	public void setFinderReturn(final Integer finderReturn) {
		this.finderReturn = finderReturn;
	}
}
