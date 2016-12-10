package chat_RMI;

import java.rmi.*;
import java.util.List;

/**
 *
 * @author David
 */
public interface MytubeServer extends Remote {

    public String setIdClient() throws RemoteException;
    public int setDescription(String description,String idClient) throws RemoteException;
    public String getDescription(int id) throws RemoteException;
    public String getDescription(String txt) throws RemoteException;
    public void addCallback(MytubeCallbackImpl CallbackObject) throws RemoteException;
    public List getDescriptionsFromClient(String idClient) throws RemoteException;
    public String deleteDescription(int idDescription) throws RemoteException;
    public String modifyDescription(int idDescription,String text) throws RemoteException;
    public String updateVideo(byte[] video, String idClient, String name) throws RemoteException;
}
