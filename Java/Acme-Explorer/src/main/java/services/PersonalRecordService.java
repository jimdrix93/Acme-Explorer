
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.Curriculum;
import domain.PersonalRecord;
import domain.Ranger;

@Service
@Transactional
public class PersonalRecordService {

	//Managed repository ------------------------------------------------
	@Autowired
	private PersonalRecordRepository	personalRecordRepository;
	//Managed service ------------------------------------------------
	@Autowired
	private CurriculumService			curriculumService;
	@Autowired
	private RangerService				rangerService;


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

	public PersonalRecord save(final PersonalRecord personalRecord, final Curriculum curriculum) {
		PersonalRecord result;
		Assert.notNull(personalRecord);

		result = this.personalRecordRepository.save(personalRecord);
		if (personalRecord.getId() == 0) {
			curriculum.setPersonalRecord(result);
			this.curriculumService.save(curriculum);
		}

		return result;
	}

	public void delete(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);
		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();

		curriculum.setPersonalRecord(null);
		this.curriculumService.save(curriculum);

		this.personalRecordRepository.delete(personalRecord);
	}
}
