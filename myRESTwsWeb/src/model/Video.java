package model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.validation.constraints.*;

public class Video {

	@Id
	@GeneratedValue(strategy=IDENTITY)
	private int id;
	
	@Column(unique = true)
	@NotNull
	@Size(max = 20, message = "client of video must has at maximum 20 characters")
	private String client;
	
	@NotNull
	@Size(max = 200, message = "max length of description is 200 characters")
	private String description;
	
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getClient(){
		return client;
	}
	public void setClient(String client){
		this.client = client;
	}
	public String getDesc(){
		return description;
	}
	public void setDesc(String desc){
		this.description = desc;
	}
	
	
}
