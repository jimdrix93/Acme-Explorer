
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.HasValueRepository;
import domain.HasValue;

@Service
@Transactional
public class HasValueService {

	//Managed repositories ------------------------------------------------
	@Autowired
	private HasValueRepository	hasValueRepository;

	//Supporting services -------------------------------------------------
	@Autowired
	private TagService			tagService;


	//Constructor ----------------------------------------------------------
	public HasValueService() {
		super();
	}

	//Methods CRUD ---------------------------------------------------------
	public HasValue create() {

		final HasValue result;
		result = new HasValue();
		return result;
	}
	public Collection<HasValue> findAll() {

		Collection<HasValue> result;

		result = this.hasValueRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public HasValue findOne(final int hasValueId) {
		HasValue result;

		result = this.hasValueRepository.findOne(hasValueId);
		Assert.notNull(result);

		return result;
	}

	public HasValue save(final HasValue hasValue) {
		Assert.notNull(hasValue);
		HasValue result;

		result = this.hasValueRepository.save(hasValue);

		return result;
	}

	public void delete(final HasValue hasValue) {
		Assert.notNull(hasValue, "can´t delete a null hasValue");
		this.hasValueRepository.delete(hasValue);
	}

	// Other business methods -------------------------------------------------

	// 14.3. Manage the catalogue of tags. Note that a tag may be modified as long 
	// as its not been used to tag any trip; they can be deleted at any time.	
	public Collection<HasValue> findHasValuesByTag(final int tagId) {

		final Collection<HasValue> hv = this.hasValueRepository.findHasValueByTag(tagId);
		Assert.notNull(hv);

		return hv;
	}

}
