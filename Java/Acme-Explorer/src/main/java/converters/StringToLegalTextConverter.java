
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.LegalText;
import repositories.LegalTextRepository;

@Component
@Transactional
public class StringToLegalTextConverter implements Converter<String, LegalText> {

	@Autowired
	LegalTextRepository repository;

	@Override
	public LegalText convert(final String text) {
		LegalText result;
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