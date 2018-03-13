
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import repositories.CategoryRepository;
import domain.Category;

public class StringToCategoryConverter implements Converter<String, Category> {

	@Autowired
	CategoryRepository	categoryRepository;


	@Override
	public Category convert(final String text) {
		Category res;
		int id;
		try {
			if (StringUtils.isEmpty(text))
				res = null;
			else {
				id = Integer.valueOf(text);
				res = this.categoryRepository.findOne(id);
			}
		} catch (final Throwable error) {
			throw new IllegalArgumentException(error);
		}
		return res;
	}

}
