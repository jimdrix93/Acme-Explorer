
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Folder;
import repositories.FolderRepository;

@Component
@Transactional
public class StringToFolderConverter implements Converter<String, Folder> {

	@Autowired
	FolderRepository repository;

	@Override
	public Folder convert(final String text) {
		Folder result;
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