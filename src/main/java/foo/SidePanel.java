package foo;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class SidePanel extends CustomComponent {

	private final UI parentUI;
	private final Window loginWindow;

	public SidePanel(UI parent) {
		this.parentUI = parent;
		final VerticalLayout vv = new VerticalLayout();
		vv.setStyleName(Reindeer.LAYOUT_WHITE);

		loginWindow = new LoginWindow(parentUI);

		Button btnLogout = new Button("Logout");
		btnLogout.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
					logout();
					parentUI.addWindow(loginWindow);
			}

		});
		Button btnRefreshData = new Button("Refresh", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				((MyVaadinApplication) parentUI).updateTables();	
			}
		});
		Button btnCreateNewEvent = new Button("Create new event!",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						if(isLoggedIn()) {
							parentUI.addWindow(new CreateNewEventWindow(parentUI));
						}
						else {
							Notification.show("Login first to create your events!");
						}
					}
				});

		vv.addComponent(btnLogout);
		vv.addComponent(btnRefreshData);
		vv.addComponent(btnCreateNewEvent);

		btnLogout.setWidth("130");
		btnRefreshData.setWidth("130");

		setCompositionRoot(vv);
		setSizeFull();
	}

	private boolean isLoggedIn() {
		return VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user") != null;
	}
	

	private void logout() {
		VaadinService.getCurrentRequest().getWrappedSession().setAttribute("user", null);
		
	}
}
