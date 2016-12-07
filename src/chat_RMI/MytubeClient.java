package chat_RMI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author David
 */
public class MytubeClient{
    public static void main(String args[]) throws IOException
    {
      Random randomGenerator = new Random();
      int idClient = randomGenerator.nextInt(100);
      MytubeServer obj = null;
      String text = "";
      int portNum = Integer.parseInt(args[0]);

      try {
         String registryURL = "rmi://localhost:"+portNum+"/myTube";
          obj = (MytubeServer) Naming.lookup(registryURL);
         //Registry registry=LocateRegistry.getRegistry("localhost",1099);
         //obj = (MytubeServer) registry.lookup("Mytube");

      }catch (Exception e){
         System.out.println("MytubeClient exception: " + e.getMessage());
      }

      System.out.println("    --Wellcome to Mytube--  ");
      System.out.println();
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      while (!text.equals("!!")){
          System.out.println("1   - Type your message");
          System.out.println("2   - Type !FIND to find a text");
          System.out.println("3   - Type !LIST to get a list of your contents");
          System.out.println("4   - Type !! to leave");

          text = br.readLine();
          if(obj!=null){
              try{
                  Scanner read = new Scanner(System.in);
                  if (text.equals("!LIST")){
                      System.out.println(obj.getMessagesFromClient(idClient));
                      System.out.println();
                      System.out.println("    Now you can delete or modify one");
                      System.out.println("- !DEL for delete");
                      System.out.println("- !MOD for modify");
                      text = br.readLine();
                      if (text.equals("!DEL")){
                          System.out.println("    Type the text id: ");
                          int idText = read.nextInt();
                          System.out.println(obj.deleteMessage(idText));
                          System.out.println();
                      }
                      if (text.equals("!MOD")){
                          System.out.println("    Type the text id: ");
                          int idText = read.nextInt();
                          System.out.println("    Now set a new description: ");
                          text = br.readLine();
                          System.out.println(obj.modifyMessage(idText,text));
                          System.out.println();
                      }

                  }else{
                      if (text.equals("!FIND")){
                          //Scanner read = new Scanner(System.in);
                          System.out.println("    Type 'ID' or Type 'Text'");
                          text = br.readLine();
                          if(text.equals("ID")){
                              System.out.println("    Type the text id: ");
                              int idText = read.nextInt();
                              System.out.println("    The message with id: "+ idText +" is: "+obj.getMessage(idText));
                              System.out.println();
                          }
                          if(text.equals("Text")){
                              System.out.println("    Type the text: ");
                              text = br.readLine();
                              System.out.println("    The message: "+ obj.getMessage(text) +" is in server database ");
                              System.out.println();
                              }
                      }else if(!text.equals("!!")){
                          int idMessage = obj.sendMessage(text,idClient);
                          System.out.println("    Id message: " + idMessage);
                          System.out.println();
                      }else{
                          System.out.println();
                          System.out.println("BYE!!");
                      }
                  }
              }catch(RemoteException e){
                  System.err.println("Exception: " + e.getMessage());
              }
          }
      }

    }
}
