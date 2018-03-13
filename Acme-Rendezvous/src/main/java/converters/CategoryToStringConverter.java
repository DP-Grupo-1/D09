
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Category;

@Transactional
@Component
public class CategoryToStringConverter implements Converter<Category, String> {

	@Override
	public String convert(final Category category) {
		String result;
		if (category == null)
			result = null;
		else
			result = String.valueOf(category.getId());

		return result;
	}

}
