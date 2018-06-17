
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.HasValue;
import repositories.HasValueRepository;

@Component
@Transactional
public class StringToHasValueConverter implements Converter<String, HasValue> {

	@Autowired
	HasValueRepository repository;

	@Override
	public HasValue convert(final String text) {
		HasValue result;
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