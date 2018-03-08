
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.AnnouncementRepository;
import domain.Announcement;

@Transactional
@Component
public class StringToAnnouncementConverter implements Converter<String, Announcement> {

	@Autowired
	AnnouncementRepository	announcementRepository;


	@Override
	public Announcement convert(final String text) {
		Announcement result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.announcementRepository.findOne(id);
			}
		} catch (Throwable error) {
			throw new IllegalArgumentException(error);
		}

		return result;
	}

}
