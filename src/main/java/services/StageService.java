
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StageRepository;
import domain.Stage;

@Service
@Transactional
public class StageService {

	//Managed repositories ------------------------------------------------
	@Autowired
	private StageRepository	stageRepository;


	//Supporting services -------------------------------------------------

	//Constructor ----------------------------------------------------------
	public StageService() {
		super();
	}

	//Methods CRUD ---------------------------------------------------------
	public Stage create() {

		final Stage result;

		result = new Stage();
		result.setUsed(false);

		return result;
	}
	public Collection<Stage> findAll() {

		Collection<Stage> result;

		result = this.stageRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Stage findOne(final int stageId) {
		Stage result;

		result = this.stageRepository.findOne(stageId);
		Assert.notNull(result);

		return result;
	}

	public Stage save(final Stage stage) {
		Assert.notNull(stage);
		Stage result;

		result = this.stageRepository.save(stage);

		return result;
	}

	public void delete(final Stage stage) {
		Assert.notNull(stage);

		this.stageRepository.delete(stage);
	}

	public Collection<Stage> findStageWithoutTrip() {
		Collection<Stage> result;

		result = this.stageRepository.findStageWithoutTrip();
		Assert.notNull(result);

		return result;
	}
}
