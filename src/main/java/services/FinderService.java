
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Explorer;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	//Managed repositories ------------------------------------------------
	@Autowired
	private FinderRepository	finderRepository;


	//Supporting services -------------------------------------------------

	//Constructor ----------------------------------------------------------
	public FinderService() {
		super();
	}

	//Methods CRUD ---------------------------------------------------------
	public Finder create() {

		final Finder result;

		result = new Finder();

		return result;
	}
	public Collection<Finder> findAll() {

		Collection<Finder> result;

		result = this.finderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Finder findOne(final int finderId) {
		Finder result;

		result = this.finderRepository.findOne(finderId);
		Assert.notNull(result);

		return result;
	}

	public Finder save(final Finder finderRecord) {
		Assert.notNull(finderRecord);
		Finder result;

		result = this.finderRepository.save(finderRecord);

		return result;
	}

	public void delete(final Finder finderRecord) {
		Assert.notNull(finderRecord);

		this.finderRepository.delete(finderRecord);
	}
	//Other methods ----------------------------------------------------
	//34.1
	public Collection<Finder> findFindersByExplorer(final Explorer explorer) {
		Collection<Finder> result;
		result = this.finderRepository.findFindersByExplorer(explorer.getId());
		Assert.notNull(result);
		return result;
	}
}
