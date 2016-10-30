package helloclient;

import java.rmi.*;

/**
 *
 * @author David
 */
public interface HelloInterface extends Remote {
    
    void register(HelloClient client) throws RemoteException;
    void unregister(HelloClient client) throws RemoteException;
    public void sendMessage(int idClient, String message) throws RemoteException;
    public String getMessage(int idClient) throws RemoteException;
}
