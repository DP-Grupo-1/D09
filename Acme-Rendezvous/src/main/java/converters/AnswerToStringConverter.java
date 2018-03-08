
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Answer;


@Transactional
@Component
public class AnswerToStringConverter implements Converter<Answer, String> {

	@Override
	public String convert(final Answer answer) {
		String result;
		if (answer == null)
			result = null;
		else
			result = String.valueOf(answer.getId());

		return result;
	}

}
