
package chat_RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Arrays;

import java.net.MalformedURLException;
import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 *
 * @author David
 */
public class MytubeServerImpl extends UnicastRemoteObject implements MytubeServer
{
    Map<Integer,String> descriptions;
    Map<String,List<Integer>> clientDescriptions;
    Map<Integer, byte[]> videos;

    public MytubeServerImpl() throws RemoteException {
        super();
        this.descriptions = new HashMap<>();
        this.clientDescriptions = new HashMap<>();
    }

    public String setIdClient(){
      String id = UUID.randomUUID().toString();
      return id;
    }

    @Override
    public int setDescription(String description, String idClient) throws RemoteException {
        int idDescription = descriptions.hashCode();
        if(!descriptions.containsKey(idDescription)){
          descriptions.put(idDescription, description);
        }else{
         idDescription = descriptions.hashCode();
         descriptions.put(idDescription, description);
        }
        if(!clientDescriptions.containsKey(idClient)){
            List clientDescription = new ArrayList();
            clientDescription.add(idDescription);
            clientDescriptions.put(idClient,clientDescription);
        }else{
            clientDescriptions.get(idClient).add(idDescription);
        }
        //POST
        String output = "";
        try{
          String URLComplete = "http://localhost:8080/myRESTwsWeb/rest/desc";
          URL url = new URL (URLComplete);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setDoOutput(true);
          conn.setRequestMethod("POST");
          conn.setRequestProperty("Content-Type", "application/json");
          String input = "{\"client\":\""+idClient+"\",\"desc\":\""+description+"\",\"id\":\""+idDescription+"\"}";

          OutputStream os = conn.getOutputStream();
          os.write(input.getBytes());
          os.flush();

          if(conn.getResponseCode() != HttpURLConnection.HTTP_CREATED){
            throw new RuntimeException("Failed: HTTP error code: "+conn.getResponseCode());
          }
          BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

          while((output = br.readLine()) != null){
            System.out.println("\nClient json: "+ output );
          }
          conn.disconnect();
        }catch(MalformedURLException e){
          e.printStackTrace();
        }catch(IOException e){
          e.printStackTrace();
        }

        System.out.println("Client "+ idClient +" sends :"+ description);
        return idDescription;
    }

