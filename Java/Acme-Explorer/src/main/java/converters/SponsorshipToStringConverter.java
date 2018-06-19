package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Sponsorship;

@Component
@Transactional
public class SponsorshipToStringConverter implements Converter<Sponsorship, String> {

	@Override
	public String convert(final Sponsorship param) {
		String result;

		if (param == null)
			result = null;
		else
			result = String.valueOf(param.getId());

		return result;
	}

}
