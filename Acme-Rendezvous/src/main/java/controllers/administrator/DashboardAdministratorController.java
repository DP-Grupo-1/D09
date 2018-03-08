
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import services.AnnouncementService;
import services.CommentService;
import services.QuestionService;
import services.RendezvousService;
import domain.Rendezvous;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController {

	//Services----------------------------------------------------
	@Autowired
	AdministratorService	administratorService;

	@Autowired
	RendezvousService		rendezvousService;

	@Autowired
	AnnouncementService		announcementService;

	@Autowired
	QuestionService			questionService;

	@Autowired
	CommentService			commentService;


	//Listing -----------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;

		//Level C-----------------------------------------------------------------------------------------------------
		//1
		final Double avgRendezvousPerUser = this.rendezvousService.avgRendezvousPerUser();
		Double stddevRendezvousPerUser = this.rendezvousService.stddevRendezvousPerUser();
		//2
		final Double ratioCreators = this.rendezvousService.ratioCreators();
		final Double ratioUsersSinRendezvous = this.rendezvousService.ratioUsersSinRendezvous();

		//3
		final Double avgUsersPerRendezvous = this.rendezvousService.avgUsersPerRendezvous();
		final Double stddevUsersPerRendezvous = this.rendezvousService.stddevUsersPerRendezvous();

		//4
		final Double avgRSVPsPerUser = this.rendezvousService.avgRSVPsPerUser();
		final Double stddevRSVPsPerUser = this.rendezvousService.stddevRSVPsPerUser();

		//5
		final Collection<Rendezvous> top10RendezvousesByRSVPs = this.rendezvousService.top10RendezvousesByRSVPs();

		//Level B
		//1
		final Double avgOfAnnouncementsPerRendezvous = this.announcementService.avgOfAnnouncementsPerRendezvous();
		//2
		final Double stddAnnouncementsPerRendezvous = this.announcementService.stddAnnouncementsPerRendezvous();
		//3
		final Collection<Rendezvous> above75AverageOfAnnouncementsPerRendezvous = this.rendezvousService.above75AverageOfAnnouncementsPerRendezvous();
		res = new ModelAndView("dashboard/list");
		//4
		final Collection<Rendezvous> linkedGreaterAveragePlus10 = this.rendezvousService.LinkedGreaterAveragePlus10();
		//Level A
		//1
		final Double avgQuestionsPerRendezvous = this.questionService.avgQuestionsPerRendezvous();
		final Double stdevQuestionsPerRendezvous = this.questionService.stddevQuestionPerRendezvous();
		//2
		final Double avgAnswersPerQuestions = this.questionService.avgAnswersPerQuestions();
		final Double stdevAnswersPerQuestions = this.questionService.stdevAnswerPerQuestions();
		//3
		final Double avgRepliesPerComment = this.commentService.avgRepliesPerComment();
		final Double stdevRepliesPerComment = this.commentService.stdevRepliesPerComment();

		//1
		res.addObject("avgRendezvousPerUser", avgRendezvousPerUser);
		
		res.addObject("stddevRendezvousPerUser", stddevRendezvousPerUser);
		//2
		res.addObject("ratioCreators", ratioCreators);
		res.addObject("ratioUsersSinRendezvous", ratioUsersSinRendezvous);

		//3.1
		res.addObject("avgUsersPerRendezvous", avgUsersPerRendezvous);
		
		
		//3.2
		res.addObject("stddevUsersPerRendezvous", stddevUsersPerRendezvous);

		//4.1
		res.addObject("avgRSVPsPerUser", avgRSVPsPerUser);

		//4.2
		res.addObject("stddevRSVPsPerUser", stddevRSVPsPerUser);

		//5
		res.addObject("top10RendezvousesByRSVPs", top10RendezvousesByRSVPs);

		//B
		res.addObject("avgOfAnnouncementsPerRendezvous", avgOfAnnouncementsPerRendezvous);
		res.addObject("stddAnnouncementsPerRendezvous", stddAnnouncementsPerRendezvous);
		res.addObject("above75AverageOfAnnouncementsPerRendezvous", above75AverageOfAnnouncementsPerRendezvous);
		res.addObject("linkedGreaterAveragePlus10", linkedGreaterAveragePlus10);
		res.addObject("requestURI", "announcement/list.do");

		//A
		res.addObject("avgRepliesPerComment", avgRepliesPerComment);
		res.addObject("stdevRepliesPerComment", stdevRepliesPerComment);
		res.addObject("avgQuestionsPerRendezvous", avgQuestionsPerRendezvous);
		res.addObject("stdevQuestionsPerRendezvous", stdevQuestionsPerRendezvous);
		res.addObject("avgAnswersPerQuestions", avgAnswersPerQuestions);
		res.addObject("stdevAnswersPerQuestions", stdevAnswersPerQuestions);
		return res;
	}
}
