
package converters;

import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import domain.EmergencyContact;


@Component
@Transactional
public class StringToEmergencyContactConverter implements Converter<String, EmergencyContact> {


	@Override
	public EmergencyContact convert(final String text) {
		EmergencyContact result;
		String parts[];

		if (text == null)
			result = null;
		else
			try {
				parts = text.split("\\|");
				result = new EmergencyContact();
				result.setName(URLDecoder.decode(parts[0], "UTF-8"));
				result.setEmail(URLDecoder.decode(parts[1], "UTF-8"));
				result.setPhone(URLDecoder.decode(parts[2], "UTF-8"));

			} catch (final Throwable oops) {
			throw new RuntimeException(oops);
		}

		return result;
	}

}