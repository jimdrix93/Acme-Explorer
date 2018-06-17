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
import services.MyMessageService;
import domain.Actor;
import domain.Folder;
import domain.FolderType;
import domain.MyMessage;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	@Autowired
	MyMessageService	messageService;
	@Autowired
	ActorService		actorService;
	@Autowired
	FolderService		folderService;


	// Constructors -----------------------------------------------------------

	public MessageController() {
		super();
	}

	// Create -----------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int actorId) {
		final ModelAndView result;

		final MyMessage message = this.messageService.create(actorId);

		result = new ModelAndView("message/create");
		result.addObject("myMessage", message);

		return result;
	}

	// Edit -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int messageId) {
		final ModelAndView result;

		final MyMessage message;
		message = this.messageService.findOne(messageId);
		Assert.notNull(message);
		result = this.createEditModelAndView(message);

		return result;
	}

	// SaveCreate -----------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MyMessage myMessage, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(myMessage);
		else
			try {
				this.messageService.save(myMessage);
				result = new ModelAndView("redirect:../welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(myMessage, "message.commit.error");
			}
		return result;
	}

	// DeleteEdit -----------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MyMessage myMessage, final BindingResult binding) {
		ModelAndView result;

		try {
			final Actor actor = this.actorService.findByPrincipal();
			Boolean trash = false;
			for (final Folder f : actor.getFolders())
				if (f.getMessages().contains(myMessage))
					if (!f.getFolderType().equals(FolderType.TRASH_BOX)) {
						f.getMessages().remove(myMessage);
						this.folderService.saveEdit(f);
					} else {
						trash = true;
						f.getMessages().remove(myMessage);
					}
			this.messageService.deleteTrash(myMessage, trash);
			result = new ModelAndView("redirect:../welcome/index.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(myMessage, "message.commit.error");
		}
		return result;
	}
	// AuxiliaryMethods -----------------------------------------------------------
	protected ModelAndView createEditModelAndView(final MyMessage myMessage) {
		final ModelAndView result;
		result = this.createEditModelAndView(myMessage, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final MyMessage myMessage, final String errorText) {
		final ModelAndView result;

		result = new ModelAndView("message/create");
		result.addObject("myMessage", myMessage);
		result.addObject("message", errorText);

		return result;
	}

	// List -----------------------------------------------------------

}
