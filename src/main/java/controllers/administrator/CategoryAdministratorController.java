
package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	@Autowired
	CategoryService	categoryService;


	// Constructors -----------------------------------------------------------

	public CategoryAdministratorController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Category category = this.categoryService.create();
		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("category/administrator/create");
		result.addObject("categories", categories);
		result.addObject("category", category);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;
		Integer del = 0;
		category = this.categoryService.findOne(categoryId);
		final Collection<Category> categories = this.categoryService.findAll();
		if (category.getChilds().isEmpty())
			del = 1;
		result = new ModelAndView("category/administrator/edit");
		result.addObject("categories", categories);
		result.addObject("category", category);
		result.addObject("del", del);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Category category, final BindingResult binding) {
		ModelAndView result;
		Assert.notNull(category);

		if (binding.hasErrors())
			result = this.createEditModelAndView(category);
		else
			try {
				if (!(category.getParent() == null)) {
					this.categoryService.save(category);
					result = new ModelAndView("redirect:../list.do");
				} else {
					final Category parent = this.categoryService.findOne(category.getId()).getParent();
					category.setParent(parent);
					this.categoryService.save(category);
					result = new ModelAndView("redirect:../list.do");
				}
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(category, "category.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Category category, final BindingResult binding) {
		ModelAndView result;
		Assert.notNull(category);

		if (binding.hasErrors())
			result = this.createEditModelAndView(category);
		else
			try {
				this.categoryService.delete(category);
				result = new ModelAndView("redirect:../list.do");
			} catch (final Throwable ooops) {
				result = this.createEditModelAndView(category, "category.commit.error");
			}
		return result;
	}

	//Ancillary Methods
	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category, final String messageCode) {
		ModelAndView result;
		Integer del = 0;
		result = new ModelAndView("category/administrator/edit");
		final Collection<Category> categories = this.categoryService.findAll();
		if (category.getChilds().isEmpty())
			del = 1;
		result.addObject("category", category);
		result.addObject("requestUri", "category/administrator/list.do");
		result.addObject("message", messageCode);
		result.addObject("categories", categories);
		result.addObject("del", del);

		return result;
	}

}
