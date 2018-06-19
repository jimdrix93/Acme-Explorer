
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.ProfessionalRecord;
import domain.Ranger;
import repositories.ProfessionalRecordRepository;

@Service
@Transactional
public class ProfessionalRecordService {

	//Managed repository ------------------------------------------------
	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	//Constructor -------------------------------------------------------
	public ProfessionalRecordService() {
		super();
	}

	//Methods CRUD ------------------------------------------------------
	public ProfessionalRecord create() {
		final ProfessionalRecord result = new ProfessionalRecord();

		return result;
	}

	public ProfessionalRecord findOne(final Integer professionalRecordID) {
		ProfessionalRecord result;
		result = this.professionalRecordRepository.findOne(professionalRecordID);
		return result;
	}

	public Collection<ProfessionalRecord> findAll() {
		Collection<ProfessionalRecord> result;
		result = this.professionalRecordRepository.findAll();
		return result;
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		ProfessionalRecord result;
		Assert.notNull(professionalRecord);

		result = this.professionalRecordRepository.save(professionalRecord);

		return result;
	}
	

	public void delete(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);

		this.professionalRecordRepository.delete(professionalRecord);
	}
}
