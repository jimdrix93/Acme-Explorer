
package services;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import domain.Ranger;

@Service
@Transactional
public class CurriculumService {

	//Managed repositories ------------------------------------------------
	@Autowired
	private CurriculumRepository		curriculumRepository;

	//Supporting services -------------------------------------------------
	@Autowired
	private PersonalRecordService		personalRecordService;
	@Autowired
	private ProfessionalRecordService	profesionalRecordService;
	@Autowired
	private EducationRecordService		educationRecordService;
	@Autowired
	private EndorserRecordService		endorserRecordService;
	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;
	@Autowired
	private RangerService				rangerService;


	//Constructor ----------------------------------------------------------
	public CurriculumService() {
		super();
	}

	//Methods CRUD ---------------------------------------------------------
	public Curriculum create() {

		final Curriculum result;
		//final Ranger ranger;
		PersonalRecord personalRecord;
		Collection<EducationRecord> educationRecords;
		Collection<ProfessionalRecord> professionalRecords;
		Collection<EndorserRecord> endorserRecords;
		Collection<MiscellaneousRecord> miscellaneousRecords;

		//ranger = this.rangerService.findByPrincipal();
		personalRecord = this.personalRecordService.create();
		educationRecords = new LinkedList<EducationRecord>();
		professionalRecords = new LinkedList<ProfessionalRecord>();
		endorserRecords = new LinkedList<EndorserRecord>();
		miscellaneousRecords = new LinkedList<MiscellaneousRecord>();

		result = new Curriculum();

		//result.setRanger(ranger);
		result.setPersonalRecord(personalRecord);
		result.setEducationRecords(educationRecords);
		result.setProfessionalRecords(professionalRecords);
		result.setEndorserRecords(endorserRecords);
		result.setMiscellaneousRecords(miscellaneousRecords);

		return result;
	}
	public Collection<Curriculum> findAll() {

		Collection<Curriculum> result;

		result = this.curriculumRepository.findAll();

		return result;
	}

	public Curriculum findOne(final int curriculumId) {
		Curriculum result;

		result = this.curriculumRepository.findOne(curriculumId);
		Assert.notNull(result);

		return result;
	}

	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum, "Curriculum must be not null");
		//Assert.isTrue(curriculum.getRanger().equals(this.rangerService.findByPrincipal()));
		final Ranger ranger = this.rangerService.findByPrincipal();
		Assert.isTrue(ranger.getCurriculum().equals(curriculum), "Curriculum is true");
		Curriculum result;

		result = this.curriculumRepository.save(curriculum);

		return result;
	}
	public void delete(final Curriculum curriculum) {

		if (!(curriculum.getPersonalRecord() == null))
			this.personalRecordService.delete(curriculum.getPersonalRecord());
		if (!(curriculum.getEducationRecords() == null))
			for (final EducationRecord e : curriculum.getEducationRecords())
				this.educationRecordService.delete(e);
		if (!(curriculum.getEndorserRecords() == null))
			for (final EndorserRecord end : curriculum.getEndorserRecords())
				this.endorserRecordService.delete(end);
		if (!(curriculum.getMiscellaneousRecords() == null))
			for (final MiscellaneousRecord mis : curriculum.getMiscellaneousRecords())
				this.miscellaneousRecordService.delete(mis);
		if (!(curriculum.getProfessionalRecords() == null))
			for (final ProfessionalRecord pro : curriculum.getProfessionalRecords())
				this.profesionalRecordService.delete(pro);

		this.curriculumRepository.delete(curriculum);
	}
	//Other methods ----------------------------------------------------
}
