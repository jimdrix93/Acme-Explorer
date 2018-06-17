package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Auditor;

@Component
@Transactional
public class AuditorToStringConverter implements Converter<Auditor, String> {

	@Override
	public String convert(final Auditor param) {
		String result;

		if (param == null)
			result = null;
		else
			result = String.valueOf(param.getId());

		return result;
	}

}
