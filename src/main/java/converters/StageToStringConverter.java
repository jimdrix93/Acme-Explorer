package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Stage;

@Component
@Transactional
public class StageToStringConverter implements Converter<Stage, String> {

	@Override
	public String convert(final Stage param) {
		String result;

		if (param == null)
			result = null;
		else
			result = String.valueOf(param.getId());

		return result;
	}

}
