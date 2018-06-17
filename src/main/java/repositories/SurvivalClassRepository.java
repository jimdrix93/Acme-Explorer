package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SurvivalClass;

@Repository
public interface SurvivalClassRepository extends
		JpaRepository<SurvivalClass, Integer> {

	// 43.1 Manage the survival classes that are associated with his or her
	// trips
	@Query("select t.survivalClasses from Trip t where t.id=?1")
	Collection<SurvivalClass> findByTrips(int tripId);
	// 44.1
	@Query("select s from SurvivalClass s join s.trip.applications a where a.trip.id=?1 and a.applicant.id=?2 and a.moment=?3")
	Collection<SurvivalClass> findByTripAndApplicant(int tripId,int applicantId, Date moment);
}
