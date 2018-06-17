package converters;

import java.net.URLEncoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.EmergencyContact;


@Component
@Transactional
public class EmergencyContactToStringConverter implements Converter<EmergencyContact, String> {

	@Override
	public String convert(final EmergencyContact param) {
		String result;
		StringBuilder builder;

		if (param == null)
			result = null;
		else

			try {
				builder = new StringBuilder();
				builder.append(URLEncoder.encode(param.getName()  , "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(param.getEmail(), "UTF-8"));
				builder.append("|");
				builder.append(URLEncoder.encode(param.getPhone(), "UTF-8"));

				result = builder.toString();
				return result;

			} catch (final Throwable e) {
				throw new RuntimeException(e);
			}
		return result;
	}

}
