
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Ranger;
import repositories.RangerRepository;

@Component
@Transactional
public class StringToRangerConverter implements Converter<String, Ranger> {

	@Autowired
	RangerRepository repository;

	@Override
	public Ranger convert(final String text) {
		Ranger result;
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