/**
 *
 */

package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select a from Administrator a where a.userAccount.id = ?1")
	Administrator findByUserAccountId(int id);

	@Query("select min(t.applications.size), max(t.applications.size), avg(t.applications.size), "
			+ "sqrt((sum(t.applications.size*t.applications.size)/ "
			+ "count(t.applications.size)) - (avg(t.applications.size)*avg(t.applications.size))) from Trip t")
	Object statisticsApplicationsTrips();

	@Query("select min(m.trips.size), max(m.trips.size), avg(m.trips.size), "
			+ "sqrt((sum(m.trips.size * m.trips.size) / " + "count(m.trips.size))-avg(m.trips.size)*avg(m.trips.size)) "
			+ "from Manager m")
	Object statisticsManagerTrips();

	@Query("select min(t.price), max(t.price), avg(t.price),"
			+ "sqrt((sum(t.price*t.price)/count(t.price)) - (avg(t.price) * avg(t.price)))" + "from Trip t")
	Object statisticsPriceTrips();

}
