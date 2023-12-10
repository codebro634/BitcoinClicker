 package game;
 
 import control.Resources;
 import graphics.ContentPane;
 import graphics.Dialogbox;
 import graphics.GameScreen;
 import java.awt.Color;
 import java.awt.Graphics;
 import java.awt.Rectangle;
 
 
 public class BittieBitcoin
 {
   private static byte phase = 1;
   
   public static void resetSpeech() {
     phase = 1;
     Dialogbox.close();
   }
   
   public static byte getPhase() {
     return phase;
   }
   
   public static void setPhase(byte phase) {
     BittieBitcoin.phase = phase;
   }
   
   public static void update() {
     if (phase >= 8 && phase < 21 && !Dialogbox.isOpen() && Game.getGameticks() % 6000L == 0L) {
       setPhase((byte)(getPhase() + 1));
     }
     if (!Dialogbox.isOpen() && 
       getText(phase) != null) {
       Dialogbox.open(getText(phase), new Rectangle((getBoxPos(phase)).x, (getBoxPos(phase)).y, (getBoxPos(phase)).width, 0));
     }
   }
   
   public static String getText(int phase) {
     switch (phase) {
       case 1:
         return "Hallo, ich bin Bittie der Bitcoin. Ich werde dir jetzt erklären, wie das hier alles funktioniert. Drücke eine beliebige Taste.";
       case 2:
         return "Vieles lässt sich beobachten oder anklicken. Experimentiere! Dies hier ist der Bitcoin. Klick mal! Dies entspricht dem Minen.";
       case 3:
         return "Hier kannst du auswählen, was du dir mit deinen Bitcoins kaufst. Je mehr Einkäufe, desto mehr Bitcoins werden generiert. Durch strg oder shift kannst du sogar 10 bzw. 100 Einkäufe aufeinmal betätigten!";
       case 4:
         return "Hier hast du die Auswahl deine Erfolge anzusehen, den Ton zu regulieren und die Credits zu betrachten.";
       case 5:
         return "Hier ist der Bitcoingraph zu sehen. Jenachdem wie viele Bitcoins du generiest, ändert sich der Graph. Der Umrechnungskurs von Bitcoin in andere Zahlungsmittel, ist abhängig von Abgebot und Nachfrage. Bitcoin wird nicht von Staaten emittiert.";
       case 6:
         return "Hier siehst du den Fortschritt deiner Upgrades.";
       case 7:
         return "Das wars von meiner Seite. Viel Spaß beim Generieren von Bitcoins :]";
       case 9:
         return Resources.bittie_texte[0];
       case 11:
         return Resources.bittie_texte[1];
       case 13:
         return Resources.bittie_texte[2];
       case 15:
         return Resources.bittie_texte[3];
       case 17:
         return Resources.bittie_texte[4];
       case 19:
         return Resources.bittie_texte[5];
       case 21:
         return Resources.bittie_texte[6];
     } 
     return null;
   }
 
   
   private static Rectangle getBoxPos(int phase) {
     switch (phase) {
       case 1:
         return new Rectangle(ContentPane.convertToScreenX(40.0F), ContentPane.convertToScreenY(30.0F), ContentPane.convertToScreenX(25.0F), 0);
       case 2:
         return new Rectangle(ContentPane.convertToScreenX(15.0F), ContentPane.convertToScreenY(30.0F), ContentPane.convertToScreenX(25.0F), 0);
       case 3:
         return new Rectangle(ContentPane.convertToScreenX(50.0F), ContentPane.convertToScreenY(30.0F), ContentPane.convertToScreenX(25.0F), 0);
       case 4:
         return new Rectangle(ContentPane.convertToScreenX(20.0F), ContentPane.convertToScreenY(10.0F), ContentPane.convertToScreenX(25.0F), 0);
       case 5:
         return new Rectangle(ContentPane.convertToScreenX(13.0F), ContentPane.convertToScreenY(55.0F), ContentPane.convertToScreenX(25.0F), 0);
       case 6:
         return new Rectangle(ContentPane.convertToScreenX(35.0F), ContentPane.convertToScreenY(30.0F), ContentPane.convertToScreenX(25.0F), 0);
       case 7:
         return new Rectangle(ContentPane.convertToScreenX(40.0F), ContentPane.convertToScreenY(30.0F), ContentPane.convertToScreenX(25.0F), 0);
       case 9:
         return new Rectangle(ContentPane.convertToScreenX(15.0F), ContentPane.convertToScreenY(15.0F), ContentPane.convertToScreenX(60.0F), 0);
       case 11:
         return new Rectangle(ContentPane.convertToScreenX(35.0F), ContentPane.convertToScreenY(35.0F), ContentPane.convertToScreenX(35.0F), 0);
       case 13:
         return new Rectangle(ContentPane.convertToScreenX(35.0F), ContentPane.convertToScreenY(35.0F), ContentPane.convertToScreenX(45.0F), 0);
     } 
     return new Rectangle(ContentPane.convertToScreenX(35.0F), ContentPane.convertToScreenY(35.0F), ContentPane.convertToScreenX(25.0F), 0);
   }
 
   
   private static Rectangle getAreaHighlight(int phase) {
     switch (phase) {
       case 1:
         return new Rectangle(0, 0, 0, 0);
       case 2:
         return new Rectangle((GameScreen.getCookiePos()).x - 30, (GameScreen.getCookiePos()).y - 30, (GameScreen.getCookiePos()).width + 60, (GameScreen.getCookiePos()).height + 60);
       case 3:
         return GameScreen.getRightRectPos();
       case 4:
         return GameScreen.getTopGrid();
       case 5:
         return GameScreen.getGraphPos();
       case 6:
         return GameScreen.getUpgradeVisualizationPos();
       case 7:
         return new Rectangle(0, 0, 0, 0);
     } 
     return new Rectangle(0, 0, 0, 0);
   }
 
   
   public static void paint(Graphics g) {
     if (getText(phase) == null)
       return; 
     highlight_area(g, getAreaHighlight(phase));
     drawBittie(g, phase);
     Dialogbox.drawBox(g);
   }
   
   private static void highlight_area(Graphics g, Rectangle area) {
     g.setColor(new Color(0.0F, 0.0F, 0.0F, 0.9F));
     g.fillRect(0, area.y, area.x, area.height);
     g.fillRect(area.x + area.width, area.y, ContentPane.convertToScreenX(100.0F) - area.x - area.width, area.height);
     g.fillRect(0, 0, ContentPane.convertToScreenX(100.0F), area.y);
     g.fillRect(0, area.y + area.height, ContentPane.convertToScreenX(100.0F), ContentPane.convertToScreenY(100.0F) - area.y - area.height);
   }
   
   private static void drawBittie(Graphics g, int phase) {
     Rectangle box = getBoxPos(phase);
     drawWhiteCircle(g, box.x - ContentPane.convertToScreenX(5.0F), box.y + ContentPane.convertToScreenY(5.0F), ContentPane.convertToScreenX(2.5F));
     g.drawImage(Resources.bittie, box.x - ContentPane.convertToScreenX(13.0F), box.y + ContentPane.convertToScreenY(10.0F), ContentPane.convertToScreenX(10.0F), ContentPane.convertToScreenX(10.0F), null);
   }
   
   public static void drawWhiteCircle(Graphics g, int x, int y, int radius) {
     g.setColor(Color.BLACK);
     g.fillOval(x, y, radius, radius);
     g.setColor(Color.WHITE);
     g.fillOval(x + 5, y + 5, radius - 10, radius - 10);
   }
 }