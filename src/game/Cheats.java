 package game;
 
 import control.Resources;
 import graphics.MessageFrame;
 import java.awt.event.KeyEvent;
 
 public class Cheats
 {
   public static boolean cheats_enabled = false;
   private static final String IDGAF_EASTEREGG = "idgaf";
   
   public static boolean areCheatsEnabled() {
     return cheats_enabled;
   }
 
 
   
   private static final String PASSWORD = "12345";
   
   private static final String DISABLE_CODE = "stop";
   
   private static String last_chars = "";
   
   public static void keyTyped(KeyEvent e) {
     last_chars = String.valueOf(last_chars) + e.getKeyChar();
     while (last_chars.length() > 30)
       last_chars = (String)last_chars.subSequence(1, last_chars.length()); 
     if (last_chars.length() >= "12345".length() && last_chars.substring(last_chars.length() - "12345".length()).equals("12345")) {
       if (!cheats_enabled)
         MessageFrame.display(new String[] { "Cheats enabled:", "Access to all commands", "Cheats" }, Resources.cheats_symbol, false); 
       cheats_enabled = true;
     }
     else if (last_chars.length() >= "stop".length() && last_chars.substring(last_chars.length() - "stop".length()).equals("stop")) {
       if (cheats_enabled)
         MessageFrame.display(new String[] { "Cheats disabled:", "Loss of access to all commands", "Cheats" }, Resources.cheats_symbol, false); 
       cheats_enabled = false;
     }
     else if (last_chars.length() >= "idgaf".length() && last_chars.substring(last_chars.length() - "idgaf".length()).equals("idgaf")) {
       MessageFrame.display(new String[] { "Idgaf", "Du hast das geheime Idgaf-Easteregg gefunden!", "EASTEREGG" }, Resources.idgaf, false);
     } 
     
     if (areCheatsEnabled())
       cheat(e); 
   }
   
   private static void cheat(KeyEvent e) {
     switch (e.getKeyChar()) {
       case 'f':
         Game.addBitcoins(Game.getBitcoins());
         break;
       case 'g':
         GoldenBitcoin.spawn();
         break;
       case 'a':
         Achievements.unlockAll();
       case 't':
         BittieBitcoin.resetSpeech();
         break;
     } 
   }
 }
