package org.eclipse.e4.demo.mailapp.mailservice.domain;

import java.util.List;

public interface IAccount extends IFolderContainer {
	public String getName();

	public List<IFolder> getFolders();
}
