package org.eclipse.e4.demo.mailapp.mailservice;

public interface IMailSessionFactory {
	public IMailSession openSession(String host, String username, String password);
}
