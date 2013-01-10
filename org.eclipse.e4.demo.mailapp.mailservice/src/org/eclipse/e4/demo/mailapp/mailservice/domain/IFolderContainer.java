package org.eclipse.e4.demo.mailapp.mailservice.domain;

import java.util.List;

import org.eclipse.e4.demo.mailapp.mailservice.IMailSession;

public interface IFolderContainer {
	public List<IFolder> getFolders();	
	public IMailSession getSession();
}
