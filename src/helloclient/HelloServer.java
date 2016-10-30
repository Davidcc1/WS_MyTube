
package helloclient;

import java.rmi.registry.Registry;
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author David
 */
public class HelloServer extends UnicastRemoteObject implements HelloInterface 
{
    Map<Integer,String> messages;
    
    public HelloServer() throws RemoteException {
        super();
        this.messages = new HashMap();
    }
    
    @Override
    public void register(HelloClient client) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void unregister(HelloClient client) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void sendMessage(int idmessage, String message) throws RemoteException {
        
        messages.put(idmessage, message);
        System.out.println("Id message : "+idmessage);
        System.out.println("Message: "+message);
    }
    
    @Override
    public String getMessage(int id){
        if(messages.containsKey(id)){
            return messages.get(id);
        }else
            return "id not exists"; 
    }
    
    public static void main(String[] args){
        try{
            HelloServer obj = new HelloServer();
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1099);
            r.rebind("Hello", obj);
            System.out.println("Server is connected");
        } catch(Exception e){
            System.out.println("Server not connected: " + e);
        }
        
    }
}
