
package chat_RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import java.net.MalformedURLException;
import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
          String URLComplete = "http://localhost:8080/myRESTwsWeb/rest/client/"+idClient+"/desc";
          URL url = new URL (URLComplete);
          HttpURLConnection conn = (HttpURLConnection) url.openConnection();
          conn.setDoOutput(true);
          conn.setRequestMethod("POST");
          conn.setRequestProperty("Content-Type", "application/json");

          String input = "{\"id\":"+idDescription+"\"}";
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
        if(descriptions.containsKey(id)){
            return descriptions.get(id);
        }else
            return "id not exists";
    }

    @Override
    public String getDescription(String txt){
        if(descriptions.containsValue(txt)){
            return txt;
        }else
            return "Text not exists";
    }

    @Override
    public List getDescriptionsFromClient(String idClient) throws RemoteException {
      //GET
      String output = "";
      try{
        String URLComplete = "http://localhost:8080/myRESTwsWeb/rest/client/"+idClient;
        URL url = new URL (URLComplete);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

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
        System.out.println("Client " + idClient + "get a list of his Descriptions");
        return clientDescriptions.get(idClient);
    }

    @Override
    public String deleteDescription(int idDescription) throws RemoteException {
        if (descriptions.containsKey(idDescription)){
            String deleted = descriptions.remove(idDescription);
            System.out.println("Description "+ deleted+" was deleted");
            return "Description "+ deleted +" was deleted";
        }
        return "id not exist";
    }

    @Override
    public String modifyDescription(int idDescription, String text) throws RemoteException {
        if (descriptions.containsKey(idDescription)){
            String changed = descriptions.get(idDescription);
            descriptions.remove(idDescription);
            descriptions.put(idDescription, text);
            System.out.println("Description ("+ changed +") is now ("+text+")");
            return "Description ("+ changed +") is now ("+text+")";
        }
        return "id not exist";
    }

    @Override
    public String updateVideo(byte[] video, String idClient, String name) throws RemoteException{
          System.out.println("Client "+ idClient +" is trying to update the video: "+ name);

          return "not implemented yet";
    }
}
