package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Manager;

@Component
@Transactional
public class ManagerToStringConverter implements Converter<Manager, String> {

	@Override
	public String convert(final Manager param) {
		String result;

		if (param == null)
			result = null;
		else
			result = String.valueOf(param.getId());

		return result;
	}

}
