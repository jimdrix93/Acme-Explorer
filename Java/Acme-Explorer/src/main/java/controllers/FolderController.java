/*
 * CustomerController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

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

import services.ActorService;
import services.FolderService;
import domain.Actor;
import domain.Folder;

@Controller
@RequestMapping("/folder")
public class FolderController extends AbstractController {

	@Autowired
	FolderService	folderService;
	@Autowired
	ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public FolderController() {
		super();
	}

	// Create -----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView result;

		final Folder folder = this.folderService.create();

		result = new ModelAndView("folder/create");
		result.addObject("folder", folder);

		return result;
	}

	//CreateWithParent -----------------------------------------------------------
	@RequestMapping(value = "/createWithParent", method = RequestMethod.GET)
	public ModelAndView createWithParent(@RequestParam final Integer parentId) {
		final ModelAndView result;
		final Folder folder, parent;

		parent = this.folderService.findOne(parentId);
		folder = this.folderService.create();
		final Actor actor = this.actorService.findByPrincipal();
		if (!actor.getFolders().contains(parent))
			this.createMessageModelAndView("folder.commit.ko", "/");

		result = this.createEditModelAndView2(folder, parentId);
		result.addObject("folder", folder);
		result.addObject("parentId", parentId);

		return result;
	}

	//Edit -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int folderId) {
		final ModelAndView result;
		final Actor actor = this.actorService.findByPrincipal();

		final Folder folder;
		folder = this.folderService.findOne(folderId);
		Assert.notNull(folder);
		if (!actor.getFolders().contains(folder))
			return this.createMessageModelAndView("folder.commit.ko", "/");
		result = this.createEditModelAndView(folder);

		return result;
	}

	//SaveCreate -----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(folder);
		else
			try {
				this.folderService.save(folder);
				result = new ModelAndView("redirect:../welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folder, "folder.commit.error");
			}
		return result;
	}

	//SaveEdit -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(folder);
		else
			try {
				this.folderService.saveEdit(folder);
				result = new ModelAndView("redirect:../welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folder, "folder.commit.error");
			}
		return result;
	}

	//SaveCreateWithParent -----------------------------------------------------------
	@RequestMapping(value = "/createWithParent", method = RequestMethod.POST, params = "save")
	public ModelAndView saveWithParent(final int parentId, @Valid final Folder folder, final BindingResult binding) {
		ModelAndView result;
		final Folder parent = this.folderService.findOne(parentId);
		if (binding.hasErrors())
			result = this.createEditModelAndView2(folder, parentId);
		else
			try {
				this.folderService.saveWithParent(folder, parent);
				result = new ModelAndView("redirect:../welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView2(folder, parentId, "folder.commit.error");
			}
		return result;
	}

	//DeleteEdit -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result;
		final Folder folder2 = this.folderService.findOne(folder.getId());
		if (!folder2.getChilds().isEmpty() || !folder2.getMessages().isEmpty())
			return this.createMessageModelAndView("folder.commit.not.delete", "/");

		try {
			this.folderService.delete(folder);
			result = new ModelAndView("redirect:../welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(folder, "folder.commit.error");
		}
		return result;
	}

	//AuxiliaryMethods -----------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Folder folder) {
		final ModelAndView result;
		result = this.createEditModelAndView(folder, null);
		return result;
	}
	protected ModelAndView createEditModelAndView(final Folder folder, final String text) {
		final ModelAndView result;

		result = new ModelAndView("folder/create");
		result.addObject("folder", folder);
		result.addObject("text", text);

		return result;
	}

	protected ModelAndView createEditModelAndView2(final Folder folder, final int parentId) {
		final ModelAndView result;
		result = this.createEditModelAndView2(folder, parentId, null);
		return result;
	}
	protected ModelAndView createEditModelAndView2(final Folder folder, final int parentId, final String text) {
		final ModelAndView result;

		result = new ModelAndView("folder/create");
		result.addObject("folder", folder);
		result.addObject("parentId", parentId);
		result.addObject("text", text);

		return result;
	}

	// List -----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		final Collection<Folder> folders;
		Actor actor;

		actor = this.actorService.findByPrincipal();
		folders = this.folderService.findFolderByActor(actor);

		if (!(actor.getFolders().containsAll(folders)))
			this.createMessageModelAndView("folder.commit.ko", "/");

		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);

		return result;
	}
}
