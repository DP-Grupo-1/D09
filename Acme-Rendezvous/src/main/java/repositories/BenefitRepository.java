
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Benefit;
import domain.Rendezvous;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Integer> {

	@Query("select b from Benefit b where ?1 member of b.rendezvouses")
	Collection<Benefit> findAllRequestedByRendezvous(Rendezvous rendezvous);

	@Query("select b from Benefit b order by b.rendezvouses.size desc")
	List<Benefit> bestSellings();

	@Query("select b from Benefit b join b.rendezvouses r where r.id=?1")
	Benefit findByRendezvoudId(int rendezvousId);

	@Query("select sum((select count(*) from Rendezvous r where r in elements(u.rendezvouses))*(select count(*) from Rendezvous r where r in elements(u.rendezvouses))) from Benefit u")
	Double sumRendezvousPerService();

	@Query("select count(r)*1.0/(select count(u)*1.0  from Rendezvous u) from Benefit r")
	Double avgServicePerRendezvous();

}
