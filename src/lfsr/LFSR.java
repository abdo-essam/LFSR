/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lfsr;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author hp
 */
public class LFSR {

    /*
    function to encrypte and decrypte text using a key
    */
    static void decryptionANDencryption(ArrayList<Integer> text1,ArrayList<Integer> key,ArrayList<Integer> text2)
    {
     for(int i=0;i<text1.size();i++)
                {
                if((text1.get(i)==0&&key.get(i)==0) || (text1.get(i)==1&&key.get(i)==1))
                    text2.add(0);
                else if((text1.get(i)==1&&key.get(i)==0) || (text1.get(i)==0&&key.get(i)==1))
                    text2.add(1);
                }
    }
    
    /*
    functiom to reading only one line from file
    */
public static String reading_file_line(String path ,int line_num)
{
    String line="";
     try{
         line = Files.readAllLines(Paths.get(path)).get(line_num);
       // System.out.println(line);
      } 
      catch(IOException e)
      {
        System.out.println(e);
      }
 return line;
}

/*
function check the sequence of key to avoid repetition
*/
public static int repetition_check(ArrayList<Integer> n1, ArrayList<Integer> n2) 
{
int repetition=0;
for(int r=0;r<n1.size();r++)
                    {
                    if(n2.get(r)==n1.get(r))
                    {
                    repetition++;
                    }
                    }
return repetition;
}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //reading first line
           String line1=reading_file_line("inputs.txt",0);
           System.out.println("initialvalues :"+line1);
        //reading second line
           String line2=reading_file_line("inputs.txt",1);
           System.out.println("P :"+line2);
        //reading third line
           String line3=reading_file_line("inputs.txt",2);
           System.out.println("X discarding bits  :"+line3);

           
                 //initialize the variable 
                ArrayList<Integer> intial = new ArrayList<Integer>();
                ArrayList<Integer> firstinitial = new ArrayList<Integer>();
                ArrayList<Integer> p = new ArrayList<Integer>();
                ArrayList<Integer> key = new ArrayList<Integer>();
                ArrayList<Integer> plaintext = new ArrayList<Integer>();
                ArrayList<Integer> ciphertext = new ArrayList<Integer>();
                ArrayList<Integer> decryption = new ArrayList<Integer>();
                int X;
                
                
                //converting string to int arraylist 
                for (int i=0;i<9;i++)
                  {
      
                       int convert;
                       char ch=line1.charAt(i);
                       convert=Integer.parseInt(String.valueOf(ch)); 
                       intial.add(convert);
                       firstinitial.add(convert);
        
                  }
                
                //converting string to int arraylist 
                 for (int i=0;i<9;i++)
                  {
      
                        int convert;
                        char ch=line2.charAt(i);
                        convert=Integer.parseInt(String.valueOf(ch)); 
                        p.add(convert);
                  }
                 
                 
             char ch=line3.charAt(0);
             X=Integer.parseInt(String.valueOf(ch)); 
                       
             //input plaintext from console
                  /*Scanner sc= new Scanner(System.in);   
                  System.out.print("Enter a plaintext : ");  
                  String inputtext= sc.nextLine(); 
               
                 for(int i=0;i<inputtext.length();i++)
                     {
                        int convert;
                        char cha=inputtext.charAt(i);
                        convert=Integer.parseInt(String.valueOf(cha)); 
                        plaintext.add(convert);
                     }*/
                 
                     
                 //input plaintext from file
                 String text=reading_file_line("plaintext.txt",0);
               for (int i=0;i<text.length();i++)
                  {
      
                        int convert;
                        char c=text.charAt(i);
                        convert=Integer.parseInt(String.valueOf(c)); 
                        plaintext.add(convert);
                  }
                 System.out.println("plaintext :"+text);
                int xor=0;
                int j=0;
                int sh=3;
                int repetition=0;
                for(int i=0;i<511;i++)
                {
                    
                    //feedback
                 for(j=0;j<9;j++)
                 {
                     
                    if(p.get(j)==1)
                    {
                     if((xor==0&&intial.get(j)==1) ||(xor==1&&intial.get(j)==0))
                      {
                        xor=1;
                      }
                     else if ((xor==0&&intial.get(j)==0) ||(xor==1&&intial.get(j)==1))
                      {
                          xor=0;
                      }
                    }
                 }
                 
                 //set the values of key
                 key.add(intial.get(intial.size()-1));

                 //System.out.println("key" +key.get(i));
                 //shifting 
                 for (sh=8;sh>0;sh--)
                     {
                         intial.set(sh,intial.get(sh-1));
                     }
                 
                    intial.set(0,xor);
                 /*for(int a=0;a<4;a++)
                 {
                  System.out.print(intial.get(a));
                 }*/
                 xor=0;
                //check the sequence
                repetition=repetition_check(firstinitial,intial);
                    if(repetition==9)
                    {
                    break;
                    }
                    else
                        repetition=0;
                }
                
                
                
                
                //print key after LFSR without repetition
                  System.out.println("");
                   System.out.println("key after LFSR without repetition :"+ key.size());
                for(int a=0;a<key.size();a++)
                {
                    System.out.print(key.get(a));
                }
                
                
                
                //discard the first X bits of the key
                for (int i=0;i<X;i++)
                {
                key.remove(i);
                }
                
                
                
                //generate a key to be the same size as the text
                if(plaintext.size()>key.size())
                {
                    int calc=plaintext.size()-key.size();
                    for(int r=0;r<calc;r++)
                    {
                        key.add(key.get(r%key.size()));
                    }
                
                }
               
             
                //print key after discard the first X
                System.out.println("");
                System.out.println("key after discard the first X :" + key.size());
                for(int a=0;a<key.size();a++)
                {
                    System.out.print(key.get(a));
                }
        
                 //print plaintext
                 System.out.println("");
                 System.out.println("plain text");
                for(int a=0;a<plaintext.size();a++)
                {
                    System.out.print(plaintext.get(a));
                }
                
                //encryption plaintext
                 decryptionANDencryption(plaintext,key,ciphertext);
                 
                //print ciphertext
                 System.out.println("");
                System.out.println("cipher text");
                for(int a=0;a<plaintext.size();a++)
                {
                    System.out.print(ciphertext.get(a));
                }
                
                //decryption
                decryptionANDencryption(ciphertext,key,decryption);
                
                        
                                /*for(int i=0;i<ciphertext.size();i++)
                {
                if((ciphertext.get(i)==0&&key.get(i)==0) || (ciphertext.get(i)==1&&key.get(i)==1))
                    decryption.add(0);
                else if((plaintext.get(i)==1&&key.get(i)==0) || (ciphertext.get(i)==0&&key.get(i)==1))
                    decryption.add(1);

                }*/
                                
                 //print plaintext after decryption
                 System.out.println("");
                 System.out.println("decryption");
                for(int a=0;a<decryption.size();a++)
                {
                    System.out.print(decryption.get(a));
                }
        // TODO code application logic here
    }
    
}
