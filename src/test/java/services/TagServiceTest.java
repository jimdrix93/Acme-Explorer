package services;

import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.HasValue;
import domain.Tag;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TagServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private TagService	tagService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		Tag tag;

		super.authenticate("admin");
		tag = this.tagService.create();

		Assert.isNull(tag.getName());

		super.unauthenticate();
	}

	@Test
	public void testSave() throws ParseException {
		final Tag tag;
		final Tag saved;
		final Collection<Tag> tags;
		
		super.authenticate("admin");

		tag = this.tagService.create();

		tag.setName("PRUEBA");
		tag.setHasValues(new LinkedList<HasValue>());

		saved = this.tagService.save(tag);
		tags = this.tagService.findAll();
		Assert.isTrue(tags.contains(saved));
		super.unauthenticate();
	}

	@Test
	public void testDelete() throws ParseException {
		final Tag tag;
		final Tag saved;
		final Collection<Tag> tags;

		super.authenticate("admin");
		
		tag = this.tagService.create();

		tag.setName("PRUEBA");
		tag.setHasValues(new LinkedList<HasValue>());

		saved = this.tagService.save(tag);
		this.tagService.delete(saved);
		tags = this.tagService.findAll();

		Assert.isTrue(!tags.contains(tag));
		Assert.isTrue(!tags.contains(saved));
		
		super.unauthenticate();
	}
}
