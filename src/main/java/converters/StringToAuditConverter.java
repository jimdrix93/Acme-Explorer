
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Audit;
import repositories.AuditRepository;

@Component
@Transactional
public class StringToAuditConverter implements Converter<String, Audit> {

	@Autowired
	AuditRepository repository;

	@Override
	public Audit convert(final String text) {
		Audit result;
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