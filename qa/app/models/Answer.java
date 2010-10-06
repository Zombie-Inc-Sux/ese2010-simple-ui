package models;

import java.util.*;

import javax.persistence.*;

import controllers.Secure;
 
import play.db.jpa.*;
import play.mvc.With;
 
@Entity
@With(Secure.class)
public class Answer extends Model {
 
    public String author;
    public Date postedAt;
     
    @Lob
    public String content;
    
    @ManyToOne
    public Question question;
    
    @ManyToMany
	public Set<User> upvotes;
    
    @ManyToMany
	public Set<User> downvotes;
    
    public Answer(Question question, String email, String content) {
        this.question = question;
        this.author = email;
        this.content = content;
        this.postedAt = new Date();
    }
 
}
