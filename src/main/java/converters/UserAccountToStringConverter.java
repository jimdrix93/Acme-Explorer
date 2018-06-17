
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import security.UserAccount;

@Component
@Transactional
public class UserAccountToStringConverter implements Converter<UserAccount, String> {

	@Override
	public String convert(final UserAccount param) {
		String result;

		if (param == null)
			result = null;
		else
			result = String.valueOf(param.getId());

		return result;
	}

}
