/*
 * ExamRepository.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http:www.tdg-seville.info/License.html
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rendezvous;

@Repository
public interface RendezvousRepository extends JpaRepository<Rendezvous, Integer> {

	//	Requisito 4.2: Lista de reuniones o quedadas a las que el usuario va a asistir
	//	o ya ha asistido.
	@Query("select u.attendances from User u where u.id = ?1")
	Collection<Rendezvous> findByUserId(int userId);

	@Query("select r from Rendezvous r where r.creator.id=?1")
	Collection<Rendezvous> findByCreatorId(int creatorId);

	//Requisito 6.3 punto 1: La media y la desviación estándar de reuniones creadas por usuario.
	@Query("select count(r)*1.0/(select count(u) from User u) from Rendezvous r")
	Double avgRendezvousPerUser();

	//@Query("select stddev(u.rendezvouses.size) from User u")
	//Double stddevRendezvousPerUser()

	//Requisito 6.3 punto 2: Ratio de usuarios que han creado al menos una reunión.
	@Query("select count(DISTINCT r.creator)*1.0/(select count(u) from User u) from Rendezvous r")
	Double ratioCreators();

	//Ratio de usuario que NO han creado un rendezvous

//	@Query("select count(u)*1.0/(select count(us) from User us) " + "from User u where NOT EXISTS( select r.creator from Rendezvous r " + "where u.id=r.creator.id")
//	Double ratioUsersSinRendezvous();

	//	@Query("select count(u)*1.0/(select count(us) from User us) from User u where NOT EXISTS(select r.creator from Rendezvous r AND u.id=r.creator.id)")
	//	Double ratioUsersSinRendezvous();


	//	Requisito 6.3 punto 3: La media y la desviación estándar de usuarios por reunión.
	@Query("select avg(r.attendants.size) from Rendezvous r")
	Double avgUsersPerRendezvous();

	@Query("select stddev(r.attendants.size) from Rendezvous r")
	Double stddevUsersPerRendezvous();

	//	Requisito 6.3 punto 4: La media y la desviación estándar de reuniones que son RSVPd
	//	por usuario.
	@Query("select avg(u.attendances.size) from User u")
	Double avgRSVPsPerUser();

	@Query("select stddev(u.attendances.size) from User u")
	Double stddevRSVPsPerUser();

	//	Requisito 6.3 punto 5: Top 10 de reuniones en las que más usuarios han RSPVd.
	@Query("select r from Rendezvous r order by r.attendants.size DESC")
	Collection<Rendezvous> top10RendezvousesByRSVPs();

	//	Requisito 17.2 punto 2: Las reuniones cuyo número de anuncios está por encima del 75%
	//	de la media del número de anuncios por reunión.
	@Query("select r from Rendezvous r group by r having r.announcements.size > (0.75*avg(r.announcements.size))")
	Collection<Rendezvous> above75AverageOfAnnouncementsPerRendezvous();

	//	Requisito 17.2 punto 3: Las reuniones que están conectadas a un número de reuniones
	//	que sea (refiriéndose a ese número) la media sumado un 10%.

	@Query("select r from Rendezvous r group by r having r.rendezvouses.size > (1.1*avg(r.rendezvouses.size))")
	Collection<Rendezvous> linkedGreaterAveragePlus10();

	@Query("select r1 from Rendezvous r1 join r1.rendezvouses r2 where r2.id = ?1")
	Collection<Rendezvous> findRendezvousParents(int rendezvousId);

	@Query("select r from Rendezvous r join r.comments c where c.id=?1")
	Rendezvous findByCommentId(int commentId);

	@Query("select r from Rendezvous r join r.announcements a where a.id=?1")
	Rendezvous findByAnnouncementId(int announcementId);

	
	
}
