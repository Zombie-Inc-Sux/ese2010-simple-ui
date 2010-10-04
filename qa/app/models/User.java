package models;
 
import java.util.*;

import javax.persistence.*;

import controllers.Secure;
 
import play.db.jpa.*;
import play.mvc.With;
 
@Entity
@With(Secure.class)
public class User extends Model {
  
	    public String email;
	    public String password;
	    public String fullname;
	    public boolean isAdmin;
	    
	    @ManyToMany
		public List<Question> questionVotes;
	    
	    @ManyToMany
		public List<Answer> answerVotes;
	    
	    public User(String email, String password, String fullname) {
	    	this.email = email;
	        this.password = password;
	        this.fullname = fullname;
	        this.questionVotes = new ArrayList();
	        this.answerVotes = new ArrayList();
	    }

	    public static User connect(String email, String password) {
	    	return find("byEmailAndPassword", email, password).first();
	    }

    
}
