
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Stage;
import repositories.StageRepository;

@Component
@Transactional
public class StringToStageConverter implements Converter<String, Stage> {

	@Autowired
	StageRepository repository;

	@Override
	public Stage convert(final String text) {
		Stage result;
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