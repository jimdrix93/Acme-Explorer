/**
 * 
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	@Query("select m from Manager m where m.userAccount.id = ?1")
	Manager findByUserAccountId(int id);

	//35.1
	@Query("select m from Manager m where m.suspicious = ?1")
	Collection<Manager> findManagerBySuspicious(Boolean suspicious);
	
	// 35.4 The ratio of suspicious managers.
	@Query("select count(m2) * 100 / (select count(m1) from Manager m1) from Manager m2 where m2.suspicious = true")
	Double getRatioOfManagersSupicious();
}
