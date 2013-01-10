package org.eclipse.e4.demo.mailapp.mailservice.domain.impl;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.e4.demo.mailapp.mailservice.IMailSession;
import org.eclipse.e4.demo.mailapp.mailservice.domain.IFolder;
import org.eclipse.e4.demo.mailapp.mailservice.domain.IFolderContainer;

public class FolderImpl extends BaseBean implements IFolder {

	private IFolderContainer container;
	private String name;
	private int mailCount;
	private List<IFolder> folders = new ArrayList<IFolder>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		firePropertyChange(new PropertyChangeEvent(this, "name", this.name,
				this.name = name));
	}

	public List<IFolder> getFolders() {
		return Collections.unmodifiableList(folders);
	}

	public IMailSession getSession() {
		return container.getSession();
	}

	public void addFolder(IFolder folder) {
		folders.add(folder);
		firePropertyChange(new IndexedPropertyChangeEvent(this, "folders",
				null, folder, folders.size() - 1));
		((FolderImpl) folder).setContainer(this);
	}

	public IFolderContainer getContainer() {
		return container;
	}

	void setContainer(IFolderContainer container) {
		firePropertyChange(new PropertyChangeEvent(this, "container",
				this.container, this.container = container));
	}

	public int getMailCount() {
		return mailCount;
	}

	public void setMailCount(int mailCount) {
		firePropertyChange(new PropertyChangeEvent(this, "mailCount",
				this.mailCount, this.mailCount = mailCount));
	}

}
