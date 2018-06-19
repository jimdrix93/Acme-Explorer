package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SocialIdentity;

@Component
@Transactional
public class SocialIdentityToStringConverter implements Converter<SocialIdentity, String> {

	@Override
	public String convert(final SocialIdentity param) {
		String result;

		if (param == null)
			result = null;
		else
			result = String.valueOf(param.getId());

		return result;
	}

}
