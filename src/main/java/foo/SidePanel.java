package foo;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

public class SidePanel extends CustomComponent {
	
	private final UI parentUI;

	public SidePanel(UI parent) {
		this.parentUI = parent;
		final VerticalLayout vv = new VerticalLayout();
		vv.setStyleName(Reindeer.LAYOUT_WHITE);

		Button login = new Button("Login");
		login.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				Window login = new LoginWindow();
				login.center();
				parentUI.addWindow(login);
			}
		});
		Button register = new Button("Register");
		Button createNewEvent = new Button("Create new event!");

		vv.addComponent(register);
		vv.addComponent(login);
		vv.addComponent(createNewEvent);

		login.setWidth("130");
		register.setWidth("130");

		setCompositionRoot(vv);
		setSizeFull();
	}
}