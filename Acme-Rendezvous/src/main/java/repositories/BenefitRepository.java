package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Benefit;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Integer>{

}
