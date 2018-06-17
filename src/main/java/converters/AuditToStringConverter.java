package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Audit;

@Component
@Transactional
public class AuditToStringConverter implements Converter<Audit, String> {

	@Override
	public String convert(final Audit param) {
		String result;

		if (param == null)
			result = null;
		else
			result = String.valueOf(param.getId());

		return result;
	}

}
