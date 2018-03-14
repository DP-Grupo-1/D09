
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Benefit;
import domain.Rendezvous;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Integer> {

	@Query("select b from Benefit b where ?1 in elements(b.rendezvouses)")
	Collection<Benefit> findAllRequestedByRendezvous(Rendezvous rendezvous);

	@Query("select b from Benefit b order by b.rendezvouses.size desc")
	Collection<Benefit> bestSellings();

}
