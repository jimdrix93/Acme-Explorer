package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Tag;

@Component
@Transactional
public class TagToStringConverter implements Converter<Tag, String> {

	@Override
	public String convert(final Tag param) {
		String result;

		if (param == null)
			result = null;
		else
			result = String.valueOf(param.getId());

		return result;
	}

}
