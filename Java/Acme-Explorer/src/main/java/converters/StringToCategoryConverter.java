
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Category;
import repositories.CategoryRepository;

@Component
@Transactional
public class StringToCategoryConverter implements Converter<String, Category> {

	@Autowired
	CategoryRepository repository;

	@Override
	public Category convert(final String text) {
		Category result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.repository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}