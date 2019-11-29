package com.sep.zuul.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class NpFilter extends Filter<ILoggingEvent> {

	@Override
	public FilterReply decide(ILoggingEvent event) {
		// TODO Auto-generated method stub
		if (event.getMessage().contains("NP_EVENT")) {
		      return FilterReply.ACCEPT;
		} else {
		      return FilterReply.DENY;
		}
	}

}