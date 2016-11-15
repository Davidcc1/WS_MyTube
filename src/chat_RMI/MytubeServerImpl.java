
package chat_RMI;

import java.rmi.Naming;
import java.rmi.registry.Registry;
import java.rmi.RemoteException; 
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author David
 */
public class MytubeServerImpl extends UnicastRemoteObject implements MytubeServer 
{
    Map<Integer,String> messages;
    Map<Integer,List<Integer>> clientMessages;
    private static Vector<MytubeCallbackImpl> callbackObjects; 
    
    public MytubeServerImpl() throws RemoteException {
        super();
        this.messages = new HashMap<>();
        this.clientMessages = new HashMap<>();
        callbackObjects = new Vector<>();
    }
    
    @Override
    public int sendMessage(String message, int idClient) throws RemoteException {
        int idmessage = messages.hashCode();
        messages.put(idmessage, message);
        if(!clientMessages.containsKey(idClient)){
            List messagesByClient = new ArrayList();
            messagesByClient.add(idmessage);
            clientMessages.put(idClient,messagesByClient);
        }else{
            clientMessages.get(idClient).add(idmessage);
        }
        for(int i=0;i<callbackObjects.size();i++){
            MytubeCallbackImpl client = callbackObjects.elementAt(i);
            client.callMe(message);
        }
        System.out.println("Client "+ idClient +" sends :"+message);
        return idmessage;
    }
    
    @Override
    public String getMessage(int id){
        if(messages.containsKey(id)){
            return messages.get(id);
        }else
            return "id not exists"; 
    }
    
    @Override
    public String getMessage(String txt){
        if(messages.containsValue(txt)){
            return txt;
        }else
            return "Text not exists"; 
    }
    
    @Override
    public List getMessagesFromClient(int idClient) throws RemoteException {
        System.out.println("Client " + idClient + "get a list of his messages");
        return clientMessages.get(idClient); 
    }
    
    @Override
    public String deleteMessage(int idMessage) throws RemoteException {
        if (messages.containsKey(idMessage)){
            System.out.println("Message "+ messages.remove(idMessage)+" was deleted");
            return "Message "+ messages.remove(idMessage)+" was deleted";
        }
        return "id not exist";
    }

    @Override
    public String modifyMessage(int idMessage, String text) throws RemoteException {
        if (messages.containsKey(idMessage)){
            String changed = messages.get(idMessage);
            messages.remove(idMessage);
            messages.put(idMessage, text);
            System.out.println("Description ("+ changed +") is now ("+text+")");
            return "Description ("+ changed +") is now ("+text+")";
        }
        return "id not exist";
    }
    
    @Override
    public void addCallback(MytubeCallbackImpl CallbackObject){
        System.out.println("Server got an 'addCallback' call.");
        callbackObjects.addElement(CallbackObject);
    }
    
    public static void main(String[] args){
        try{ 
            MytubeServer obj = new MytubeServerImpl();
            String registry = "localhost";
            
            if (args.length >= 1) {
                registry = args[0];
            }
            //String registryURL="rmi://localhost:"+1099+"/some";
            //Naming.rebind(registryURL,obj);
            Registry r =LocateRegistry.createRegistry(1099);
            r.list();
            r.rebind("Mytube", obj);
            System.out.println("Server is connected");
        } catch(Exception e){
            System.out.println("Server not connected: " + e);
        }
        
    }
}
