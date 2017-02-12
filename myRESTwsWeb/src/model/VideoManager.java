package model;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import model.Video;

@Named
@RequestScoped
public class VideoManager {
	
	private Video video = new Video();
	List<Video> videos = new ArrayList<>();
	
	public Video getVideo(){
		return video;
	}
	public void setVideo(Video vid){
		video = vid;
	}
	public List<String> getAllDesc(){
		List<String> list = new ArrayList<>();
		File path = new File("/home/david/ws/");
		File[] listFiles = path.listFiles();
		for(int i = 0;i<listFiles.length;i++){
			if(listFiles[i].isDirectory()){
				File[] vid = listFiles[i].listFiles();
				for(int j = 0;j<vid.length;j++){		
					list.add(vid[i].getName());
				}
			}
		}
		return list;
	}
	
	public void createVideo(String client , byte[] video, String title) throws IOException{
		String id = UUID.randomUUID().toString();
		System.out.println(id);
		File vidC = new File("/home/david/ws/"+client);
		vidC.mkdir();
		File vid = new File("/home/david/ws/"+client+"/vid-"+id+".txt");
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(vid));
			bw.write(title+"\n");
			bw.write(video.toString());
			bw.close();
		}catch(Exception e){
			System.out.println("error: "+e);
		}
	}
	
	public void createDesc(int id, String client , String description) throws IOException{
		String idS = Integer.toString(id);
		File vidC = new File("/home/david/ws/"+client);
		vidC.mkdir();
		File vid = new File("/home/david/ws/"+client+"/"+idS+".txt");
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(vid));
			//bw.write(client+"\n");
			bw.write(description);
			bw.close();
		}catch(Exception e){
			System.out.println("error: "+e);
		}
	}
	
	public String getVideoDesc(int id) throws IOException{
		String idS = Integer.toString(id);
		String file = idS+".txt";
		String desc = "NOT IN WS";
		File path = new File("/home/david/ws/");
		File[] listFiles = path.listFiles();
		for(int i = 0;i<listFiles.length;i++){
			if(listFiles[i].isDirectory()){
				File[] vid = listFiles[i].listFiles();
				for(int j = 0;j<vid.length;j++){
					System.out.println(vid[j].getName());
					System.out.println(file);
					if(vid[j].getName().equals(file)){
						Scanner s = new Scanner(vid[j]);
						try{
							while(s.hasNextLine()){
								desc = s.nextLine();
							}
							s.close();
						}catch(Exception e){
							System.out.println("not exist");
						}
					}
				}
			}
		}
		return desc;
	}
	
	public List<String> getUserDesc(String id) throws IOException{
		List<String> list = new ArrayList<>();
		String path = "/home/david/ws/"+id;
		//String files;
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		for(int i = 0; i< listOfFiles.length;i++){
			if(listOfFiles[i].isFile()){
				
				list.add(listOfFiles[i].getName());
				
			}
		}
		return list;
	}
}
