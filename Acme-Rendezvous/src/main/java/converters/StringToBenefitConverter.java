
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.BenefitRepository;
import domain.Benefit;

@Transactional
@Component
public class StringToBenefitConverter implements Converter<String, Benefit> {

	@Autowired
	BenefitRepository	benefitRepository;


	@Override
	public Benefit convert(final String text) {
		Benefit result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.benefitRepository.findOne(id);
			}
		} catch (Throwable error) {
			throw new IllegalArgumentException(error);
		}

		return result;
	}

}
