
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TagRepository;
import domain.Administrator;
import domain.HasValue;
import domain.Tag;

@Service
@Transactional
public class TagService {

	//Managed repositories ------------------------------------------------
	@Autowired
	private TagRepository			tagRepository;

	//Supporting services -------------------------------------------------
	@Autowired
	private HasValueService			hasValueService;
	@Autowired
	private AdministratorService	administratorService;


	//Constructor ----------------------------------------------------------
	public TagService() {
		super();
	}

	//Methods CRUD ---------------------------------------------------------
	public Tag create() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		final Tag result;

		result = new Tag();

		return result;
	}

	public Collection<Tag> findAll() {

		Collection<Tag> result;

		result = this.tagRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Tag findOne(final int tagId) {

		Tag result;

		result = this.tagRepository.findOne(tagId);
		Assert.notNull(result);

		return result;
	}

	// 14.3. Manage the catalogue of tags. Note that a tag may be modified as long 
	// as its not been used to tag any trip; they can be deleted at any time.
	public Tag save(final Tag tag) {

		if (tag.getId() == 0) {
			final Administrator admin = this.administratorService.findByPrincipal();
			Assert.notNull(admin);
		}

		Assert.notNull(tag);
		Tag result;

		if (tag.getId() != 0)
			Assert.isTrue(tag.getHasValues().size() == 0);

		result = this.tagRepository.save(tag);
		return result;
	}

	public void delete(final Tag tag) {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Assert.notNull(tag);
		if (tag.getHasValues().size() != 0)
			for (final HasValue hasValue : tag.getHasValues())

				this.hasValueService.delete(hasValue);

		this.tagRepository.delete(tag);
	}
}
