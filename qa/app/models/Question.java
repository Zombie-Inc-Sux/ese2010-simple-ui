package models;

import java.util.*;

import javax.persistence.*;

import controllers.Secure;
 
import play.db.jpa.*;
import play.mvc.With;
 
@Entity
@With(Secure.class)
public class Question extends Model {
 
    public String title;
    public Date postedAt;
    
    @Lob
    public String content;
    
    @ManyToOne
    public User author;
    
    @OneToMany(mappedBy="question", cascade=CascadeType.ALL)
    public List<Answer> answers;
    
    @ManyToMany
	public Set<User> upvotes;
    
    @ManyToMany
	public Set<User> downvotes;

    public Question(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
        this.answers = new ArrayList<Answer>();
        this.upvotes = new HashSet<User>();
        this.downvotes = new HashSet<User>();
    }
    
    public Question addAnswer(User author, String content) {
        Answer newAnswer = new Answer(this, author.email, content).save();
        this.answers.add(newAnswer);
        this.save();
        return this;
    }
    
    public Question previous() {
        return Question.find("postedAt < ? order by postedAt desc", postedAt).first();
    }
     
    public Question next() {
        return Question.find("postedAt > ? order by postedAt asc", postedAt).first();
    }

	public Question addVote(Vote vote,User user) {
		user.save();
		if (vote==Vote.UP){
			this.downvotes.remove(user);
			this.upvotes.add(user);
		}
		else {
			this.upvotes.remove(user);
			this.downvotes.add(user);
		}
		this.save();
		return this;
	}
	
	public int rating(){
		int rating = this.upvotes.size()-this.downvotes.size();
		return rating;
	}
}
