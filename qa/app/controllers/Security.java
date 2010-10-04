package controllers;

import play.mvc.With;
import models.User;
 
@With(Secure.class)
public class Security extends Secure.Security{
    
	static boolean authenticate(String username, String password) {
	    return User.connect(username, password) != null;
	}

    
}
