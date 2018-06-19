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

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import domain.MyMessage;

@Controller
@RequestMapping("/folder/message")
public class FolderMessageController extends AbstractController {

	@Autowired
	MyMessageService	messageService;
	@Autowired
	ActorService		actorService;
	@Autowired
	FolderService		folderService;


	// Constructors -----------------------------------------------------------

	public FolderMessageController() {
		super();
	}

	// Moving -----------------------------------------------------------

	@RequestMapping(value = "/moving", method = RequestMethod.GET)
	public ModelAndView movingToFolder(@RequestParam final int folderId) {

		ModelAndView result;
		final Folder folder = this.folderService.findOne(folderId);

		result = this.createEditModelAndView(folder);
		result.addObject("folder", folder);

		return result;
	}

	@RequestMapping(value = "/moving", method = RequestMethod.POST, params = "move")
	public ModelAndView saveMoving(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView result;

		Folder oldFolder = new Folder();
		final Collection<Folder> folders = new ArrayList<Folder>();

		for (final MyMessage myMessage : folder.getMessages()) {
			oldFolder = this.folderService.findByMessage(myMessage);
			folders.add(oldFolder);
		}
		if (binding.hasErrors())
			result = this.createEditModelAndView(folder);
		else
			try {
				this.folderService.saveMoving(folder, folders);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folder, "folder.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder) {
		final ModelAndView result;
		result = this.createEditModelAndView(folder, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Folder folder, final String message) {
		final ModelAndView result;

		final Actor actor = this.actorService.findByPrincipal();
		final Collection<MyMessage> messagesDomain = this.messageService.findAllMessagesByActor(actor);
		final Collection<MyMessage> myMessages = folder.getMessages();
		final Collection<MyMessage> messagesDomainCopy = this.messageService.findAllMessagesByActor(actor);

		for (final MyMessage myMessage : messagesDomain)
			if (myMessages.contains(myMessage))
				messagesDomainCopy.remove(myMessage);

		result = new ModelAndView("folder/message/moving");
		result.addObject("folder", folder);
		result.addObject("messages", messagesDomainCopy);
		result.addObject("message", message);

		return result;
	}

	// List -----------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int folderId) {

		ModelAndView result;
		Collection<MyMessage> messages;
		final Actor actor = this.actorService.findByPrincipal();
		final Folder folder = this.folderService.findOne(folderId);

		messages = this.messageService.findAllByFolder(actor, folder);

		result = new ModelAndView("folder/message/list");
		result.addObject("messages", messages);
		result.addObject("requestUri", "folder/message/list.do");

		return result;
	}

}
