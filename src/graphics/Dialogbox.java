 package graphics;
 
 import game.BittieBitcoin;
 import game.Game;
 import game.Upgrades;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics;
 import java.awt.Rectangle;
 import java.util.ArrayList;
 
 public class Dialogbox
 {
   private static String text;
   private static Rectangle area;
   
   public static void setText(String text) {
     Dialogbox.text = text;
     chars_shown = 0;
   }
 
 
   
   public static void setPosition(Rectangle area) {
     Dialogbox.area = area;
   }
   
   private static boolean open = false;
   
   public static boolean isOpen() {
     return open;
   }
   private static int chars_shown;
   public static void open(String text, Rectangle area) {
     setText(text);
     setPosition(area);
     open = true;
   }
   
   public static void close() {
     open = false;
     if (BittieBitcoin.getText(BittieBitcoin.getPhase()) != null) {
       BittieBitcoin.setPhase((byte)(BittieBitcoin.getPhase() + 1));
     }
   }
 
   
   public static void update() {
     if (open && chars_shown < Integer.MAX_VALUE && Game.getGameticks() % 2L == 0L)
       chars_shown++; 
   }
   
   public static void drawBox(Graphics g) {
     if (!open)
       return; 
     g.setFont(new Font("Cambria", 0, 25));
     int border_size = 5;
     ArrayList<String> split = Upgrades.splitToFitLine(g, text, area.width - 3 * border_size);
     int height = split.isEmpty() ? 0 : GameScreen.getStringHeight(g, split.get(0));
     int row_count = 0;
     int count = chars_shown;
     for (String s : split) {
       count -= s.length();
       row_count++;
       if (count < 0)
         break; 
     } 
     count = chars_shown;
     int frame_height = (int)(height * 1.25D * row_count) + height;
     g.setColor(Color.BLACK);
     g.fillRoundRect(area.x, area.y, area.width, frame_height, 50, 50);
     g.setColor(Color.WHITE);
     g.fillRoundRect(area.x + border_size, area.y + border_size, area.width - 2 * border_size, frame_height - 2 * border_size, 50, 50);
     g.setColor(Color.BLACK);
     
     for (int i = 0; i < split.size() && 
       count >= 0; i++) {
       
       count -= ((String)split.get(i)).length();
       String s = split.get(i);
       if (count < 0) {
         int end_index = count + ((String)split.get(i)).length();
         s = s.substring(0, end_index);
       } 
       g.drawString(s, area.x + 2 * border_size, area.y + (int)(height * 1.25D * i) + 2 * border_size + height);
     } 
   }
 }
