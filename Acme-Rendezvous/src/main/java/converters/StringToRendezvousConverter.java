
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.RendezvousRepository;
import domain.Rendezvous;

@Transactional
@Component
public class StringToRendezvousConverter implements Converter<String, Rendezvous> {

	@Autowired
	RendezvousRepository	rendezvousRepository;


	@Override
	public Rendezvous convert(final String text) {
		Rendezvous result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.rendezvousRepository.findOne(id);
			}
		} catch (Throwable error) {
			throw new IllegalArgumentException(error);
		}

		return result;
	}

}
