package foo;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;

public class SidePanel extends CustomComponent {

	public SidePanel() {
		VerticalLayout vv = new VerticalLayout();
		vv.setStyleName(Reindeer.LAYOUT_WHITE);

		Label temporary = new Label("Side Panel Here");

		Button login = new Button("Login");
		login.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
			}
		});
		Button register = new Button("Register");
		Button createNewEvent = new Button("Create new event!");

		vv.addComponent(temporary);
		vv.addComponent(register);
		vv.addComponent(login);
		vv.addComponent(createNewEvent);

		login.setWidth("130");
		register.setWidth("130");
		
		setCompositionRoot(vv);
		setSizeFull();
	}
}
