 package control;
 
 import java.io.BufferedReader;
 import java.io.BufferedWriter;
 import java.io.File;
 import java.io.FileReader;
 import java.io.FileWriter;
 import java.io.IOException;
 import java.util.ArrayList;
 
 public class FileManager
 {
   public static void writeFile(String filename, String[] text) {
     File levelFile = new File(filename);
     if (!levelFile.exists()) {
       try {
         levelFile.createNewFile();
         (new File(filename)).createNewFile();
       } catch (IOException e) {
         e.printStackTrace();
       } 
     }
     FileWriter writeFile = null;
     BufferedWriter writer = null;
     try {
       writeFile = new FileWriter(levelFile);
       writer = new BufferedWriter(writeFile); byte b; int i;
       String[] arrayOfString;
       for (i = (arrayOfString = text).length, b = 0; b < i; ) { String s = arrayOfString[b];
         writer.write(s);
         writer.newLine();
         b++; }
     
     } catch (Exception e) {
       e.printStackTrace();
     } finally {
       try {
         if (writer != null) {
           writer.close();
         }
       } catch (Exception exception) {}
     } 
   }
 
 
   
   public static String[] readFile(String filename) {
     File file = new File(filename);
     if (!file.exists()) return null;
     
     ArrayList<String> content = new ArrayList<>();
     
     BufferedReader reader = null;
     try {
       reader = new BufferedReader(new FileReader(file));
       String line = reader.readLine();
       while (line != null) {
         content.add(line);
         line = reader.readLine();
       }
     
     } catch (Exception e) {
       e.printStackTrace();
     } finally {
       try {
         if (reader != null)
           reader.close(); 
       } catch (Exception x) {
         x.printStackTrace();
       } 
     } 
     
     String[] arr = new String[content.size()];
     for (int i = 0; i < content.size(); i++) {
       arr[i] = content.get(i);
     }
     return arr;
   }
 }