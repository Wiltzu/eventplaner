package foo;

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

		loginWindow = new LoginWindow();
		loginWindow.center();

		Button btnLogin = new Button("Login");
		btnLogin.addClickListener(new Button.ClickListener() {
			boolean isAdded = false;

			@Override
			public void buttonClick(ClickEvent event) {
				if (!isLoggedIn()) {
					if (isAdded) {
						parentUI.removeWindow(loginWindow);
					}
					parentUI.addWindow(loginWindow);
					isAdded = true;
				} else {
					Notification.show(getSession().getAttribute("user")
							.toString());
				}
			}

		});
		Button btnCreateNewEvent = new Button("Create new event!",
				new Button.ClickListener() {

					@Override
					public void buttonClick(ClickEvent event) {
						if(isLoggedIn()) {
							parentUI.addWindow(new CreateNewEventWindow());
						}
						else {
							Notification.show("Login first to create your events!");
						}
					}
				});

		vv.addComponent(btnLogin);
		vv.addComponent(btnCreateNewEvent);

		btnLogin.setWidth("130");

		setCompositionRoot(vv);
		setSizeFull();
	}

	private boolean isLoggedIn() {
		return getSession().getAttribute("user") != null;
	}
}
