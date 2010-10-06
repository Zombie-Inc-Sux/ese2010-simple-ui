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
	    
	    // this is 1:42AM stupid
	    @ManyToMany
		public Set<Question> questionUpVotes;
	    @ManyToMany
		public Set<Question> questionDownVotes;
	    
	    @ManyToMany
		public Set<Answer> answerUpVotes;
	    @ManyToMany
		public Set<Answer> answerDownVotes;
	    
	    public User(String email, String password, String fullname) {
	    	this.email = email;
	        this.password = password;
	        this.fullname = fullname;
	        this.questionUpVotes = new HashSet();
	        this.questionDownVotes = new HashSet();
	        this.answerUpVotes = new HashSet();
	        this.answerDownVotes = new HashSet();
	    }

	    public static User connect(String email, String password) {
	    	return find("byEmailAndPassword", email, password).first();
	    }

    
}
