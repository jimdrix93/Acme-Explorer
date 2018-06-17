/**
 *
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LegalText;

@Repository
public interface LegalTextRepository extends JpaRepository<LegalText, Integer> {

	@Query("select t.legalText.title as title, t.legalText.id as id, count(t.legalText.id) as references from Trip t group by t.legalText")
	Collection<Object> timesReferenced();

	@Query("select lt from LegalText lt where lt.finalMode = true")
	Collection<LegalText> findAllValid();
}
