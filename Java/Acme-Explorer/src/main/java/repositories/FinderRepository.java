/**
 * 
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	//34.1
	@Query("select e.finders from Explorer e where e.id = ?1")
	Collection<Finder> findFindersByExplorer(Integer ExplorerID);
}
