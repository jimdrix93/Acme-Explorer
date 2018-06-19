/**
 * 
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer> {

	//33.2
	@Query("select a.audits from Auditor a where a.id = ?1")
	Collection<Audit> findAuditByAuditor(Integer AuditorID);

	@Query("select a from Audit a join a.trip t where a.finalMode=true and t.id = ?1")
	Collection<Audit> findAuditsByTrip(Integer TripID);

	//35.4
	@Query("select min(t.audits.size), max(t.audits.size), avg(t.audits.size), sqrt((sum(t.audits.size*t.audits.size)/count(t.audits.size)) - (avg(t.audits.size) * avg(t.audits.size))) from Trip t")
	Object findMinMaxAverStandardAuditsPerTrip();
}
