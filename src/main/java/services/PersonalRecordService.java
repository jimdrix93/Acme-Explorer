
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	//Managed repository ------------------------------------------------
	@Autowired
	private PersonalRecordRepository	personalRecordRepository;


	//Constructor -------------------------------------------------------
	public PersonalRecordService() {
		super();
	}

	//Methods CRUD ------------------------------------------------------
	public PersonalRecord create() {
		final PersonalRecord result = new PersonalRecord();

		return result;
	}

	public PersonalRecord findOne(final Integer personalRecordID) {
		PersonalRecord result;
		result = this.personalRecordRepository.findOne(personalRecordID);
		return result;
	}

	public Collection<PersonalRecord> findAll() {
		Collection<PersonalRecord> result;
		result = this.personalRecordRepository.findAll();
		return result;
	}

	public PersonalRecord save(final PersonalRecord personalRecord) {
		PersonalRecord result;
		Assert.notNull(personalRecord);

		result = this.personalRecordRepository.save(personalRecord);

		return result;
	}

	public void delete(final PersonalRecord personalRecord) {

		this.personalRecordRepository.delete(personalRecord);
	}
}
