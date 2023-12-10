 package graphics;
 
 import animations.AnimationSupervisor;
 import control.Resources;
 import game.Achievements;
 import game.Game;
 import game.HoverUpdate;
 import game.Upgrades;
 import java.awt.BasicStroke;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.Rectangle;
 import java.awt.image.BufferedImage;
 import java.util.ArrayList;
 
 public class AchievementScreen
 {
   public static final byte GAMESTATE_INDEX = 2;
   
   public static void render(Graphics g) {
     drawBackground(g);
     drawMiscellaneous(g);
     drawStats(g);
     drawAchievements(g);
   }
   
   private static void drawStats(Graphics g) {
     g.setFont(new Font("Cambria", 1, ContentPane.convertToScreenY(10.0F)));
     g.setColor(Color.WHITE);
     String txt = "Statistiken:";
     int y = ContentPane.convertToScreenX(7.0F) + GameScreen.getStringHeight(g, txt);
     g.drawString(txt, ContentPane.convertToScreenX(61.0F), y);
     ((Graphics2D)g).setStroke(new BasicStroke(3.0F));
     g.drawLine(ContentPane.convertToScreenX(61.0F), y + 4, ContentPane.convertToScreenX(93.0F), y + 4);
     
     String[][] stats = { { "Gesamte Bitcoins:", GameScreen.round(Achievements.lifetime_coins, 1) }, { "Momentane Bitcoins:", GameScreen.round(Game.getBitcoins(), 1)
         }, { "Erklickte Bitcoins:", GameScreen.round(Achievements.lifetime_coins_clicked, 1) }, { "Gekaufte Upgrades:", Achievements.lifetime_upgrades
         }, { "Bitcoins pro Sekunde:", GameScreen.round(Game.getBitcoins_per_second(), 1) }, { "Erfolge freigeschaltet:", Achievements.getTotalUnlockCount()
         }, { "Bitcoins pro Klick:", GameScreen.round(Game.getBitCoinsPerClick(), 1) }, { "", ""
         }, { "Version", "1.3" } };
     
     g.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
     g.fillRect(ContentPane.convertToScreenX(60.0F), ContentPane.convertToScreenY(23.0F), ContentPane.convertToScreenX(35.0F), ContentPane.convertToScreenY(52.0F));
     
     g.setColor(new Color(0.5F, 0.5F, 0.5F, 0.5F));
     int border_size = ContentPane.convertToScreenX(1.0F);
     g.fillRect(ContentPane.convertToScreenX(60.0F) + border_size, ContentPane.convertToScreenY(23.0F) + border_size, ContentPane.convertToScreenX(35.0F) - 2 * border_size, ContentPane.convertToScreenY(52.0F) - 2 * border_size);
     int dist = ContentPane.convertToScreenY(5.0F);
     g.setColor(Color.WHITE);
     g.setFont(new Font("Cambria", 1, ContentPane.convertToScreenY(3.0F))); int i;
     for (i = 0; i < stats.length; i++) {
       g.drawString(stats[i][0], ContentPane.convertToScreenX(63.0F), ContentPane.convertToScreenY(30.0F) + dist * i);
     }
     g.setColor(Color.GREEN);
     for (i = 0; i < stats.length; i++)
       g.drawString(stats[i][1], ContentPane.convertToScreenX(64.0F) + GameScreen.getStringWidth(g, stats[i][0]), ContentPane.convertToScreenY(30.0F) + dist * i); 
   }
   
   public static int getHoverIndex(Rectangle area) {
     Rectangle data = getAchievementPosData();
     for (int index = 0; index < Achievements.getTotalAchievementCount(); index++) {
       int i = index % data.height;
       int y = data.y + data.width * index / data.height;
       int width = data.width;
       int x = data.x + i * width;
       int height = width;
       if (area.intersects(new Rectangle(x, y, width, height)))
         return index; 
     } 
     return -1;
   }
   
   private static void drawAchievements(Graphics g) {
     Rectangle data = getAchievementPosData();
     Rectangle info_text_index = null;
     for (int index = 0; index < Achievements.getTotalAchievementCount(); index++) {
       int i = index % data.height;
       int width = data.width;
       int border_size = width / 10;
       int x = data.x + (data.width - border_size) * i;
       int y = data.y + (data.width - border_size) * index / data.height;
       int height = width;
       g.setColor(Color.BLACK);
       g.fillRect(x, y, width, height);
       Color bg_color = Achievements.isUnlocked(index) ? Color.green : Color.DARK_GRAY;
       g.setColor(bg_color);
       g.fillRect(x + border_size, y + border_size, width - 2 * border_size, height - 2 * border_size);
       BufferedImage img = Achievements.isUnlocked(index) ? Achievements.getImage(index) : Resources.fragezeichen;
       g.drawImage(img, x + border_size, y + border_size, width - 2 * border_size, height - 2 * border_size, null);
       if (HoverUpdate.getHoverIndexAchievementsInAchievementScreen() == index) {
         info_text_index = new Rectangle(x + width, y, ContentPane.convertToScreenX(60.0F), index);
         g.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
         g.fillRect(x + border_size, y + border_size, width - 2 * border_size, height - 2 * border_size);
       } 
     } 
     if (info_text_index != null) {
       drawInfoText(g, info_text_index.x, info_text_index.y, info_text_index.width, info_text_index.height);
     }
   }
   
   public static void drawInfoText(Graphics g, int x, int y, int width, int index) {
     int border_size = width / 100;
     g.setFont(new Font("Cambria", 1, width / 50));
     ArrayList<String> split = Achievements.isUnlocked(index) ? Upgrades.splitToFitLine(g, Achievements.getDescription(index), width - border_size * 4) : new ArrayList<>();
     if (split.isEmpty())
       split.add("???"); 
     g.setColor(new Color(0.0F, 0.0F, 0.0F, 0.7F));
     int height = border_size * 2 + width / 10 + (split.size() + 2) * width / 30;
     if (height + y > ContentPane.convertToScreenY(100.0F)) {
       y -= height + y - ContentPane.convertToScreenY(100.0F);
     }
     g.fillRect(x, y, width, height);
     g.setColor(new Color(0.45F, 0.45F, 0.45F, 0.7F));
     g.fillRect(x + border_size, y + border_size, width - 2 * border_size, height - 2 * border_size);
     BufferedImage img = Achievements.isUnlocked(index) ? Achievements.getImage(index) : Resources.fragezeichen;
     g.drawImage(img, x + 2 * border_size, y + 2 * border_size, width / 10, width / 10, null);
     g.setColor(Color.WHITE);
     g.setFont(new Font("Cambria", 1, width / 20));
     String text = Achievements.isUnlocked(index) ? Achievements.getName(index) : "?";
     GameScreen.drawYCenteredString(g, text, x + border_size * 3 + width / 10, y + border_size * 2, width / 20);
     if (Achievements.isUnlocked(index)) {
       g.setColor(Color.GREEN);
     } else {
       g.setColor(Color.RED);
     }  g.setFont(new Font("Cambria", 1, width / 40));
     text = Achievements.isUnlocked(index) ? "[freigeschaltet]" : "[gesperrt]";
     GameScreen.drawYCenteredString(g, text, x + border_size * 3 + width / 10, y + border_size * 2 + width / 20, width / 20);
     g.setColor(Color.WHITE);
     g.setFont(new Font("Cambria", 1, width / 50));
     g.setColor(new Color(0.75F, 0.75F, 0.75F));
     if (!((String)split.get(0)).equals("???")) {
       for (int i = 0; i < split.size(); i++) {
         GameScreen.drawYCenteredString(g, split.get(i), x + border_size * 2, y + border_size * 2 + width / 10 + i * width / 30, width / 10);
       }
     } else {
       g.setFont(new Font("Cambria", 1, width / 30));
       GameScreen.drawYCenteredString(g, "???", x + border_size * 4, y + border_size * 2 + width / 8, width / 20);
     } 
   }
   
   private static void drawMiscellaneous(Graphics g) {
     g.setFont(new Font("Cambria", 1, ContentPane.convertToScreenY(10.0F)));
     g.setColor(Color.WHITE);
     String txt = "Erfolge:";
     Graphics2D g2 = (Graphics2D)g;
     g2.setStroke(new BasicStroke(3.0F));
     int y = ContentPane.convertToScreenX(7.0F) + GameScreen.getStringHeight(g, txt);
     g.drawString(txt, ContentPane.convertToScreenX(16.0F), y);
     g.drawLine(ContentPane.convertToScreenX(16.0F), y + 4, ContentPane.convertToScreenX(37.0F), y + 4);
     g.setColor(Color.LIGHT_GRAY);
     g.setFont(new Font("Cambria", 1, ContentPane.convertToScreenY(5.0F)));
     txt = "freigeschaltet: " + Achievements.getTotalUnlockCount() + "/" + Achievements.getTotalAchievementCount();
     g.drawString(txt, ContentPane.convertToScreenX(13.5F), GameScreen.getStringHeight(g, txt) + y + ContentPane.convertToScreenY(3.0F));
     Rectangle back = getBackButton();
     g.drawImage(Resources.back, back.x, back.y, back.width, back.height, null);
     if (HoverUpdate.isBackButtonAchHovered()) {
       g.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
       g.fillRect(back.x, back.y, back.width, back.height);
     } 
     g.setColor(Color.WHITE);
     g2.drawLine(ContentPane.convertToScreenX(56.0F), 0, ContentPane.convertToScreenX(56.0F), ContentPane.convertToScreenY(100.0F));
   }
   
   private static void drawBackground(Graphics g) {
     g.drawImage(Resources.background, 0, 0, ContentPane.convertToScreenX(100.0F), ContentPane.convertToScreenY(100.0F), null);
     AnimationSupervisor.drawLowPrio(g);
   }
   
   public static Rectangle getBackButton() {
     return new Rectangle(ContentPane.convertToScreenX(95.0F), 0, ContentPane.convertToScreenX(5.0F), ContentPane.convertToScreenX(5.0F));
   }
   
   public static Rectangle getAchievementPosData() {
     int x = ContentPane.convertToScreenX(13.0F);
     int y = ContentPane.convertToScreenY(35.0F);
     int width = ContentPane.convertToScreenX(7.5F);
     int height = 4;
     return new Rectangle(x, y, width, height);
   }
 }

