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
        ).from(1).fetch(10);
        render(frontQuestion, olderQuestions);
        String user = Security.connected();
        render(user);
    }
    
    public static void show(Long id) {
        Question question = Question.findById(id);
        render(question);
    }
    
    public static void postAnswer(Long postId, @Required String author, @Required String content) {
        Question question = Question.findById(postId);
        if (validation.hasErrors()) {
            render("Application/show.html", question);
        }
        question.addAnswer(author, content);
        flash.success("Thanks for posting %s", author);
        show(postId);
    }
 
}
