
package services;

import java.util.Collection;
import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Folder;
import domain.FolderType;
import domain.MyMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FolderServiceTest extends AbstractTest {

	// Service under test ---------------------------------
	@Autowired
	private FolderService	folderService;
	@Autowired
	private ActorService	actorService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		// TODO: Auto-generated method stub
		Folder folder;

		folder = this.folderService.create();

		Assert.notNull(folder.getChilds());
		Assert.isTrue(folder.getFolderType().equals(FolderType.CUSTOM));
		Assert.notNull(folder.getMessages());
		Assert.isNull(folder.getName());
		//Assert.isNull(folder.getParent());

	}

	@Test
	public void testSave() {
		final Folder folder;
		final Folder saved;
		final Collection<Folder> folders;
		super.authenticate("explorer1");
		folder = this.folderService.create();
		folder.setChilds(new LinkedList<Folder>());
		folder.setMessages(new LinkedList<MyMessage>());
		folder.setName("name1");

		saved = this.folderService.save(folder);
		folders = this.folderService.findAll();
		Assert.isTrue(folders.contains(saved));
		super.unauthenticate();
	}
	@Test
	public void testDelete() {
		Folder folder, folder2;
		final Collection<Folder> folders;

		folder = this.folderService.create();
		folder.setName("name1");
		super.authenticate("explorer1");
		folder2 = this.folderService.save(folder);
		this.folderService.delete(folder2);
		folders = this.folderService.findAll();

		Assert.isTrue(!folders.contains(folder2), "Contains folder");
		Assert.isTrue(!folders.contains(folder), "Contains folder");
		super.unauthenticate();
	}

	@Test
	public void testSystemFolders() {

		final Collection<Folder> folders = this.folderService.systemFolders();

		for (final Folder f : folders) {
			Assert.notNull(f.getName());
			Assert.notNull(f.getFolderType());
			Assert.isTrue(f.getMessages().isEmpty());
			//Assert.isNull(f.getParent());
		}
	}

	@Test
	public void testFindFolderOfActor() {
		Folder fol;
		fol = this.folderService.findFolderOfActor(this.actorService.findOne(26274), "inbox");

		Assert.notNull(fol);
	}
}
