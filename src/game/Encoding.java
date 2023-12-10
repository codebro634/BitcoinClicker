 package game;
 
 public class Encoding
 {
   private static final String KEY = "SqvdTRu5g8GW99rX7Qpv";
   
   public static String encrypt(String msg) {
     String encrypted = "";
     for (int i = 0; i < msg.length(); i++)
       encrypted = String.valueOf(encrypted) + (msg.charAt(i) + "SqvdTRu5g8GW99rX7Qpv".charAt(i % "SqvdTRu5g8GW99rX7Qpv".length())) + " "; 
     return encrypted;
   }
   
   public static String decrypt(String msg) {
     String decrypted = "";
     String[] split = msg.split(" ");
     for (int i = 0; i < split.length; i++)
       decrypted = String.valueOf(decrypted) + (char)(Integer.parseInt(split[i]) - "SqvdTRu5g8GW99rX7Qpv".charAt(i % "SqvdTRu5g8GW99rX7Qpv".length())); 
     return decrypted;
   }
 }
