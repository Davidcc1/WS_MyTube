package chat_RMI;

import java.rmi.*;
import java.util.List;

/**
 *
 * @author David
 */
public interface MytubeServer extends Remote {
    
    public int sendMessage(String message,int idClient) throws RemoteException;
    public String getMessage(int idClient) throws RemoteException;
    public String getMessage(String txt) throws RemoteException;
    public void addCallback(MytubeCallbackImpl CallbackObject) throws RemoteException;
    public List getMessagesFromClient(int idClient) throws RemoteException;
    public String deleteMessage(int idMessage) throws RemoteException;
    public String modifyMessage(int idMessage,String text) throws RemoteException;
}
