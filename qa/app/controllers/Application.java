package controllers;
 
import java.util.*;
 
import play.*;
import play.data.validation.Required;
import play.mvc.*;
 
import models.*;

@With(Secure.class)
public class Application extends Controller {
	
	@Before
	static void addDefaults() {
	    renderArgs.put("blogTitle", Play.configuration.getProperty("SU.title"));
	    renderArgs.put("blogBaseline", Play.configuration.getProperty("SU.baseline"));
	}
 
    public static void index() {
        Question frontQuestion = Question.find("order by postedAt desc").first();
        List<Question> olderQuestions = Question.find(
            "order by postedAt desc"
        ).from(1).fetch(9);
        render(frontQuestion, olderQuestions);
        String user = Security.connected();
        render(user);
    }
    
    public static void show(Long id) {
        Question question = Question.findById(id);
        render(question);
    }
    
    public static void postAnswer(Long postId, @Required String content) {
        Question question = Question.findById(postId);
        User author = Security.currentUser();
        if (validation.hasErrors()) {
            render("Application/show.html", question);
        }
        question.addAnswer(author, content);
        flash.success("Thanks for posting %s", author.fullname);
        show(postId);
    }
    
    public static void rateQuestion(Long postId){
    	String vote = params.get("vote");
    	User user = Security.currentUser();
    	System.out.println("rated: " +vote+" by "+user.email);
    	Question question = Question.findById(postId);
    	question.addVote(vote=="like"?Vote.UP:Vote.DOWN,user);
    	System.out.println(question.downvotes.toString());
    	System.out.println(question.upvotes.toString());
    	System.out.println(question.rating());
    	flash.success("Thanks for voting");
    	show(postId);
    }
 
}
