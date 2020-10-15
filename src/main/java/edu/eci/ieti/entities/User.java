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
		    private Provider provider;
		    private String rol;			
			public User(String userName, String email, String password, String telefono, Provider provider) {
				this.userName = userName;
				this.email = email;
				this.password = password;
				this.telefono = telefono;
				this.provider = provider;
				this.rol = provider != null && provider.getNit() !=0 ? "pro":"cli";
			}
			
			public String getRol() {
				return rol;
			}

			public void setRol(String rol) {
				this.rol = rol;
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

			public Provider getProvider() {
				return provider;
			}

			public void setProvider(Provider provider) {
				this.provider = provider;
			}

			/**
		    @Override
		    public String toString() {
		        return "User{" +
		                ", userName='" + userName + '\'' +
		                ", email='" + email + '\'' +
		                ", password='" + password + '\'' +
						", telefono='" + telefono + '\'' +
		                '}';
		    }
			 */
	

}
