package org.eclipse.e4.demo.mailapp.mailservice.domain;

import java.util.Date;

public interface IMail {
	public String getFrom();

	public String getTo();

	public String getSubject();

	public String getBody();

	public void setFrom(String string);

	public void setSubject(String string);

	public void setBody(String string);

	public Date getDate();

	public void setDate(Date date);

	public void setTo(String to);
}
