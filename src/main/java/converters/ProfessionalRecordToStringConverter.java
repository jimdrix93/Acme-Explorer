package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ProfessionalRecord;

@Component
@Transactional
public class ProfessionalRecordToStringConverter implements Converter<ProfessionalRecord, String> {

	@Override
	public String convert(final ProfessionalRecord param) {
		String result;

		if (param == null)
			result = null;
		else
			result = String.valueOf(param.getId());

		return result;
	}

}
