package forms;

import java.util.Collection;
import java.util.List;

import javax.persistence.ElementCollection;

import org.hibernate.validator.constraints.NotEmpty;

import domain.Answer;
import domain.Question;

public class AnswerQuestions {

	public AnswerQuestions() {
		super();
	}


	private Collection<Question>	questions;
	private List<Answer>	answers;
	
	@ElementCollection
	public Collection<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(Collection<Question> questions) {
		this.questions = questions;
	}
	@ElementCollection
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

}
