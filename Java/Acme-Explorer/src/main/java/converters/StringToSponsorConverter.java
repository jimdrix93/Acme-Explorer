
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Sponsor;
import repositories.SponsorRepository;

@Component
@Transactional
public class StringToSponsorConverter implements Converter<String, Sponsor> {

	@Autowired
	SponsorRepository repository;

	@Override
	public Sponsor convert(final String text) {
		Sponsor result;
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