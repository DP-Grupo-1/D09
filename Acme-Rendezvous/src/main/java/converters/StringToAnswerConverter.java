
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.AnswerRepository;
import domain.Answer;

@Transactional
@Component
public class StringToAnswerConverter implements Converter<String, Answer> {

	@Autowired
	AnswerRepository	answerRepository;


	@Override
	public Answer convert(final String text) {
		Answer result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.answerRepository.findOne(id);
			}
		} catch (Throwable error) {
			throw new IllegalArgumentException(error);
		}

		return result;
	}

}
