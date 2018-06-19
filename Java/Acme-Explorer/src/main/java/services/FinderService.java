
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Explorer;
import domain.Finder;
import domain.Trip;

@Service
@Transactional
public class FinderService {

	//Managed repositories ------------------------------------------------
	@Autowired
	FinderRepository		finderRepository;
	@Autowired
	ExplorerService			explorerService;
	@Autowired
	TripService				tripService;
	@Autowired
	ConfigurationService	configurationService;


	//Supporting services -------------------------------------------------

	//Constructor ----------------------------------------------------------
	public FinderService() {
		super();
	}

	//Methods CRUD ---------------------------------------------------------
	public Finder create() {

		final Finder result;

		result = new Finder();
		result.setMoment(new Date(System.currentTimeMillis() - 1));

		return result;
	}
	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = this.finderRepository.findAll();

		return result;
	}
	public Finder findOne(final int finderId) {
		Finder result;

		result = this.finderRepository.findOne(finderId);
		this.checkPrincipal(result);

		return result;
	}

	public Finder save(final Finder finderRecord) {
		Finder result = null;
		final Collection<Trip> trips;
		final Collection<Finder> finders = this.finderRepository.findAll();

		finderRecord.setMoment(new Date(System.currentTimeMillis()));

		for (final Finder f : finders)
			if (Objects.equals(f.getSearch(), finderRecord.getSearch()) && Objects.equals(f.getMaxDate(), finderRecord.getMaxDate()) && Objects.equals(f.getMinDate(), finderRecord.getMinDate())
				&& Objects.equals(f.getMaxPrice(), finderRecord.getMaxPrice()) && Objects.equals(f.getMinPrice(), finderRecord.getMinPrice())) {
				result = f;
				break;
			}

		if (result == null) {
			trips = this.tripService.findTripsExplorer(finderRecord);
			finderRecord.setTrips(trips);
			result = this.finderRepository.save(finderRecord);
		}

		return result;
	}

	//Other methods ----------------------------------------------------
	public void checkPrincipal(final Finder finder) {
		Explorer explorer;
		explorer = this.explorerService.findByPrincipal();
		Assert.isTrue(explorer.getFinders().contains(finder));
	}

	//34.1 ...that is, the trips that meet the search criteria.
	public Collection<Finder> findFindersByExplorer(final Explorer explorer) {
		Collection<Finder> result;

		result = this.finderRepository.findFindersByExplorer(explorer.getId());

		return result;
	}

	// 26. Explorers have a finder ...
	public Finder saveFinderExplorer(final Finder finderRecord) {
		final Explorer explorer = this.explorerService.findByPrincipal();
		Finder result;
		final Collection<Trip> trips = this.tripService.findTripsExplorer(finderRecord);

		if (finderRecord.getId() == 0) {
			final Collection<Finder> finders = explorer.getFinders();

			// Check if finderRecord already exist in db
			for (final Finder f : finders)
				if (Objects.equals(f.getSearch(), finderRecord.getSearch()) && Objects.equals(f.getMaxDate(), finderRecord.getMaxDate()) && Objects.equals(f.getMinDate(), finderRecord.getMinDate())
					&& Objects.equals(f.getMaxPrice(), finderRecord.getMaxPrice()) && Objects.equals(f.getMinPrice(), finderRecord.getMinPrice())) {
					f.setMoment(new Date(System.currentTimeMillis() - 1));
					result = this.finderRepository.save(f);
					return f;
				}
		}

		finderRecord.setMoment(new Date(System.currentTimeMillis() - 1));
		finderRecord.setTrips(trips);

		result = this.finderRepository.save(finderRecord);

		// Add finder to Explorer
		if (finderRecord.getId() == 0) {
			explorer.getFinders().add(result);
			this.explorerService.save(explorer);
		}

		return result;
	}

	public Collection<Finder> findFinderExplorer(final Integer explorerId) {
		DateTime actualMoment = new DateTime();
		// Trae de la bd el tiempo que tiene que estar cacheada la búsqueda.
		final Integer hours = this.configurationService.findCacheTime();
		actualMoment = actualMoment.minusHours(hours);

		Collection<Finder> finders;
		final Collection<Finder> result = new ArrayList<Finder>();
		final Explorer explorer = this.explorerService.findByPrincipal();
		finders = explorer.getFinders();

		for (final Finder f : finders)
			if (new DateTime(f.getMoment()).compareTo(actualMoment) > 0)
				result.add(f);

		return result;
	}
}
