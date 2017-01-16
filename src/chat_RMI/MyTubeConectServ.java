
package chat_RMI;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;

public class MyTubeConectServ {
  public static void main(String args[]){
    try{
      MytubeServer obj = new MytubeServerImpl();
      //int numPort = Integer.parseInt(args[0]);
      int numPort = 2000;
      startRegistry(numPort);
      String registryURL = "rmi://localhost:"+numPort+"/myTube";
      Naming.rebind(registryURL, obj);
      System.out.println("Server Ready.");
    }catch(Exception e){
      System.out.println("RMI Server not connected");
    }
  }
  public static void startRegistry(int RMIPortNum) throws RemoteException{
    try{
    Registry registry = LocateRegistry.getRegistry(RMIPortNum);
    registry.list();
    }catch(RemoteException e){
    System.out.println("RMI cannot located at port :"+RMIPortNum);
    Registry registry = LocateRegistry.createRegistry(RMIPortNum);
    System.out.println("RMI Registry created at port :" + RMIPortNum);
  }
  }
}
