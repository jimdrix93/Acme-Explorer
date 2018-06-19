package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Ranger;

@Component
@Transactional
public class RangerToStringConverter implements Converter<Ranger, String> {

	@Override
	public String convert(final Ranger param) {
		String result;

		if (param == null)
			result = null;
		else
			result = String.valueOf(param.getId());

		return result;
	}

}
