/**
 *
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;
import domain.Status;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select t.applications from Trip t where t.id = ?1")
	Collection<Application> findAppliByTrip(Integer tripId);

	// 13.2 An actor who is authenticated as an explorer must be able to:
	// List the applications that he or she made, grouped by status.
	@Query("select a from Application a where a.applicant.id = ?1")
	Collection<Application> findAppliByExplorer(Integer explorerId);

	@Query("select a from Application a join a.trip t where t.manager.id = ?1")
	Collection<Application> findAppliByManager(Integer managerId);

	@Query("select sum(case when a.status = ?1 then 1 else 0 end)*100/count(a) from Application a")
	Double findRatioWithStatus(Status status);
}
