
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EndorserRecord;
import repositories.EndorserRecordRepository;

@Component
@Transactional
public class StringToEndorserRecordConverter implements Converter<String, EndorserRecord> {

	@Autowired
	EndorserRecordRepository repository;

	@Override
	public EndorserRecord convert(final String text) {
		EndorserRecord result;
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