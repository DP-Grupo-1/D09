
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.QuestionRepository;
import domain.Question;

@Transactional
@Component
public class StringToQuestionConverter implements Converter<String, Question> {

	@Autowired
	QuestionRepository	questionRepository;


	@Override
	public Question convert(final String text) {
		Question result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.questionRepository.findOne(id);
			}
		} catch (Throwable error) {
			throw new IllegalArgumentException(error);
		}

		return result;
	}

}
