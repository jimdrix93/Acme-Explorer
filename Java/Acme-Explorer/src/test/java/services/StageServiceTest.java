
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Stage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class StageServiceTest {

	// Service under test ---------------------------------
	@Autowired
	private StageService	stageService;


	// Tests ----------------------------------------------

	@Test
	public void testCreate() {
		// TODO: Auto-generated method stub
		Stage stage;

		stage = this.stageService.create();

		Assert.isNull(stage.getTitle());
		Assert.isNull(stage.getDescription());
		Assert.isTrue(stage.getPrice() == 0.0);//El precio es un tipo primitivo, no puede ser null, por defecto se inicia a cero

	}

	@Test
	public void testSave() {
		final Stage stage;
		final Stage saved;
		final Collection<Stage> stages;

		stage = this.stageService.create();
		stage.setDescription("description1");
		stage.setPrice(200.0);
		stage.setTitle("title1");

		saved = this.stageService.save(stage);
		stages = this.stageService.findAll();
		Assert.isTrue(stages.contains(saved));
	}
	@Test
	public void testDelete() {
		Stage stage, stage2;
		final Collection<Stage> stages;

		stage = this.stageService.create();
		stage.setDescription("description1");
		stage.setPrice(200.0);
		stage.setTitle("title1");

		stage2 = this.stageService.save(stage);
		this.stageService.delete(stage2);
		stages = this.stageService.findAll();

		Assert.isTrue(!stages.contains(stage2));
		Assert.isTrue(!stages.contains(stage));
	}

}
