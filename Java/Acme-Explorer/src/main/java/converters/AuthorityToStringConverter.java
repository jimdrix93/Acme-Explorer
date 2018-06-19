package converters;

import java.net.URLEncoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import security.Authority;

@Component
@Transactional
public class AuthorityToStringConverter implements Converter<Authority, String> {

	@Override
	public String convert(final Authority param) {
		String result;
		StringBuilder builder;

		if (param == null)
			result = null;
		else

			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(param.getAuthority()  , "UTF-8"));

				result = builder.toString();
				return result;

			} catch (final Throwable e) {
				throw new RuntimeException(e);
			}
		return result;
	}

}
