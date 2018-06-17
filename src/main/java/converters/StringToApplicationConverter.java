
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Application;
import repositories.ApplicationRepository;

@Component
@Transactional
public class StringToApplicationConverter implements Converter<String, Application> {

	@Autowired
	ApplicationRepository repository;

	@Override
	public Application convert(final String text) {
		Application result;
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