
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Sponsorship;
import repositories.SponsorshipRepository;

@Component
@Transactional
public class StringToSponsorshipConverter implements Converter<String, Sponsorship> {

	@Autowired
	SponsorshipRepository repository;

	@Override
	public Sponsorship convert(final String text) {
		Sponsorship result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.repository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}