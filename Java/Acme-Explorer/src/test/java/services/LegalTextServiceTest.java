
package services;

import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.LegalText;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@Transactional
public class LegalTextServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private LegalTextService legalTextService;

	@Test
	public void testCreate() {

		super.authenticate("admin");

		LegalText l;
		l = this.legalTextService.create();

		Assert.isNull(l.getBody());
		Assert.isNull(l.getTitle());
		Assert.isTrue(!l.getFinalMode());
		Assert.notNull(l.getNumberApLaws());

		super.unauthenticate();
	}

	@Test
	public void testSave() {

		super.authenticate("admin");

		LegalText saved;
		final LegalText l = this.legalTextService.create();
		final Date moment = new Date(System.currentTimeMillis() - 1);
		// final Collection<Trip> trips = new LinkedList<Trip>();

		l.setTitle("Titulo del legaltext test");
		l.setBody("Esto va a ser el body del legaltext del test");
		l.setMoment(moment);
		l.setNumberApLaws(7);
		// l.setTrips(trips);
		l.setFinalMode(false);

		saved = this.legalTextService.save(l);

		Assert.notNull(saved);
		Assert.isTrue(this.legalTextService.findAll().contains(saved), "legalText isn't in database");

		super.unauthenticate();
	}

	@Test
	public void testDelete() {

		super.authenticate("admin");

		LegalText saved;
		final LegalText l = this.legalTextService.create();
		final Date moment = new Date(System.currentTimeMillis() - 1);
		// final Collection<Trip> trips = new LinkedList<Trip>();

		l.setTitle("Titulo del legaltext test");
		l.setBody("Esto va a ser el body del legaltext del test");
		l.setMoment(moment);
		l.setNumberApLaws(7);
		// l.setTrips(trips);
		l.setFinalMode(false);

		saved = this.legalTextService.save(l);

		Assert.notNull(saved);
		Assert.isTrue(this.legalTextService.findAll().contains(saved));

		this.legalTextService.delete(saved);

		Assert.isTrue(!this.legalTextService.findAll().contains(saved));

		super.unauthenticate();
	}

	@Test
	public void testTimesReferenced() {
		super.authenticate("admin");
		Collection<Object> result;
		result = this.legalTextService.timesReferenced();
		Assert.notNull(result);
		super.unauthenticate();
	}
}
