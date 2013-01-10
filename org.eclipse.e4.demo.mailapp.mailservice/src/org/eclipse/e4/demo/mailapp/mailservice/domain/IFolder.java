package org.eclipse.e4.demo.mailapp.mailservice.domain;

public interface IFolder extends IFolderContainer {
	public String getName();

	public int getMailCount();

	public IFolderContainer getContainer();
}
