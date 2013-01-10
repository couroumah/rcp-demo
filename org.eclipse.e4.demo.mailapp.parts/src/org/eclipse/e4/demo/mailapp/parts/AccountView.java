package org.eclipse.e4.demo.mailapp.parts;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.property.list.IListProperty;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.Preference;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.demo.mailapp.mailservice.IMailSession;
import org.eclipse.e4.demo.mailapp.mailservice.IMailSession.ISessionListener;
import org.eclipse.e4.demo.mailapp.mailservice.IMailSessionFactory;
import org.eclipse.e4.demo.mailapp.mailservice.domain.IAccount;
import org.eclipse.e4.demo.mailapp.mailservice.domain.IFolder;
import org.eclipse.e4.demo.mailapp.mailservice.domain.IFolderContainer;
import org.eclipse.e4.demo.mailapp.mailservice.domain.IMail;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.databinding.viewers.ObservableListTreeContentProvider;
import org.eclipse.jface.databinding.viewers.TreeStructureAdvisor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.EventConstants;

@SuppressWarnings("restriction")
public class AccountView {
	@Inject
	@Optional
	private ESelectionService selectionService;

	private IMailSessionFactory mailSessionFactory;
	private IMailSession mailSession;
	private TreeViewer viewer;
	private String username = "john";
	private String password = "doe";
	private String host = "tomsondev.bestsolution.com";
	private boolean modified = false;
	private ISessionListener listener;

	@Inject
	@Optional
	private IEventBroker eventBroker;

	@Inject
	public AccountView(Composite parent, IMailSessionFactory mailSessionFactory) {
		this.mailSessionFactory = mailSessionFactory;
		viewer = new TreeViewer(parent, SWT.FULL_SELECTION);
		viewer.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof IAccount) {
					return ((IAccount) element).getName();
				} else if (element instanceof IFolder) {
					return ((IFolder) element).getName();
				}
				return super.getText(element);
			}
		});

		IObservableFactory factory = new IObservableFactory() {
			private IListProperty prop = BeanProperties.list("folders");

			@Override
			public IObservable createObservable(Object target) {
				if (target instanceof IObservableList) {
					return (IObservable) target;
				} else if (target instanceof IFolderContainer) {
					return prop.observe(target);
				}
				return null;
			}
		};

		TreeStructureAdvisor advisor = new TreeStructureAdvisor() {
		};
		viewer.setContentProvider(new ObservableListTreeContentProvider(
				factory, advisor));

		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				if (selectionService != null) {
					selectionService.setSelection(((IStructuredSelection) event
							.getSelection()).getFirstElement());
				}
			}
		});

		listener = new ISessionListener() {

			public void mailAdded(IFolder folder, IMail mail) {
				if (eventBroker != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("NEW_MAIL_TAG_FOLDER", folder);
					map.put("NEW_MAIL_TAG_MAIL", mail);
					eventBroker.post("NEW_MAIL", map);
//					map.put(EventConstants.NEW_MAIL_TAG_FOLDER, folder);
//					map.put(EventConstants.NEW_MAIL_TAG_MAIL, mail);
//					eventBroker.post(EventConstants.NEW_MAIL, map);
				}
			}

		};

	}

	public void setUsername(@Preference("username") String username) {
		this.username = username;
	}

	public void setPassword(@Preference("password") String password) {
		this.password = password;
	}

	public void setHost(@Preference("host") String host) {
		this.host = host;
	}

	@PostConstruct
	public void init() {
		if (username != null && password != null && host != null) {
			if (mailSession != null) {
				mailSession.removeListener(listener);
			}
			mailSession = mailSessionFactory.openSession(host, username,
					password);
			if (mailSession != null) {
				viewer.setInput(mailSession.getAccounts());
				mailSession.addListener(listener);
			} else {
				viewer.setInput(new WritableList());
			}
		}
		modified = false;
	}

	@PreDestroy
	void cleanUp() {
		if (mailSession != null && listener != null) {
			mailSession.removeListener(listener);
		}
	}

	@Focus
	void onFocus(@Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		if (modified) {
			if (MessageDialog
					.openQuestion(
							shell,
							"AccountInfos Modified",
							"The account informations have been modified would you like to reconnect with them?")) {
				init();
				if (mailSession == null) {
					MessageDialog.openWarning(shell, "Connection failed",
							"Opening a connecting to the mail server failed.");
				}
			}
		}
	}
}
