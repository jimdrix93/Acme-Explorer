
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;

@Service
@Transactional
public class UserAccountService {

	// Managed repositories------------------------------------

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Constructor---------------------------------------------

	public UserAccountService() {
		super();
	}

	// Methods-------------------------------------------------

	// Simple CRUDS methods--------------------------------

	public UserAccount create() {

		final UserAccount result = new UserAccount();

		//		final Collection<Authority> authorities = new LinkedList<Authority>();
		//
		//		result.setAuthorities(authorities);

		return result;

	}

	public UserAccount save(final UserAccount userAccount) {

		Assert.notNull(userAccount);
		UserAccount saved;
		
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		userAccount.setPassword(encoder.encodePassword(userAccount.getPassword(),null));

		saved = this.userAccountRepository.save(userAccount);
		return saved;
	}

	// Other business methods -------------------------------------------------

	public UserAccount findByActor(final Actor actor) {
		UserAccount result;
		result = this.userAccountRepository.findByUsername(actor.getUserAccount().getUsername());

		return result;
	}

	public UserAccount findByUserName(final String username) {
		UserAccount result;
		result = this.userAccountRepository.findByUsername(username);

		return result;
	}

}
