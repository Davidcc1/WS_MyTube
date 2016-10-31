
package chat_RMI;

import java.rmi.registry.Registry;
import java.rmi.RemoteException; 
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author David
 */
public class ChatServerImpl extends UnicastRemoteObject implements ChatServerInterface 
{
    Map<Integer,String> messages;
    private static Vector callbackObjects; 
    
    public ChatServerImpl() throws RemoteException {
        super();
        this.messages = new HashMap();
        callbackObjects = new Vector();
    }
    
    @Override
    public void register(ChatClientImpl client) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int sendMessage(String message) throws RemoteException {
        int idmessage = messages.hashCode();
        messages.put(idmessage, message);
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
    public String getMessage2(String txt){
        if(messages.containsValue(txt)){
            return txt;
        }else
            return "Text not exists"; 
    }
    @Override
    public void addCallback(ChatCallbackInterface CallbackObject){
        // store the callback object into the vector
        System.out.println("Server got an 'addCallback' call.");
        callbackObjects.addElement (CallbackObject);
    }
    
    public static void callback(int idClient) throws RemoteException{
        ChatCallbackInterface client = (ChatCallbackInterface)callbackObjects.elementAt(idClient);
        client.callMe("server call client " + idClient);
    }
    
    public static void main(String[] args){
        try{
            ChatServerImpl obj = new ChatServerImpl();
            Registry r =LocateRegistry.createRegistry(1099);
            r.bind("Hello", obj);
            System.out.println("Server is connected");
        } catch(Exception e){
            System.out.println("Server not connected: " + e);
        }
        
    }
}
