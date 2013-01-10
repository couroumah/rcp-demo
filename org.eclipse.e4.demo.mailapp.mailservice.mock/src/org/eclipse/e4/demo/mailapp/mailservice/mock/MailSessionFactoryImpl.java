package org.eclipse.e4.demo.mailapp.mailservice.mock;

import org.eclipse.e4.demo.mailapp.mailservice.IMailSession;
import org.eclipse.e4.demo.mailapp.mailservice.IMailSessionFactory;

public class MailSessionFactoryImpl implements IMailSessionFactory {
	public IMailSession openSession(String host, String username,
			String password) {
		if ("john".equals(username) && "doe".equals(password)) {
			return new MailSessionImpl();
		}
		return null;
	}
}
