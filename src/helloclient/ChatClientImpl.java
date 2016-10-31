package helloclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming; 
import java.rmi.RemoteException;
import java.util.Random;
import java.util.Scanner;

public class ChatClientImpl 
{ 
    public static void main(String[] arg) throws IOException 
    {
        ChatServerInterface obj = null;
        Random rdm = new Random();
        String text= "";
        try 
        { 
           obj = (ChatServerInterface) Naming.lookup( "//localhost/Hello");
        } 
        catch (Exception e) 
        { 
           System.out.println("HelloClient exception: " + e.getMessage()); 
           e.printStackTrace(); 
        }
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Type your message or !! to leave");
        text = br.readLine();
        while (!text.equals("!!")){
            System.out.println("Type your message or FIND to find a text or !! to leave");
            text = br.readLine();
            if(obj!=null){
                try{
                    int idMessage = obj.sendMessage(text);
                    System.out.println("Message Sent");
                    if (text.equals("FIND")){
                        Scanner read = new Scanner(System.in);
                        System.out.println("Find an ID");
                        int idText = read.nextInt();
                        System.out.println("The message with id: "+ idText +" is: "+obj.getMessage(idText));
                    }
                    else{
                        System.out.println("Id message: " + idMessage);
                    }
                }catch(RemoteException e){
                }
            }
        }
        
    } 
} 