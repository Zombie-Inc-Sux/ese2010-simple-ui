package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.jpa.Model;

/**
 * Typesafe constants for voting.
 * @author aaron
 *
 */
@Entity
public class Vote extends Model{
	
	public static Vote UP= new Vote();
	public static Vote DOWN=new Vote();
}

