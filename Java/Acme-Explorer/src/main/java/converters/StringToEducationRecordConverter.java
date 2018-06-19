
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EducationRecord;
import repositories.EducationRecordRepository;

@Component
@Transactional
public class StringToEducationRecordConverter implements Converter<String, EducationRecord> {

	@Autowired
	EducationRecordRepository repository;

	@Override
	public EducationRecord convert(final String text) {
		EducationRecord result;
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