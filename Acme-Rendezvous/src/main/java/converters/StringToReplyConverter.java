
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.ReplyRepository;
import domain.Reply;

@Transactional
@Component
public class StringToReplyConverter implements Converter<String, Reply> {

	@Autowired
	ReplyRepository	replyRepository;


	@Override
	public Reply convert(final String text) {
		Reply result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.replyRepository.findOne(id);
			}
		} catch (Throwable error) {
			throw new IllegalArgumentException(error);
		}

		return result;
	}

}
