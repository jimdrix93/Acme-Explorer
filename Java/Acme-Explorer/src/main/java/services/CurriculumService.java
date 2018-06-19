
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

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
		personalRecord = new PersonalRecord();
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
		result.setTicker(this.generateTicker());

		return result;
	}

	public String generateTicker() {
		final Collection<Curriculum> curriculums = this.curriculumRepository.findAll();
		final Collection<String> tickers = new LinkedList<>();
		for (final Curriculum s : curriculums)
			tickers.add(s.getTicker());

		final Date date = new Date(System.currentTimeMillis() - 1);
		final SimpleDateFormat dt = new SimpleDateFormat("yyMMdd");
		final Random r = new Random();
		String randomLetter = "";
		String ticker = "";
		while (tickers.contains(ticker) || ticker == "") {
			for (int i = 0; i < 4; i++)
				randomLetter += String.valueOf((char) (r.nextInt(26) + 'A'));

			ticker = dt.format(date).toString() + "-" + randomLetter;
		}

		return ticker;
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

		Curriculum result;

		if (curriculum.getId() == 0) {
			//Assert.isNull(ranger.getCurriculum(), "Ranger has a curriculum");
			result = this.curriculumRepository.save(curriculum);
			ranger.setCurriculum(result);
			this.rangerService.save(ranger);
		} else {
			Assert.isTrue(ranger.getCurriculum().equals(curriculum), "Curriculum is true");
			result = this.curriculumRepository.save(curriculum);
		}

		return result;
	}
	public void delete(final Curriculum curriculum) {
		final Ranger ranger = this.rangerService.findByPrincipal();
		final Collection<EducationRecord> educationRecords = curriculum.getEducationRecords();

		if (!(curriculum.getPersonalRecord() == null)) {
			final PersonalRecord personalRecord = curriculum.getPersonalRecord();
			curriculum.setPersonalRecord(null);
			this.personalRecordService.delete(personalRecord);
		}
		if (!(curriculum.getEducationRecords().isEmpty()))
			for (final EducationRecord e : educationRecords)
				this.educationRecordService.delete(e);
		if (!(curriculum.getEndorserRecords().isEmpty()))
			for (final EndorserRecord end : curriculum.getEndorserRecords())
				this.endorserRecordService.delete(end);
		if (!(curriculum.getMiscellaneousRecords().isEmpty()))
			for (final MiscellaneousRecord mis : curriculum.getMiscellaneousRecords())
				this.miscellaneousRecordService.delete(mis);
		if (!(curriculum.getProfessionalRecords().isEmpty()))
			for (final ProfessionalRecord pro : curriculum.getProfessionalRecords())
				this.profesionalRecordService.delete(pro);

		ranger.setCurriculum(null);
		this.rangerService.save(ranger);
		this.curriculumRepository.delete(curriculum);
	}
	//Other methods ----------------------------------------------------
}
