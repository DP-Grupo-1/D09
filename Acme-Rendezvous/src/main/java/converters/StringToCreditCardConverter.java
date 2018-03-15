package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.CreditCardRepository;
import domain.CreditCard;

	@Transactional
	@Component
	public class StringToCreditCardConverter implements Converter<String, CreditCard>{
		@Autowired
		CreditCardRepository creditCardRepository;

		@Override
		public CreditCard convert(final String text) {
			CreditCard result;
			int id;

			try {
				if (StringUtils.isEmpty(text))
					result = null;
				else {
					id = Integer.valueOf(text);
					result = this.creditCardRepository.findOne(id);
				}
			} catch (Throwable error) {
				throw new IllegalArgumentException(error);
			}

			return result;
		}
}
