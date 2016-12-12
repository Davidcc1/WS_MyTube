
package chat_RMI;

import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.UUID;

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
