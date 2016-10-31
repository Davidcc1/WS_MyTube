package chat_RMI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming; 
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Scanner;

public class ChatClientImpl{ 
    ChatClientImpl() throws IOException{
        //System.setSecurityManager(new RMISecurityManager());
        ChatServerInterface obj = null;
        String text = "";
        try 
        { 
           obj = (ChatServerInterface) Naming.lookup( "//localhost/Hello");
           ChatCallbackInterface callbackObj= new CallbackImpl();
           obj.addCallback(callbackObj);
        } 
        catch (Exception e) 
        { 
           System.out.println("HelloClient exception: " + e.getMessage()); 
        }
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (!text.equals("!!")){
            System.out.println("Type your message or FIND to find a text or !! to leave");
            text = br.readLine();
            if(obj!=null){
                try{
                    int idMessage = obj.sendMessage(text);
                    if (text.equals("FIND")){
                        Scanner read = new Scanner(System.in);
                        System.out.println("Type 'ID' or Type 'Text'");
                        text = br.readLine();
                        if(text.equals("ID")){
                            System.out.println("Type the text id: ");
                            int idText = read.nextInt();
                            System.out.println("The message with id: "+ idText +" is: "+obj.getMessage(idText));
                        }
                        if(text.equals("Text")){
                            System.out.println("Type the text: ");
                            text = br.readLine();
                            System.out.println("The message: "+ obj.getMessage2(text) +" is in server database ");
                            }   
                    }else{
                        System.out.println("Id message: " + idMessage);
                    }
                }catch(RemoteException e){
                }
            }
        }
    }
    public static void main(String[] arg) throws IOException 
    {
        ChatClientImpl client = new ChatClientImpl();
        
    } 
} 