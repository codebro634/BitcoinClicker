 package game;
 
 import control.FileManager;
 import control.Resources;
 import graphics.MessageFrame;
 import java.util.ArrayList;
 import java.util.Iterator;
 
 
 
 public class Save
 {
   public static final String FILE_PATH = "Spieldaten.dat";
   
   public static void saveData() {
     ArrayList<String> save = new ArrayList<>();
     save.add(Encoding.encrypt(BittieBitcoin.getPhase()));
     save.add(Encoding.encrypt(Game.getBitcoins()));
     save.add(Encoding.encrypt(Game.getBitcoins_per_second()));
     save.add(Encoding.encrypt(Game.getBitCoinsPerClick()));
     save.add(Encoding.encrypt(String.valueOf(Achievements.lifetime_coins) + "/" + Achievements.lifetime_coins_clicked + "/" + Achievements.lifetime_upgrades));
     ArrayList<Integer> list = Achievements.getUnlockedAchievements();
     save.add(Encoding.encrypt(list.size()));
     for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext(); ) { int k = ((Integer)iterator.next()).intValue();
       save.add(Encoding.encrypt(k)); }
      for (int i = 0; i < Upgrades.getTotalUpgrades(); i++)
       save.add(Encoding.encrypt(String.valueOf(Upgrades.getUpgradeCost(i)) + "/" + Upgrades.getUpgradeBought(i) + "/" + Upgrades.getBPSIncrease(i))); 
     String[] text = new String[save.size()];
     for (int j = 0; j < save.size(); j++)
       text[j] = save.get(j); 
     FileManager.writeFile("Spieldaten.dat", text);
   }
 
   
   public static boolean loadData() {
     String[] file = FileManager.readFile("Spieldaten.dat");
     if (file == null)
       return true; 
     try {
       int line = 0;
       byte phase = Byte.parseByte(Encoding.decrypt(file[line++]));
       BittieBitcoin.setPhase(phase);
       float bitcoins = Float.parseFloat(Encoding.decrypt(file[line++]));
       Game.setBitcoins(bitcoins);
       float bps = Float.parseFloat(Encoding.decrypt(file[line++]));
       Game.setBitcoins_per_second(bps);
       float bpc = Float.parseFloat(Encoding.decrypt(file[line++]));
       Game.setBitCoinsPerClick(bpc);
       String[] stats = Encoding.decrypt(file[line++]).split("/");
       Achievements.lifetime_coins = Float.parseFloat(stats[0]);
       Achievements.lifetime_coins_clicked = Float.parseFloat(stats[1]);
       Achievements.lifetime_upgrades = Integer.parseInt(stats[2]);
       int ach_count = Integer.parseInt(Encoding.decrypt(file[line++]));
       for (int i = 0; i < ach_count; i++) {
         int index = Integer.parseInt(Encoding.decrypt(file[line + i]));
         Achievements.unlock(index, true);
       } 
       line += ach_count;
       Object[][] upgrades = Upgrades.getData();
       for (int j = 0; j < Upgrades.getTotalUpgrades(); j++) {
         String[] split = Encoding.decrypt(file[line + j]).split("/");
         long cost = Long.parseLong(split[0]);
         int count = Integer.parseInt(split[1]);
         float bps_increase = Float.parseFloat(split[2]);
         upgrades[j][2] = Long.valueOf(cost);
         upgrades[j][3] = Integer.valueOf(count);
         upgrades[j][4] = Float.valueOf(bps_increase);
       }
     
     } catch (Exception e) {
       MessageFrame.display(new String[] { "Fehler beim Laden:", "Lösche die Datei 'Spieldaten.dat'.", "ERROR" }, Resources.error, true);
       return false;
     } 
     return true;
   }
 }
