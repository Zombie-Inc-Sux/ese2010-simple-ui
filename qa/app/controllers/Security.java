package controllers;

import play.mvc.With;
import models.User;
 
@With(Secure.class)
public class Security extends Secure.Security{
    
	static boolean authenticate(String username, String password) {
	    return User.connect(username, password) != null;
	}

	static boolean check(String profile) {
	    if("admin".equals(profile)) {
	        return User.find("byEmail", connected()).<User>first().isAdmin;
	    }
	    return false;
	}
	
	static User currentUser(){
		 return User.find("byEmail", connected()).<User>first();
	}
    
}
