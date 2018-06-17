
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Note;
import repositories.NoteRepository;

@Component
@Transactional
public class StringToNoteConverter implements Converter<String, Note> {

	@Autowired
	NoteRepository repository;

	@Override
	public Note convert(final String text) {
		Note result;
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