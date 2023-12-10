 package game;
 
 import animations.Animation;
 import animations.AnimationSupervisor;
 import animations.ItemBought;
 import control.Resources;
 import graphics.ContentPane;
 import graphics.GameScreen;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics;
 import java.awt.image.BufferedImage;
 import java.util.ArrayList;
 
 public class Upgrades
 {
   private static final Object[][] upgrade_data = new Object[][] {
       { Resources.cursor_default, "Grind Power", Long.valueOf(50L), Integer.valueOf(0), Float.valueOf(1.0F), Resources.upgrade_texte[0]
       }, { Resources.gammelpc, "Office PC", Long.valueOf(50L), Integer.valueOf(0), Float.valueOf(1.0F), Resources.upgrade_texte[1]
       }, { Resources.gpu, "GPU", Long.valueOf(250L), Integer.valueOf(0), Float.valueOf(5.0F), Resources.upgrade_texte[2]
       }, { Resources.battery, "GPU-Batterie", Long.valueOf(1250L), Integer.valueOf(0), Float.valueOf(25.0F), Resources.upgrade_texte[3]
       }, { Resources.miningchip2, "Mining-Chip", Long.valueOf(6250L), Integer.valueOf(0), Float.valueOf(125.0F), Resources.upgrade_texte[4]
       }, { Resources.asci, "ASIC", Long.valueOf(31250L), Integer.valueOf(0), Float.valueOf(500.0F), Resources.upgrade_texte[5]
       }, { Resources.poolm, "Pool-Mining", Long.valueOf(156000L), Integer.valueOf(0), Float.valueOf(2500.0F), Resources.upgrade_texte[6]
       }, { Resources.cloud, "Cloud", Long.valueOf(750000L), Integer.valueOf(0), Float.valueOf(12500.0F), Resources.upgrade_texte[7]
       }, { Resources.halle, "Lagerhalle", Long.valueOf(3500000L), Integer.valueOf(0), Float.valueOf(60000.0F), Resources.upgrade_texte[8]
       }, { Resources.skyscraper, "Skyscraper", Long.valueOf(17500000L), Integer.valueOf(0), Float.valueOf(300000.0F), Resources.upgrade_texte[9] } };
   public static final float SCALING_FACTOR = 1.03F;
   
   public static Object[][] getData() {
     return upgrade_data;
   }
   
   public static void drawUpgradeInfoText(Graphics g, int x, int y, int width, int index) {
     int border_size = width / 100;
     g.setFont(new Font("Cambria", 1, width / 50));
     ArrayList<String> split = splitToFitLine(g, getInfo(index), width - border_size * 4);
     g.setColor(new Color(0.0F, 0.0F, 0.0F, 0.7F));
     int height = border_size * 2 + width / 10 + 2 * width / 20 + (split.size() + 2) * width / 30;
     if (height + y > ContentPane.convertToScreenY(100.0F)) {
       y -= height + y - ContentPane.convertToScreenY(100.0F);
     }
     g.fillRect(x, y, width, height);
     g.setColor(new Color(0.45F, 0.45F, 0.45F, 0.7F));
     g.fillRect(x + border_size, y + border_size, width - 2 * border_size, height - 2 * border_size);
     g.drawImage(getUpgradeImage(index), x + 2 * border_size, y + 2 * border_size, width / 10, width / 10, null);
     g.setColor(Color.WHITE);
     g.setFont(new Font("Cambria", 1, width / 20));
     String text = getUpgradeName(index);
     GameScreen.drawYCenteredString(g, text, x + border_size * 3 + width / 10, y + border_size * 2, width / 20);
     g.setColor(Color.LIGHT_GRAY);
     g.setFont(new Font("Cambria", 1, width / 40));
     text = "Upgrade - [im Besitz: " + getUpgradeBought(index) + "]";
     GameScreen.drawYCenteredString(g, text, x + border_size * 3 + width / 10, y + border_size * 2 + width / 20, width / 20);
     g.setColor(Color.WHITE);
     g.setFont(new Font("Cambria", 1, width / 50));
     switch (index) {
       case 0:
         text = "- jeder " + getUpgradeName(index) + " erhöht die Klickstärke um " + GameScreen.round(getBPSIncrease(index), 1) + " Bitcoins pro Klick.";
         GameScreen.drawYCenteredString(g, text, x + border_size * 2, y + border_size + width / 10, width / 10);
         text = "- alle " + getUpgradeName(index) + " [" + getUpgradeBought(index) + "] erhöhen Klickstärke um " + GameScreen.round(getBPSIncrease(index) * getUpgradeBought(index), 1) + " Bitcoins pro Klick";
         GameScreen.drawYCenteredString(g, text, x + border_size * 2, y + border_size + width / 10 + width / 20, width / 10);
         break;
       default:
         text = "- jeder " + getUpgradeName(index) + " generiert " + GameScreen.round(getBPSIncrease(index), 1) + " Bitcoins pro Sekunde";
         GameScreen.drawYCenteredString(g, text, x + border_size * 2, y + border_size + width / 10, width / 10);
         text = "- alle " + getUpgradeName(index) + "  [" + getUpgradeBought(index) + "] generieren " + GameScreen.round(getBPSIncrease(index) * getUpgradeBought(index), 1) + " Bitcoins pro Sekunde";
         GameScreen.drawYCenteredString(g, text, x + border_size * 2, y + border_size + width / 10 + width / 20, width / 10);
         break;
     } 
     g.setColor(new Color(0.75F, 0.75F, 0.75F));
     for (int i = 0; i < split.size(); i++)
       GameScreen.drawYCenteredString(g, split.get(i), x + border_size * 2, y + border_size * 2 + width / 10 + 2 * width / 20 + i * width / 30, width / 10); 
   }
   
   public static ArrayList<String> splitToFitLine(Graphics g, String text, int line_width) {
     ArrayList<String> split = new ArrayList<>();
     String[] space_split = text.split(" ");
     String line = "";
     for (int i = 0; i < space_split.length; i++) {
       line = String.valueOf(line) + " " + space_split[i];
       if (g.getFontMetrics().stringWidth(line) >= line_width) {
         String[] line_split = line.split(" ");
         line = line.substring(0, line.length() - line_split[line_split.length - 1].length());
         split.add(line);
         line = "";
         i--;
       } 
     } 
     split.add(line);
     return split;
   }
   
   public static int getTotalUpgrades() {
     return upgrade_data.length;
   }
   
   public static String getInfo(int index) {
     return (String)upgrade_data[index][5];
   }
   
   public static int getUpgradeBought(int index) {
     return ((Integer)upgrade_data[index][3]).intValue();
   }
   
   public static long getUpgradeCost(int index) {
     return ((Long)upgrade_data[index][2]).longValue();
   }
   
   public static long getUpgradeCost(int index, int count) {
     long total = 0L;
     long cost = getUpgradeCost(index);
     for (int i = 0; i < count; i++) {
       total += cost;
       cost = (long)((float)cost * 1.03F);
     } 
     return total;
   }
   
   public static float getBPSIncrease(int index) {
     return ((Float)upgrade_data[index][4]).floatValue();
   }
 
 
   
   public static boolean buyUpgrade(int index) {
     if (Game.getBitcoins() < (float)getUpgradeCost(index))
       return false; 
     Game.setBitcoins(Game.getBitcoins() - (float)getUpgradeCost(index));
     upgrade_data[index][2] = Long.valueOf((long)((float)((Long)upgrade_data[index][2]).longValue() * 1.03F));
     upgrade_data[index][3] = Integer.valueOf(((Integer)upgrade_data[index][3]).intValue() + 1);
     if (ItemBought.itembought_animation_count == 0)
       AnimationSupervisor.animate((Animation)new ItemBought(0L, 1)); 
     switch (index)
     { case 0:
         Game.setBitCoinsPerClick(Game.getBitCoinsPerClick() + getBPSIncrease(index));
 
 
 
 
         
         Achievements.lifetime_upgrades = Achievements.lifetime_upgrades + 1;
         Resources.boughtsound.play();
         return true; }  Game.setBitcoins_per_second(Game.getBitcoins_per_second() + getBPSIncrease(index)); Achievements.lifetime_upgrades = Achievements.lifetime_upgrades + 1; Resources.boughtsound.play(); return true;
   }
   
   public static BufferedImage getUpgradeImage(int index) {
     return (BufferedImage)upgrade_data[index][0];
   }
   
   public static String getUpgradeName(int index) {
     return (String)upgrade_data[index][1];
   }
 }
