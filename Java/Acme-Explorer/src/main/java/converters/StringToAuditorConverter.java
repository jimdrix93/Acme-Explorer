
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Auditor;
import repositories.AuditorRepository;

@Component
@Transactional
public class StringToAuditorConverter implements Converter<String, Auditor> {

	@Autowired
	AuditorRepository repository;

	@Override
	public Auditor convert(final String text) {
		Auditor result;
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