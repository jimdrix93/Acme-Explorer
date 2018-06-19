
package services;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RangerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.Folder;
import domain.MiscellaneousRecord;
import domain.MyMessage;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import domain.Ranger;
import domain.SocialIdentity;
import domain.Trip;

@Service
@Transactional
public class RangerService {

	// Managed repositories-----------------------------------------------------
	@Autowired
	private RangerRepository		rangerRepository;

	// Services ----------------------------------------------------------------
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private FolderService			folderService;
	@Autowired
	private CurriculumService		curriculumService;
	@Autowired
	private MyMessageService		messageService;
	@Autowired
	private SocialIdentityService	socialIdentityService;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructor -------------------------------------------------------------

	public RangerService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public Ranger create() {

		UserAccount useraccount;
		final Ranger result;
		final Authority authority = new Authority();

		authority.setAuthority("RANGER");
		useraccount = this.userAccountService.create();
		final Collection<Authority> authorities = new LinkedList<Authority>();
		authorities.add(authority);
		useraccount.setAuthorities(authorities);

		result = new Ranger();
		final Collection<Folder> folders = this.folderService.systemFolders();
		final Collection<Trip> trips = new LinkedList<Trip>();
		final Collection<MyMessage> receivedMessages = new LinkedList<MyMessage>();
		final Collection<MyMessage> sendedMessages = new LinkedList<MyMessage>();
		final Collection<SocialIdentity> socialIdentities = new LinkedList<SocialIdentity>();
		final Curriculum curriculum = this.curriculumService.create();// null;

		result.setUserAccount(useraccount);
		result.setAccountActivated(true);
		result.setSuspicious(false);
		result.setFolders(folders);
		result.setTrips(trips);
		result.setReceivedMessages(receivedMessages);
		result.setSendedMessages(sendedMessages);
		result.setSocialIdentities(socialIdentities);
		result.setCurriculum(curriculum);

		return result;
	}

	public Collection<Ranger> findAll() {

		Collection<Ranger> result;

		result = this.rangerRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Ranger findOne(final int rangerId) {
		Ranger result;

		result = this.rangerRepository.findOne(rangerId);
		Assert.notNull(result);

		return result;
	}

	public Ranger save(final Ranger ranger) {

		Assert.notNull(ranger, "ranger must be not null");
		final Ranger saved;
		if (ranger.getId() == 0) {
			Md5PasswordEncoder encoder;
			encoder = new Md5PasswordEncoder();

			ranger.getUserAccount().setPassword(encoder.encodePassword(ranger.getUserAccount().getPassword(), null));
		}

		final String countryCode = this.configurationService.findCountryCode();
		if (!ranger.getPhone().contains(countryCode)) {
			final String phone = countryCode + ranger.getPhone();
			ranger.setPhone(phone);
		}

		saved = this.rangerRepository.saveAndFlush(ranger);
		return saved;
	}

	// Other methods ----------------------------------------------------

	public Ranger findByPrincipal() {
		Ranger result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Ranger findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Ranger result;
		result = this.rangerRepository.findByUserAccountId(userAccount.getId());
		return result;

	}

	public Ranger findByUsername(final String username) {

		UserAccount us;
		us = this.userAccountService.findByUserName(username);
		return this.findByUserAccount(us);
	}

	public Ranger findOneToEdit(final int rangerId) {
		// TODO Auto-generated method stub
		Ranger result, ranger2;
		result = this.rangerRepository.findOne(rangerId);
		ranger2 = this.findByPrincipal();
		Assert.isTrue(ranger2.equals(result), "Ranger is not found in db.");
		return result;
	}

	// 35.4 The ratio of rangers who have registered their curricula.
	public Double getRatioOfRangerHaveCurricula() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		Double ratio;
		ratio = this.rangerRepository.getRatioOfRangerHaveCurricula();
		Assert.isTrue(0.0 <= ratio && ratio <= 100.0, "Ratio of ranger with curricula must be between (0,100)");
		return ratio;
	}

