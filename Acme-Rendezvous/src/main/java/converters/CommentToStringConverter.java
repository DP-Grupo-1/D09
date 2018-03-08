
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Comment;


@Transactional
@Component
public class CommentToStringConverter implements Converter<Comment, String> {

	@Override
	public String convert(final Comment comment) {
		String result;
		if (comment == null)
			result = null;
		else
			result = String.valueOf(comment.getId());

		return result;
	}

}
