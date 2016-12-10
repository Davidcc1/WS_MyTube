package chat_RMI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.rmi.*;
import java.rmi.registry.*;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author David
 */
public class MytubeClient{

    public static byte[] convert(String file) throws IOException{
      FileInputStream video = new FileInputStream(file);
      ByteArrayOutputStream aos = new ByteArrayOutputStream();
      byte[] b = new byte[1024];
      for (int num;(num = video.read(b)) != -1;){
        aos.write(b,0, num);
      }
      byte[] bytes = aos.toByteArray();
      return bytes;
    }
    public static void main(String args[]) throws IOException
    {

      MytubeServer obj = null;
      String text = "";
      byte[] bytes;
      int portNum = Integer.parseInt(args[0]);

      try {
          String registryURL = "rmi://localhost:"+ portNum +"/myTube";
          obj = (MytubeServer) Naming.lookup(registryURL);
         //Registry registry=LocateRegistry.getRegistry("localhost",1099);
         //obj = (MytubeServer) registry.lookup("Mytube");

      }catch (Exception e){
         System.out.println("MytubeClient exception: " + e.getMessage());
      }
      //server generate the id of client
      String idClient = obj.setIdClient();

      System.out.println("    --Wellcome to Mytube--  ");
      System.out.println();
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      while (!text.equals("!!")){
          System.out.println("1   - Type your description");
          System.out.println("2   - Type !UPDATE to update a video");
          System.out.println("3   - Type !FIND to find a description");
          System.out.println("4   - Type !LIST to get a list of your contents");
          System.out.println("5   - Type !! to leave");

          text = br.readLine();
          if(obj!=null){
              try{
                  Scanner read = new Scanner(System.in);
                  if (text.equals("!LIST")){
                      System.out.println(obj.getDescriptionsFromClient(idClient));
                      System.out.println();
                      System.out.println("    Now you can delete or modify one");
                      System.out.println("- !DEL for delete");
                      System.out.println("- !MOD for modify");
                      text = br.readLine();
                      if (text.equals("!DEL")){
                          System.out.println("    Type the text id: ");
                          int idText = read.nextInt();
                          System.out.println(obj.deleteDescription(idText));
                          System.out.println();
                      }
                      if (text.equals("!MOD")){
                          System.out.println("    Type the text id: ");
                          int idText = read.nextInt();
                          System.out.println("    Now set a new description: ");
                          text = br.readLine();
                          System.out.println(obj.modifyDescription(idText,text));
                          System.out.println();
                      }

                  }else{
                      if (text.equals("!FIND")){
                          System.out.println("    Type 'ID' or Type 'Text'");
                          text = br.readLine();
                          if(text.equals("ID")){
                              System.out.println("    Type the text id: ");
                              int idText = read.nextInt();
                              System.out.println("    The Description with id: "+ idText +" is: "+obj.getDescription(idText));
                              System.out.println();
                          }
                          if(text.equals("Text")){
                              System.out.println("    Type the text: ");
                              text = br.readLine();
                              System.out.println("    The Description: "+ obj.getDescription(text) +" is in server database ");
                              System.out.println();
                              }
                          }
                      if (text.equals("!UPDATE")){
                        text = br.readLine();
                        bytes = convert(text);
                        System.out.println(obj.updateVideo(bytes, idClient, text));
                      }else if(!text.equals("!!")){
                          int idDescription = obj.setDescription(text,idClient);
                          System.out.println("    Id Description: " + idDescription);
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
