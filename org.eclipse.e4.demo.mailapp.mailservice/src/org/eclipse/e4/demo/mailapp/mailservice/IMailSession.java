package org.eclipse.e4.demo.mailapp.mailservice;

import java.util.List;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.e4.demo.mailapp.mailservice.domain.IFolder;
import org.eclipse.e4.demo.mailapp.mailservice.domain.IMail;

public interface IMailSession {
	public interface ISessionListener {
		public void mailAdded(IFolder folder, IMail mail);
	}

	public IObservableList getAccounts();
	public List<IMail> getMails(IFolder folder, int startIndex, int amount);
	public void addListener(ISessionListener listener);
	public void removeListener(ISessionListener listener);
}
