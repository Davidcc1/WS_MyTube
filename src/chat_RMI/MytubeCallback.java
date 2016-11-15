/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat_RMI;

import java.rmi.*;

/**
 *
 * @author David
 */
public interface MytubeCallback extends Remote {
    public void callMe(String message) throws RemoteException;
}
