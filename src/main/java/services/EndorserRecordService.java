
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;

@Service
@Transactional
public class EndorserRecordService {

	//Managed repositories ------------------------------------------------
	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;


	//Supporting services -------------------------------------------------

	//Constructor ----------------------------------------------------------
	public EndorserRecordService() {
		super();
	}

	//Methods CRUD ---------------------------------------------------------
	public EndorserRecord create() {

		final EndorserRecord result;

		result = new EndorserRecord();

		return result;
	}
	public Collection<EndorserRecord> findAll() {

		Collection<EndorserRecord> result;

		result = this.endorserRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public EndorserRecord findOne(final int endorserRecordId) {
		EndorserRecord result;

		result = this.endorserRecordRepository.findOne(endorserRecordId);
		Assert.notNull(result);

		return result;
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		EndorserRecord result;

		result = this.endorserRecordRepository.save(endorserRecord);

		return result;
	}

	public void delete(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);

		this.endorserRecordRepository.delete(endorserRecord);
	}
	//Other methods ----------------------------------------------------
}
