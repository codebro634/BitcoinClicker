 package game;
 
 import animations.Animation;
 import animations.AnimationSupervisor;
 import animations.GoldenExplosion;
 import animations.ImageAnimation;
 import control.Resources;
 import graphics.ContentPane;
 import java.awt.Color;
 import java.awt.Graphics;
 import java.awt.Rectangle;
 
 public class GoldenBitcoin {
   private static boolean active = false;
   private static boolean caught = false;
   
   public static boolean isActive() {
     return active;
   }
   
   private static final int WIDTH = ContentPane.convertToScreenX(12.0F); private static final int HEIGHT = WIDTH / 2;
   
   private static int x;
   private static int y;
   private static float angle = 0.0F; private static final int SPAWN_CHANCE = 100000;
   
   public static void spawn() {
     if (!active) {
       Resources.magic.play();
       active = true;
       caught = false;
       x = -WIDTH;
       y = ContentPane.convertToScreenY(50.0F) - HEIGHT / 2;
     } 
   }
   
   private static final int MOVESPEED = ContentPane.convertToScreenX(0.5F);
   
   public static void update() {
     if (active) {
       if (!caught) {
         x += MOVESPEED;
         if (x > ContentPane.convertToScreenX(100.0F)) {
           active = false;
           return;
         } 
         y = (int)(Math.sin(angle) * ContentPane.convertToScreenY(25.0F)) + ContentPane.convertToScreenY(50.0F) - WIDTH / 2;
         angle = (float)(angle + 0.1D);
         if (angle >= 360.0F) {
           angle = 0.0F;
         }
       }
     
     } else if ((int)(Math.random() * 100000.0D) == 0) {
       spawn();
     } 
   }
   
   public static void collect() {
     if (active) {
       caught = true;
       Resources.hooray.play();
       Game.setBitcoins(Game.getBitcoins() * 2.0F);
       final int count = 30 + (int)(Math.random() * 30.0D);
       for (int i = 0; i < count; i++) {
         AnimationSupervisor.animate((Animation)new GoldenExplosion((i * 50), 1, x + WIDTH / 2, y + HEIGHT / 2, ContentPane.convertToScreenX(i / count * 2.0F)));
       }
       AnimationSupervisor.animate((Animation)new ImageAnimation(0L, (count * 50), 1, x, y, WIDTH, HEIGHT, Resources.golden_bitcoin));
       (new Thread() {
           public void run() {
             try {
               Thread.sleep((count * 50 + 1000));
             } catch (InterruptedException interruptedException) {}
             GoldenBitcoin.active = false;
           }
         }).start();
     } 
   }
   
   public static Rectangle getArea() {
     return new Rectangle(x, y, WIDTH, HEIGHT);
   }
   
   public static void draw(Graphics g) {
     if (active) {
       g.setColor(new Color(0.0F, 0.0F, 0.0F, 0.9F));
       g.fillRect(0, 0, ContentPane.convertToScreenX(100.0F), ContentPane.convertToScreenY(100.0F));
       if (!caught)
         g.drawImage(Resources.golden_bitcoin, x, y, WIDTH, HEIGHT, null); 
     } 
   }
 }