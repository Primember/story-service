package vn.sunbuy.storyapi.config;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class ConvertToLocalDateViaInstant implements Converter<Date, LocalDate> {

	@Override
	public LocalDate convert(Date value) {
		return value.toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
	}

}
