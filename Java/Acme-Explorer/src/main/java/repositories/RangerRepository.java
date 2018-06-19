/**
 * 
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;
import domain.Ranger;

@Repository
public interface RangerRepository extends JpaRepository<Ranger, Integer> {

	@Query("select r.curriculum from Ranger r where r.id = ?1")
	Curriculum findCurriculum(Integer rangerid);

	// 35.1
	@Query("select r from Ranger r where r.suspicious = ?1")
	Collection<Ranger> findRangerBySuspicious(Boolean suspicious);

	@Query("select r from Ranger r where r.userAccount.id = ?1")
	Ranger findByUserAccountId(int id);

	// 35.4 The ratio of rangers who have registered their curricula.
	@Query("select count(r)*100/(select count(r1) from Ranger r1) from Ranger r where r.curriculum is not null")
	Double getRatioOfRangerHaveCurricula();

	// 35.4 The ratio of rangers whose curriculum been endorsed.
	@Query("select count(r)*100/(select count(r1) from Ranger r1) from Ranger r where r.curriculum is not null")
	Double getRatioOfRangerWithEndorser();

	// 35.4 The ratio of suspicious rangers.
	@Query("select count(r2)*100 / (select count(r1) from Ranger r1) from Ranger r2 where r2.suspicious = true")
	Double getRatioOfRangersSupicious();

	@Query("select min(r.trips.size), max(r.trips.size), avg(r.trips.size), sqrt((sum(r.trips.size*r.trips.size)/count(r.trips.size))-(avg(r.trips.size)*avg(r.trips.size))) from Ranger r")
	double[] getMinMaxAverStandardTripsGuidedPerRanger();
	
	//30.1
	@Query("select t.ranger from Trip t where t.id = ?1")
	Collection<Ranger> findRangerByTrip(Integer TripID);
}
