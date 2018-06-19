
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.Curriculum;
import domain.MiscellaneousRecord;
import domain.Ranger;

@Service
@Transactional
public class MiscellaneousRecordService {

	//Managed repository ------------------------------------------------
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	//Managed service ------------------------------------------------
	@Autowired
	private CurriculumService				curriculumService;
	@Autowired
	private RangerService					rangerService;


	//Constructor -------------------------------------------------------
	public MiscellaneousRecordService() {
		super();
	}

	//Methods CRUD ------------------------------------------------------
	public MiscellaneousRecord create() {
		final MiscellaneousRecord result = new MiscellaneousRecord();

		return result;
	}

	public MiscellaneousRecord findOne(final Integer miscellaneousRecordID) {
		MiscellaneousRecord result;
		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordID);
		return result;
	}

	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> result;
		result = this.miscellaneousRecordRepository.findAll();
		return result;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord, final Curriculum curriculum) {
		Assert.notNull(miscellaneousRecord);
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		if (miscellaneousRecord.getId() == 0) {
			curriculum.getMiscellaneousRecords().add(result);
			this.curriculumService.save(curriculum);
		}

		return result;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		final Ranger ranger = this.rangerService.findByPrincipal();
		final Curriculum curriculum = ranger.getCurriculum();

		curriculum.getMiscellaneousRecords().remove(miscellaneousRecord);
		this.curriculumService.save(curriculum);

		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

}
