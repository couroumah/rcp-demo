package org.eclipse.e4.demo.mailapp.mailservice.domain.impl;

import java.beans.PropertyChangeEvent;
import java.util.Date;

import org.eclipse.e4.demo.mailapp.mailservice.domain.IMail;

public class MailImpl extends BaseBean implements IMail {

	private String from;
	private String subject;
	private String body;
	private Date date;
	private String to;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		firePropertyChange(new PropertyChangeEvent(this, "from", this.from,
				this.from = from));
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		firePropertyChange(new PropertyChangeEvent(this, "to", this.to,
				this.to = to));
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		firePropertyChange(new PropertyChangeEvent(this, "subject",
				this.subject, this.subject = subject));
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		firePropertyChange(new PropertyChangeEvent(this, "body", this.body,
				this.body = body));
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		firePropertyChange(new PropertyChangeEvent(this, "date", this.date,
				this.date = date));
	}

}
