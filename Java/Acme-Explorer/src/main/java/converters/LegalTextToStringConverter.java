package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.LegalText;

@Component
@Transactional
public class LegalTextToStringConverter implements Converter<LegalText, String> {

	@Override
	public String convert(final LegalText param) {
		String result;

		if (param == null)
			result = null;
		else
			result = String.valueOf(param.getId());

		return result;
	}

}
