
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	@Query("select a from Manager a where a.userAccount.id=?1")
	Manager findByUserAccountId(int userAccountId);

	@Query("select m from Manager m where m.benefits.size > " + "(select avg (m1.benefits.size) from Manager m1)")
	Collection<Manager> managersWithMoreBenefits();

	@Query("select m from Manager m join m.benefits b where b.flag=1")
	Collection<Manager> managersWithMoreBenefitsCancelled();

}
