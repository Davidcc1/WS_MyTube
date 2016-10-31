package helloclient;

import java.rmi.*;

/**
 *
 * @author David
 */
public interface ChatServerInterface extends Remote {
    
    void register(ChatClientImpl client) throws RemoteException;
    public int sendMessage(String message) throws RemoteException;
    public String getMessage(int idClient) throws RemoteException;
}
