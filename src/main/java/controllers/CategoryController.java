
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import domain.Category;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController {

	@Autowired
	CategoryService	categoryService;


	// Constructors -----------------------------------------------------------
	public CategoryController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Category> categories;

		categories = this.categoryService.findMainCategory();

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestUri", "category/list.do");

		return result;
	}

	@RequestMapping(value = "/listCategory", method = RequestMethod.GET)
	public ModelAndView listCategory(@RequestParam final int categoryId) {
		ModelAndView result;
		Collection<Category> categories;

		categories = this.categoryService.findCategories(categoryId);

		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestUri", "category/list.do");

		return result;
	}

}
