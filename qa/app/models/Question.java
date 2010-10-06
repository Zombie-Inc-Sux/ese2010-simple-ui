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
    
    @OneToMany
    public Map<User,QVote> votes;

    public Question(User author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.postedAt = new Date();
        this.answers = new ArrayList<Answer>();
        this.votes = new HashMap<User,QVote>();
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

	public Question addVote(int vote,User user) {
		this.votes.remove(user);
		QVote v = new QVote(user,this,vote);
		v.save();
		this.votes.put(user,v);
		this.save();
		return this;
	}
	
	public int rating(){
		int rating = 0;
		for (QVote vote : this.votes.values()){
			rating += vote.vote;
		}
		return rating;	
	}
}
