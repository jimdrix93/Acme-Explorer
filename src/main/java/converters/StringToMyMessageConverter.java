
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.MyMessageRepository;
import domain.MyMessage;

@Component
@Transactional
public class StringToMyMessageConverter implements Converter<String, MyMessage> {

	@Autowired
	MyMessageRepository	repository;


	@Override
	public MyMessage convert(final String text) {
		MyMessage result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.repository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
