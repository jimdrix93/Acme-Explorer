
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.MyMessage;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;
import repositories.MyMessageRepository;
import repositories.MiscellaneousRecordRepository;

@Service
@Transactional
public class MiscellaneousRecordService {

	//Managed repository ------------------------------------------------
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

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

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		MiscellaneousRecord result;
		Assert.notNull(miscellaneousRecord);

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);

		return result;
	}
	
	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId() != 0);

		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}
	
}
