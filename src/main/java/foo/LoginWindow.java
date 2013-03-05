package foo;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.util.filter.Like;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import foo.domain.User;

@SuppressWarnings("serial")
public class LoginWindow extends Window {
	
	private JPAContainer<User> users;
	private Label lblUsername;
	private Label lblPassword;
	private TextField username;
	private PasswordField password;
	private Button btnLogin;
	private Button btnRegister;

	public LoginWindow() {
		super("Login");
		initDBContainer();
		
		lblUsername = new Label("Username:");
		username = new TextField();
		lblPassword = new Label("Password:");
		password = new PasswordField();
		btnLogin = new Button("Login");
		btnRegister = new Button("Register");
		btnLogin.addClickListener(new LoginClickListener());
		
		VerticalLayout fieldLayout = new VerticalLayout();
		fieldLayout.addComponent(lblUsername);
		fieldLayout.addComponent(username);
		fieldLayout.addComponent(lblPassword);
		fieldLayout.addComponent(password);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);
		buttonLayout.addComponent(btnLogin);
		buttonLayout.addComponent(btnRegister);
		
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.setSpacing(true);
		mainLayout.addComponent(fieldLayout);
		mainLayout.addComponent(buttonLayout);
		
		setHeight("170px");
		setWidth("150px");
		setContent(mainLayout);
		setResizable(false);
	}

	private void initDBContainer() {
		users = JPAContainerFactory.make(User.class, "database");	
	}
	
	private class LoginClickListener implements Button.ClickListener {

		@Override
		public void buttonClick(ClickEvent event) {
			users.removeAllContainerFilters();
			users.addContainerFilter(new Like("name", username.getValue()));
			users.applyFilters();
			if(users.size() != 0) {
				User user = users.getItem(users.firstItemId()).getEntity();
				Notification.show(user.toString());
			}	
		}
		
	}
}
