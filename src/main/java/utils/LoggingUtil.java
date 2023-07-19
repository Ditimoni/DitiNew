package utils;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import io.restassured.filter.Filter;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class LoggingUtil {

	/**
	 * Author: Tejaswini
	 * Date: 26/5/22(Modified)
	 */
	public static List<Filter> getLoggingFilters(PrintStream log) {
		List<Filter> restAssuredFilters = new ArrayList<>();
			restAssuredFilters.add(new ErrorLoggingFilter(log));
			restAssuredFilters.add(new RequestLoggingFilter(log));
			restAssuredFilters.add(new ResponseLoggingFilter(log));
		return restAssuredFilters;
	}
}

