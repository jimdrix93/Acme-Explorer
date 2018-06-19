/**
 * 
 */
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HasValue;

@Repository
public interface HasValueRepository extends
		JpaRepository<HasValue, Integer> {

	// 14.3. Manage the catalogue of tags. Note that a tag may be modified as long 
	// as its not been used to tag any trip; they can be deleted at any time.	
	@Query("select h from HasValue h where h.tag.id = ?1")
	Collection<HasValue> findHasValueByTag(Integer tagId);
}
