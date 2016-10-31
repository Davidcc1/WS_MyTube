/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Usuario
 */
public class CallbackImpl extends UnicastRemoteObject implements ChatCallbackInterface{
    public CallbackImpl() throws RemoteException{
        super();
    }

    @Override
    public void callMe(String message) throws RemoteException {
        System.out.println(message);
    }
    
}
