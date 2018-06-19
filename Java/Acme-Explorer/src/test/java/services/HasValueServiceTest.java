
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.HasValue;
import domain.Tag;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class HasValueServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private HasValueService	hasValueService;

	@Autowired
	private TagService		tagService;

	@Autowired
	private TripService		tripService;


	// Tests ----------------------------------------------
	@Test
	public void testCreate() {

		final HasValue hv;

		hv = this.hasValueService.create();

		Assert.isNull(hv.getValue());
		Assert.isNull(hv.getTag());
		Assert.isNull(hv.getTrip());
	}

	@Test
	public void testSave() {

		HasValue hv;
		HasValue savedhv;
		Trip trip;
		final Tag tag;

		hv = this.hasValueService.create();

		final List<Trip> trips = new ArrayList<Trip>(this.tripService.findAll());
		trip = trips.get(0);

		final List<Tag> tags = new ArrayList<Tag>(this.tagService.findAll());
		tag = tags.get(0);

		hv.setTrip(trip);
		hv.setTag(tag);
		hv.setValue("ValorDelHas");

		savedhv = this.hasValueService.save(hv);

		Assert.isTrue(this.hasValueService.findAll().contains(savedhv));
	}

	@Test
	public void testDelete() {

		HasValue hv;
		HasValue savedhv;
		Trip trip;
		Tag tag;

		hv = this.hasValueService.create();

		final List<Trip> trips = new ArrayList<Trip>(this.tripService.findAll());
		trip = trips.get(0);

		final List<Tag> tags = new ArrayList<Tag>(this.tagService.findAll());
		tag = tags.get(0);

		hv.setTrip(trip);
		hv.setTag(tag);
		hv.setValue("ValorDelHas");

		savedhv = this.hasValueService.save(hv);

		this.hasValueService.delete(savedhv);

		Assert.isTrue(!this.hasValueService.findAll().contains(savedhv));
	}

	@Test
	public void testFindHasValueByTag() {

		Collection<HasValue> res = null;

		final List<Tag> tags = new ArrayList<Tag>(this.tagService.findAll());

		res = this.hasValueService.findHasValuesByTag(tags.get(0).getId());

		Assert.notNull(res);
	}
}
