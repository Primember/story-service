package vn.sunbuy.storyapi.config;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;


public class ConvertToDateViaSqlDate implements Converter<LocalDate, Date> {

	@Override
	public Date convert(LocalDate value) {
		return java.sql.Date.valueOf(value);
	}

}
