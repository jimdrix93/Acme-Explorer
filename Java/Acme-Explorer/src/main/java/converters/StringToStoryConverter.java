
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Story;
import repositories.StoryRepository;

@Component
@Transactional
public class StringToStoryConverter implements Converter<String, Story> {

	@Autowired
	StoryRepository repository;

	@Override
	public Story convert(final String text) {
		Story result;
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