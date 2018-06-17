
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private CategoryService			categoryService;
	@Autowired
	private AdministratorService	administratorService;


	//Tests
	@Test
	public void testCreate() {
		super.authenticate("admin");
		final Category category = this.categoryService.create();
		Assert.notNull(category.getChilds());
		Assert.isNull(category.getName());
		Assert.notNull(category.getTrips());

		super.unauthenticate();
	}

	@Test
	public void testSave() {
		super.authenticate("admin");
		final Category category, saved;
		Collection<Category> categories;

		category = this.categoryService.create();
		final Category parent = this.categoryService.findOne(26606);

		category.setName("category");
		category.setParent(parent);

		saved = this.categoryService.save(category);
		super.unauthenticate();

		categories = this.categoryService.findAll();
		Assert.isTrue(categories.contains(saved), "category isn't in database");
		Assert.isTrue(saved.getParent().getChilds().contains(saved), "category isn't a child");

	}

	@Test
	public void testDelete() {
		super.authenticate("admin");
		final Category category, saved;
		Collection<Category> categories;

		category = this.categoryService.create();
		final Category parent = this.categoryService.findOne(26616);

		category.setName("category");
		category.setParent(parent);

		saved = this.categoryService.save(category);
		this.categoryService.delete(saved);
		super.unauthenticate();

		categories = this.categoryService.findAll();
		Assert.isTrue(!categories.contains(saved), "category isn't in database");
		Assert.isTrue(!saved.getParent().getChilds().contains(saved), "category isn't a child");

	}

}
