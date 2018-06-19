package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Actor;

@Component
@Transactional
public class ActorToStringConverter implements Converter<Actor, String> {

	@Override
	public String convert(final Actor param) {
		String result;

		if (param == null)
			result = null;
		else
			result = String.valueOf(param.getId());

		return result;
	}

}
