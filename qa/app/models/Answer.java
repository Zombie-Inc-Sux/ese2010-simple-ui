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
	public Map<User,Vote> votes;
    
    public Answer(Question question, String author, String content) {
        this.question = question;
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
    }
 
}
