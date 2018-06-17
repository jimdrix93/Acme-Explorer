
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Finder;
import repositories.FinderRepository;

@Component
@Transactional
public class StringToFinderConverter implements Converter<String, Finder> {

	@Autowired
	FinderRepository repository;

	@Override
	public Finder convert(final String text) {
		Finder result;
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