
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

	//select avg(r.announcements.size),
	//sqrt(sum(r.announcements.size * r.announcements.size) / count(r.announcements.size) -
	//(avg(r.announcements.size) * avg(r.announcements.size))) from Rendezvous r;

	//The average of announcements per rendezvous
	@Query("select avg(r.announcements.size) from Rendezvous r")
	Double avgOfAnnouncementsPerRendezvous();

	//The standard deviation of announcements per rendezvous
	@Query("select sqrt(sum(r.announcements.size * r.announcements.size) / count(r.announcements.size) - (avg(r.announcements.size) * avg(r.announcements.size))) from Rendezvous r")
	Double stddAnnouncementsPerRendezvous();

	//The rendezvouses that are linked to a number of rendezvouses that is greater than the average plus 10%
}
