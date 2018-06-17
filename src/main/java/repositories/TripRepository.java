/**
 *
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Audit;
import domain.Ranger;
import domain.Trip;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer> {

	@Query("select t from domain.Trip t where (t.title like %?1% or t.ticker like %?1% or t.description like %?1%) and t.publicationDate < CURRENT_DATE")
	Collection<Trip> findTripByKeyword(String word);

	// Requirement 10.4
	@Query("select t from Trip t where t.category.id=?1 and t.publicationDate < CURRENT_DATE")
	Collection<Trip> findTripByCategory(int categoryId);

	@Query("select m.trips from Manager m where m.id = ?1")
	Collection<Trip> findTripsByManager(Integer managerID);

	@Query("select sum(case when t.cancelated = true then 1 else 0 end)*100/count(t) from Trip t")
	Double findRatioOfTripCancelled();

	@Query("select t.ranger from Trip t where t.id = ?1")
	Ranger findRanger(Integer tripid);

	@Query("select t1.ticker as ticker, t1.applications.size as size from Trip t1 where t1.applications.size > (select avg(t2.applications.size) * 1.1 from Trip t2) order by t1.applications.size")
	Collection<Object> findTripsWithMoreApplications();

	@Query("select t.audits from Trip t where t.id = ?1")
	Collection<Audit> findAudits(Integer tripid);

	// 35.4 The ratio of trips with an audit record.
	@Query("select count(t)*100/(select count(t1) from Trip t1) from Trip t where t.audits.size=1")
	Double findByAnAudit();

	// 34.1 ...that is, the trips that meet the search criteria.
	@Query("select f.trips from Finder f where f.id = ?1")
	Collection<Trip> findTripsByFinder(Integer finderID);

	@Query("select t from Trip t where t.publicationDate < CURRENT_DATE")
	Collection<Trip> findPublicatedTrips();

	@Query("select avg(r.trips.size),min(r.trips.size),max(r.trips.size),sqrt((sum(r.trips.size*r.trips.size)/count(r.trips.size)) - (avg(r.trips.size) * avg(r.trips.size))) from Ranger r")
	Object tripsPerRanger();
}
