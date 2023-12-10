 package graphics;
 
 import animations.AnimationSupervisor;
 import control.Resources;
 import game.HoverUpdate;
 import java.awt.BasicStroke;
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.Rectangle;
 
 public class CreditsScreen
 {
   public static final byte GAMESTATE_INDEX = 3;
   
   public static void render(Graphics g) {
     g.drawImage(Resources.background, 0, 0, ContentPane.convertToScreenX(100.0F), ContentPane.convertToScreenY(100.0F), null);
     AnimationSupervisor.drawLowPrio(g);
     drawMiscellaneous(g);
   }
   
   private static final String[] featuring = new String[] { "Robin S.", "Nguyen K.", "Lennart K.", "Laurin K.", "Yaesin S.", "Adnan Q.", "Moritz B.", "Marvin S.", "Christopher K." };
   
   private static void drawMiscellaneous(Graphics g) {
     Rectangle back = getBackButton();
     g.drawImage(Resources.back, back.x, back.y, back.width, back.height, null);
     if (HoverUpdate.isBackButtonCreditsHovered()) {
       g.setColor(new Color(1.0F, 1.0F, 1.0F, 0.25F));
       g.fillRect(back.x, back.y, back.width, back.height);
     } 
     g.setFont(new Font("Cambria", 1, ContentPane.convertToScreenY(10.0F)));
     g.setColor(Color.WHITE);
     g.drawString("Bitc", ContentPane.convertToScreenX(30.0F), ContentPane.convertToScreenY(15.0F));
     g.drawImage(Resources.bitcoin, ContentPane.convertToScreenX(41.7F), ContentPane.convertToScreenY(9.7F), ContentPane.convertToScreenX(3.5F), ContentPane.convertToScreenX(3.5F), null);
     g.drawString("inClicker", ContentPane.convertToScreenX(45.5F), ContentPane.convertToScreenY(15.0F));
     ((Graphics2D)g).setStroke(new BasicStroke(5.0F));
     g.drawLine(ContentPane.convertToScreenX(30.0F), ContentPane.convertToScreenY(15.0F) + 5, ContentPane.convertToScreenX(70.0F), ContentPane.convertToScreenY(15.0F) + 5);
     g.setFont(new Font("Cambria", 1, ContentPane.convertToScreenY(5.0F)));
     
     g.drawString("erstellt von:", ContentPane.convertToScreenX(41.0F), ContentPane.convertToScreenY(22.0F));
     g.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
     int x = ContentPane.convertToScreenX(22.0F), y = ContentPane.convertToScreenY(25.0F), width = ContentPane.convertToScreenX(56.0F), height = ContentPane.convertToScreenY(70.0F);
     g.fillRect(x, y, width, height);
     g.setColor(new Color(0.5F, 0.5F, 0.5F, 0.5F));
     int border_size = ContentPane.convertToScreenX(1.0F);
     g.fillRect(x + border_size, y + border_size, width - 2 * border_size, height - 2 * border_size);
     g.setColor(Color.WHITE);
     for (int i = 0; i < featuring.length; i++) {
       GameScreen.drawCenteredString(g, featuring[i], new Rectangle(ContentPane.convertToScreenX(0.0F), ContentPane.convertToScreenY(30.0F) + ContentPane.convertToScreenY(6.0F) * i, ContentPane.convertToScreenX(100.0F), ContentPane.convertToScreenY(10.0F)));
     }
   }
   
   public static Rectangle getBackButton() {
     return new Rectangle(ContentPane.convertToScreenX(95.0F), 0, ContentPane.convertToScreenX(5.0F), ContentPane.convertToScreenX(5.0F));
   }
 }
