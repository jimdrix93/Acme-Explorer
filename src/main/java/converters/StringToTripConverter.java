
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Trip;
import repositories.TripRepository;

@Component
@Transactional
public class StringToTripConverter implements Converter<String, Trip> {

	@Autowired
	TripRepository repository;

	@Override
	public Trip convert(final String text) {
		Trip result;
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