package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	
	@Query("select c from Category c where c.name='CATEGORY'")
	Category findCATEGORY();

	@Query("select c.childrens from Category c where c.id=?1")
	Collection<Category> findSubcategoriesById(int id);

	@Query("select c from Category c where c.parent.id = ?1")
	Collection<Category> findChildren(Integer categoryId);

	@Query("select c from Category c where c.benefit.id = ?1")
	Collection<Category> findByBenefitId(int benefitId);
	
}
