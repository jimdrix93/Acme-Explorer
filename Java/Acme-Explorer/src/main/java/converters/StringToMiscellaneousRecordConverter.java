
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MiscellaneousRecord;
import repositories.MiscellaneousRecordRepository;

@Component
@Transactional
public class StringToMiscellaneousRecordConverter implements Converter<String, MiscellaneousRecord> {

	@Autowired
	MiscellaneousRecordRepository repository;

	@Override
	public MiscellaneousRecord convert(final String text) {
		MiscellaneousRecord result;
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