	// 35.4 The ratio of rangers whose curriculum been endorsed.
	public Double getRatioOfRangerWithEndorser() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		Double ratio;
		ratio = this.rangerRepository.getRatioOfRangerWithEndorser();
		Assert.isTrue(0.0 <= ratio && ratio <= 100.0, "Ratio of ranger with endorser must be between (0,100)");
		return ratio;
	}

	// 35.4 The ratio of suspicious rangers.
	public Double getRatioOfRangersSupicious() {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		Double ratio;
		ratio = this.rangerRepository.getRatioOfRangersSupicious();
		Assert.isTrue(0.0 <= ratio && ratio <= 100.0, "Ratio of ranger supicious must be between (0,100)");
		return ratio;
	}

	// 35.1
	public Collection<Ranger> findRangerBySuspicious(final Boolean suspicious) {
		final Administrator admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		final Collection<Ranger> res = this.rangerRepository.findRangerBySuspicious(suspicious);
		return res;
	}

	// 30.1
	public Collection<Ranger> findRangerByTrip(final Trip trip) {
		Collection<Ranger> result;
		result = this.rangerRepository.findRangerByTrip(trip.getId());
		Assert.notNull(result);
		return result;
	}

	public void suspicious(final Ranger ranger) {
		final Collection<String> spam = this.configurationService.findAllSpamWord();
		final LinkedList<String> strings = new LinkedList<String>();

		if (ranger.getCurriculum() != null) {
			final PersonalRecord per = ranger.getCurriculum().getPersonalRecord();
			strings.add(per.getEmail());
			strings.add(per.getFullName());
			strings.add(per.getLinkedInUrl());
			strings.add(per.getPhone());
			strings.add(per.getPhoto());
			for (final String spamWord : spam)
				if (strings.contains(spamWord)) {
					ranger.setSuspicious(true);
					break;
				}
			for (final EducationRecord edu : ranger.getCurriculum().getEducationRecords()) {
				strings.add(edu.getAttachment());
				strings.add(edu.getDiplomaTitle());
				strings.add(edu.getInstitution());
				strings.add(edu.getComments());
				for (final String spamWord : spam)
					if (strings.contains(spamWord)) {
						ranger.setSuspicious(true);
						break;
					}

			}

			for (final EndorserRecord edr : ranger.getCurriculum().getEndorserRecords()) {
				strings.add(edr.getEmail());
				strings.add(edr.getFullName());
				strings.add(edr.getLinkedInUrl());
				strings.add(edr.getPhone());
				strings.add(edr.getComments());
				for (final String spamWord : spam)
					if (strings.contains(spamWord)) {
						ranger.setSuspicious(true);
						break;
					}
			}
			for (final MiscellaneousRecord mr : ranger.getCurriculum().getMiscellaneousRecords()) {
				strings.add(mr.getAttachment());
				strings.add(mr.getTitle());
				strings.add(mr.getComments());
				for (final String spamWord : spam)
					if (strings.contains(spamWord)) {
						ranger.setSuspicious(true);
						break;
					}
			}
			for (final ProfessionalRecord pro : ranger.getCurriculum().getProfessionalRecords()) {
				strings.add(pro.getAttachment());
				strings.add(pro.getCompany());
				strings.add(pro.getRole());
				strings.add(pro.getComments());
				for (final String spamWord : spam)
					if (strings.contains(spamWord)) {
						ranger.setSuspicious(true);
						break;
					}
			}
		}

		final LinkedList<MyMessage> sendedMessages = new LinkedList<MyMessage>(ranger.getSendedMessages());
		if (!sendedMessages.isEmpty())
			for (final MyMessage m : sendedMessages) {
				strings.add(m.getBody());
				strings.add(m.getSubject());
				for (final String spamWord : spam)
					if (strings.contains(spamWord)) {
						ranger.setSuspicious(true);
						break;
					}
			}
	}
}
