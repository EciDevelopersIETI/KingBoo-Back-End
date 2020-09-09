package edu.eci.ieti.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "users")
public class User {

		 	//private int userId;
		    private String userName;
		    private String email;
		    private String password;
		    private String telefono;
		    
		    public User(String userName,String email,String password,String telefono) {
		    	//this.userId=userId;
		    	this.userName=userName;
		    	this.email=email;
		    	this.password=password;
		    	this.telefono=telefono;
		    }

			public String getTelefono() {
				return telefono;
			}

			public void setTelefono(String telefono) {
				this.telefono = telefono;
			}

			public String getUserName() {
						return userName;
					}

		    public void setUserName(String userName) {
		        this.userName = userName;
		    }
		    public String getEmail() {
		        return email;
		    }

		    public void setEmail(String email) {
		        this.email = email;
		    }

		    public String getPassword() {
		        return password;
		    }

		    public void setPassword(String password) {
		        this.password = password;
		    }

		    @Override
		    public String toString() {
		        return "User{" +
		                ", userName='" + userName + '\'' +
		                ", email='" + email + '\'' +
		                ", password='" + password + '\'' +
						", telefono='" + telefono + '\'' +
		                '}';
		    }
	

}
