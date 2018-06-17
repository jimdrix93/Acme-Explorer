/**
 * 
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Stage;

@Repository
public interface StageRepository extends JpaRepository<Stage, Integer> {

	@Query("select s from Stage s where s.used=false")
	Collection<Stage> findStageWithoutTrip();
}
