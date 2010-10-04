import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

import org.junit.Test;


public class UserTest extends UnitTest{
	
	@Before
	public void setup() {
		Fixtures.deleteAll();
	}
	
	@Test
	public void createAndRetrieveUser() {
		User markus = new User("markus@gmail.com", "1234", "Markus").save();
	    // Retrieve the user with e-mail address markus@gmail.com
	    markus = User.find("byEmail", "markus@gmail.com").first();
	    assertNotNull(markus);
	    assertEquals("Markus", markus.fullname);
	}
	
	@Test
	public void tryConnectAsUser() {
		User markus = new User("markus@gmail.com", "1234", "Markus").save();
	    assertNotNull(User.connect("markus@gmail.com", "1234"));
	    assertNull(User.connect("markus@gmail.com", "badpassword"));
	    assertNull(User.connect("marksu@gmail.com", "1234"));
	}
	
	@Test
	public void createPost() {
		User markus = new User("markus@gmail.com", "1234", "Markus").save();
	    new Question (markus, "My first post", "Hello world").save();
	    assertEquals(1, Question.count());
	    
	    // Retrieve all posts created by a User
	    List<Question> bobPosts = Question.find("byAuthor", markus).fetch();
	    
	    // Tests
	    assertEquals(1, bobPosts.size());
	    Question firstPost = bobPosts.get(0);
	    assertNotNull(firstPost);
	    assertEquals(markus, firstPost.author);
	    assertEquals("My first post", firstPost.title);
	    assertEquals("Hello world", firstPost.content);
	    assertNotNull(firstPost.postedAt);
	}
	
	@Test
	public void postComments() {
		User markus = new User("markus@gmail.com", "1234", "Markus").save();
	    // Create a new post
		Question bobQuestion = new Question(markus, "My first post", "Hello world").save();
	 
	    // Question a first comment
	    new Answer(bobQuestion, "Jeff", "Nice post").save();
	    new Answer(bobQuestion, "Tom", "I knew that !").save();
	 
	    // Retrieve all comments
	    List<Answer> markusQuestionAnswers = Answer.find("byQuestion", bobQuestion).fetch();
	 
	    // Tests
	    assertEquals(2, markusQuestionAnswers.size());
	 
	    Answer firstAnswer = markusQuestionAnswers.get(0);
	    assertNotNull(firstAnswer);
	    assertEquals("Jeff", firstAnswer.author);
	    assertEquals("Nice post", firstAnswer.content);
	    assertNotNull(firstAnswer.postedAt);
	 
	    Answer secondAnswer = markusQuestionAnswers.get(1);
	    assertNotNull(secondAnswer);
	    assertEquals("Tom", secondAnswer.author);
	    assertEquals("I knew that !", secondAnswer.content);
	    assertNotNull(secondAnswer.postedAt);
	}

	@Test
	public void useTheCommentsRelation() {
		User markus = new User("markus@gmail.com", "1234", "Markus").save();
	    Question markusQuestion = new Question(markus, "My first post", "Hello world").save();
	 
	    // Question a first comment
	    markusQuestion.addAnswer("Jeff", "Nice post");
	    markusQuestion.addAnswer("Tom", "I knew that !");
	 
	    // Count things
	    assertEquals(1, User.count());
	    assertEquals(1, Question.count());
	    assertEquals(2, Answer.count());
	 
	    // Retrieve Bob's post
	    markusQuestion = Question.find("byAuthor", markus).first();
	    assertNotNull(markusQuestion);
	 
	    // Navigate to comments
	    assertEquals(2, markusQuestion.answers.size());
	    assertEquals("Jeff", markusQuestion.answers.get(0).author);
	    
	    // Delete the post
	    markusQuestion.delete();
	    
	    // Check that all comments have been deleted
	    assertEquals(1, User.count());
	    assertEquals(0, Question.count());
	    assertEquals(0, Answer.count());
	}

}
