
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Application;
import domain.Explorer;
import domain.Status;
import domain.Story;
import domain.Trip;
import repositories.StoryRepository;

@Service
@Transactional
public class StoryService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private StoryRepository storyRepository;

	// Supporting services -------------------------------------------------
	@Autowired
	private ExplorerService explorerService;
	@Autowired
	private TripService tripService;

	// Constructor ----------------------------------------------------------
	public StoryService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------
	public Story create(final Trip trip) throws Exception {

		Explorer explorer;
		try {
			explorer = this.explorerService.findByPrincipal();
		} catch (final Exception e) {
			throw new Exception("story.create.error.not_logged_as_explorer");
		}

		Assert.isTrue(trip.getEndTrip().compareTo(new Date()) < 0, "story.create.error.trip_is_not_ended");

		final Story result;
		result = new Story();

		try {

			for (final Application a : trip.getApplications())
				if (explorer.getId() == a.getApplicant().getId() && a.getStatus().equals(Status.ACCEPTED)) {
					result.setWriter(this.explorerService.findByPrincipal());
					result.setTrip(trip);
					break;
				}
			Assert.notNull(result.getTrip());
		} catch (final Exception e) {
			throw new Exception("story.create.error.explorer_whithout_an_accepted_application");
		}

		return result;
	}

	public Collection<Story> findAll() {
		final Collection<Story> stories = this.storyRepository.findAll();
		Assert.notNull(stories);
		return stories;
	}

	public Story findOne(final int storyId) {
		Story result;
		result = this.storyRepository.findOne(storyId);
		this.checkExplorerPrincipal(result);
		Assert.notNull(result);

		return result;
	}

	public Story save(final Story story) {
		Story saved;
		if (story.getId() != 0)
			this.checkExplorerPrincipal(story);

		saved = this.storyRepository.save(story);

		// Reflejamos los cambios en Trip
		final Trip trip = saved.getTrip();
		trip.getStories().add(saved);
		// Crear save de Trip
		this.tripService.save(trip);
		return saved;
	}

	public void delete(final Story story) {
		this.storyRepository.delete(story.getId());
	}

	public void checkExplorerPrincipal(final Story story) {
		Explorer explorer;
		explorer = this.explorerService.findByPrincipal();
		Assert.isTrue(story.getWriter().equals(explorer));
	}
}
