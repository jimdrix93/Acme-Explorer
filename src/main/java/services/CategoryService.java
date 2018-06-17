
package services;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Administrator;
import domain.Category;
import domain.Trip;

// 14.4. Manage the taxonomies of categories. Note that categories evolve
// independently from trips, which means that they can be created, modified,
// or deleted independently from whether they are referenced from a trip or not.

@Service
@Transactional
public class CategoryService {

	// Managed repositories-----------------------------------------------------
	@Autowired
	private CategoryRepository		categoryRepository;

	// Supporting services -----------------------------------------------------
	@Autowired
	private AdministratorService	adminService;


	// Constructor -------------------------------------------------------------

	public CategoryService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public Category create() {
		final Administrator admin = this.adminService.findByPrincipal();
		Assert.notNull(admin);

		final Category result = new Category();
		result.setChilds(new LinkedList<Category>());
		result.setTrips(new LinkedList<Trip>());
		return result;
	}

	public Category findOne(final int categoryId) {
		Category result;

		result = this.categoryRepository.findOne(categoryId);
		return result;
	}

	public Collection<Category> findAll() {
		Collection<Category> Categories;
		Categories = this.categoryRepository.findAll();
		return Categories;
	}

	public Category save(final Category category) {

		if (category.getId() == 0) {
			final Administrator admin = this.adminService.findByPrincipal();
			Assert.notNull(admin);
			final Category testpar = category.getParent();
			final Collection<Category> testpars = testpar.getChilds();
			for (final Category c : testpars)
				Assert.isTrue(!category.getName().equals(c.getName()));
		}

		Category saved, parent;
		saved = this.categoryRepository.save(category);
		parent = category.getParent();
		if (!category.getParent().getChilds().contains(saved)) {
			category.getParent().getChilds().add(saved);
			this.categoryRepository.save(parent);
		}

		return saved;
	}

	public void delete(final Category category) {
		final Administrator admin = this.adminService.findByPrincipal();
		Assert.notNull(admin);

		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);

		final Category parent = category.getParent();

		if (parent != null) {
			parent.getChilds().remove(category);
			this.categoryRepository.save(parent);
		}
		this.categoryRepository.delete(category);
	}

	public Collection<Category> findCategories(final int categoryId) {
		final Category category = this.findOne(categoryId);
		return category.getChilds();
	}

	public Collection<Category> findMainCategory() {
		final Collection<Category> categories = this.findAll();
		final Collection<Category> result = new LinkedList<>();
		for (final Category c : categories)
			if (c.getParent() == null) {
				result.addAll(c.getChilds());
				break;
			}
		return result;
	}

	// Other business methods -------------------------------------------------

}
