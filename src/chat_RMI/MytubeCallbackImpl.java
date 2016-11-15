package chat_RMI;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author David
 */
public class MytubeCallbackImpl extends UnicastRemoteObject implements MytubeCallback{
    

    public static MytubeServer mytube;

    public MytubeCallbackImpl() throws RemoteException {
    }

    public static void main(String[] args) {
        try {
            //String registryURL = "rmi://localhost:"+1099+"/some";
            //obj = (MytubeServer) Naming.lookup(registryURL);
            Registry registry=LocateRegistry.getRegistry("localhost",1099);
            mytube = (MytubeServer)registry.lookup("Mytube");
            
            MytubeCallbackImpl callback = new MytubeCallbackImpl();
            mytube.addCallback(callback);
        } catch (NotBoundException nbe) {
            System.out.println("Error -> "+nbe );
        } catch (RemoteException e) {
            System.out.println("RMI Error - " + e);
        }
        
    }

    @Override
    public void callMe(String message) throws RemoteException {
        System.out.println(message);
    }
}
