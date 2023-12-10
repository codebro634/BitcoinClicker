 package animations;
 
 import control.Resources;
 import graphics.ContentPane;
 import java.awt.Graphics;
 import java.util.HashSet;
 
 
 public class GoldenExplosion
   extends Animation
 {
   private static final int COUNT = 100;
   private final HashSet<ball> balls = new HashSet<>();
   
   public GoldenExplosion(long delay, int prio, int x, int y, int ball_size) {
     super(delay, prio);
     int count = 50 + (int)(Math.random() * 50.0D);
     for (int i = 0; i < count; i++) {
       double x2 = Math.random() * ContentPane.convertToScreenX(100.0F);
       double y2 = Math.random() * ContentPane.convertToScreenY(100.0F);
       this.balls.add(new ball(Resources.bitcoin, x, y, x2, y2, ball_size));
     } 
   }
   
   public void draw(Graphics g) {
     for (ball b : new HashSet(this.balls)) {
       if (b.isActive())
         b.draw(g); 
     } 
   }
   
   public void render() {
     for (ball b : new HashSet(this.balls)) {
       if (b.isActive()) {
         b.update(); continue;
       } 
       this.balls.remove(b);
     } 
     
     if (this.balls.isEmpty())
       setActive(false); 
   }
 }
