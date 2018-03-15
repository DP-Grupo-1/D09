
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Benefit;


@Transactional
@Component
public class BenefitToStringConverter implements Converter<Benefit, String> {

	@Override
	public String convert(final Benefit benefit) {
		String result;
		if (benefit == null)
			result = null;
		else
			result = String.valueOf(benefit.getId());

		return result;
	}

}