    @Override
    public String getDescription(int id){
      //GET
      String output = "";
      try{
        String URLComplete = "http://localhost:8080/myRESTwsWeb/rest/desc/"+id;
        URL url = new URL (URLComplete);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
	      conn.setRequestProperty("Accept", MediaType.APPLICATION_JSON);

        if(conn.getResponseCode() != 200){
          throw new RuntimeException("Failed: HTTP error code: "+conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String sol = null;
        while((output = br.readLine()) != null){
          System.out.println("\nClient json: "+ output );
          sol = output;
        }
        conn.disconnect();
	    Gson json = new Gson();
            String out = json.fromJson(output,String.class);

        return sol;
      }catch(MalformedURLException e){
        e.printStackTrace();
      }catch(IOException e){
        e.printStackTrace();
      }
      return "not connected";

    }

    @Override
    public String getDescription(String txt){
        if(descriptions.containsValue(txt)){
            return txt;
        }else
            return "Text not exists";
    }

    @Override
    public List<String> getDescriptionsFromClient(String idClient) throws RemoteException {
      //GET
      String output = "";
      List<String> descs = null;
      try{
        String URLComplete = "http://localhost:8080/myRESTwsWeb/rest/client/"+idClient+"/descs";
        URL url = new URL (URLComplete);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
	      conn.setRequestProperty("Accept", MediaType.APPLICATION_JSON);

        if(conn.getResponseCode() != 200){
          throw new RuntimeException("Failed: HTTP error code: "+conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        output = br.readLine();
        //descs.add(output);
        conn.disconnect();
        Gson json = new Gson();
        String[] desc = json.fromJson(output,String[].class);
        descs = new ArrayList<>(Arrays.asList(desc));
        System.out.println(descs);

        System.out.println("Client " + idClient + "get a list of his Descriptions");
        return descs;
      }catch(MalformedURLException e){
        e.printStackTrace();
      }catch(IOException e){
        e.printStackTrace();
      }
       return descs;
    }
    
      public List<String> getAllDesc(String idClient) throws RemoteException {
      //GET
      String output = "";
      List<String> descs = null;
      try{
        String URLComplete = "http://localhost:8080/myRESTwsWeb/rest/client/descs";
        URL url = new URL (URLComplete);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
	      conn.setRequestProperty("Accept", MediaType.APPLICATION_JSON);

        if(conn.getResponseCode() != 200){
          throw new RuntimeException("Failed: HTTP error code: "+conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        output = br.readLine();
        conn.disconnect();
        Gson json = new Gson();
        String[] desc = json.fromJson(output,String[].class);
        descs = new ArrayList<>(Arrays.asList(desc));
        System.out.println(descs);

        System.out.println("Client " + idClient + "get a list of his Descriptions");
        return descs;
      }catch(MalformedURLException e){
        e.printStackTrace();
      }catch(IOException e){
        e.printStackTrace();
      }
       return descs;
    }


    @Override
    public String deleteDescription(int idDescription) throws RemoteException {
        if (descriptions.containsKey(idDescription)){
            String deleted = descriptions.remove(idDescription);
            System.out.println("Description "+ deleted+" was deleted");
            return "Description "+ deleted +" was deleted";
        }
        String output = "";
        try{
          String URLComplete = "http://localhost:8080/myRESTwsWeb/rest/desc/"+idDescription+"/delete";
          URL url = new URL (URLComplete);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setRequestMethod("DELETE");
  	      conn.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON);
          if(conn.getResponseCode() != 200){
            throw new RuntimeException("Failed: HTTP error code: "+conn.getResponseCode());
          }
          return "description deleted";
        }catch(MalformedURLException e){
          e.printStackTrace();
        }catch(IOException e){
          e.printStackTrace();
        }
        return "Description "+idDescription+" was deleted";
    }

    @Override
    public String modifyDescription(String idClient, int idDescription, String text) throws RemoteException {
        if (descriptions.containsKey(idDescription)){
            String changed = descriptions.get(idDescription);
            descriptions.remove(idDescription);
            descriptions.put(idDescription, text);
            System.out.println("Description ("+ changed +") is now ("+text+")");
            return "Description ("+ changed +") is now ("+text+")";
        }
        String output = "";
        try{
        String URLComplete = "http://localhost:8080/myRESTwsWeb/rest/desc/"+idDescription+"/modify";
        URL url = new URL (URLComplete);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", MediaType.APPLICATION_JSON);
        String input = "{\"client\":\""+idClient+"\",\"desc\":\""+text+"\",\"id\":\""+idDescription+"\"}";

        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        if(conn.getResponseCode() != 200){
          throw new RuntimeException("Failed: HTTP error code: "+conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        while((output = br.readLine()) != null){
          System.out.println("\nClient json: "+ output );
        }
        conn.disconnect();
      }catch(MalformedURLException e){
        e.printStackTrace();
      }catch(IOException e){
        e.printStackTrace();
      }

        return "id not exist";
    }

    @Override
    public String updateVideo(byte[] bytes, String idClient, String title) throws RemoteException{
	     System.out.println("Client "+ idClient +" is trying to update the video: "+ title);
	//POST
        int idDesc = descriptions.hashCode();
        String output = "";
        Video vid = new Video(idDesc,bytes,idClient, title);
        try{
          String URLComplete = "http://localhost:8080/myRESTwsWeb/rest/video/";
          URL url = new URL (URLComplete);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setDoOutput(true);
          conn.setRequestMethod("POST");
          conn.setRequestProperty("Content-Type", "application/json");
          
          //String input = "{\"client\":\""+idClient+"\",\"title\":\""+name+"\",\"video\":\""+video+"\"}";

	  Gson json = new Gson();
	  String input = json.toJson(vid);

          OutputStream os = conn.getOutputStream();
          os.write(input.getBytes());
          os.flush();

          if(conn.getResponseCode() != HttpURLConnection.HTTP_CREATED){
            throw new RuntimeException("Failed: HTTP error code: "+conn.getResponseCode());
          }
          BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

          while((output = br.readLine()) != null){
            System.out.println("\nClient json: "+ output );
          }
          conn.disconnect();
	  return title;
    }catch(MalformedURLException e){
      e.printStackTrace();
    }catch(IOException e){
      e.printStackTrace();
    }
        return "video "+title+" was updated";
  }
}
