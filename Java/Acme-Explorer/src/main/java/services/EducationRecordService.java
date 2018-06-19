
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.Ranger;

@Service
@Transactional
public class EducationRecordService {

	//Managed repositories ------------------------------------------------
	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	//Managed service ------------------------------------------------
	@Autowired
	private CurriculumService			curriculumService;
	@Autowired
	private RangerService				rangerService;


	//Supporting services -------------------------------------------------

	//Constructor ----------------------------------------------------------
	public EducationRecordService() {
		super();
	}

	//Methods CRUD ---------------------------------------------------------
	public EducationRecord create() {

		final EducationRecord result;

		result = new EducationRecord();

		return result;
	}
	public Collection<EducationRecord> findAll() {

		Collection<EducationRecord> result;

		result = this.educationRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public EducationRecord findOne(final int educationRecordId) {
		EducationRecord result;

		result = this.educationRecordRepository.findOne(educationRecordId);
		Assert.notNull(result);

		return result;
	}

	public EducationRecord save(final EducationRecord educationRecord, final Curriculum curriculum) {
		Assert.notNull(educationRecord);
		EducationRecord result;

		result = this.educationRecordRepository.save(educationRecord);
		if (educationRecord.getId() == 0) {
			curriculum.getEducationRecords().add(result);
			this.curriculumService.save(curriculum);
		}

		return result;
	}

	public void delete(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();

		curriculum.getEducationRecords().remove(educationRecord);
		this.curriculumService.save(curriculum);

		this.educationRecordRepository.delete(educationRecord);
	}
	//Other methods ----------------------------------------------------
}
