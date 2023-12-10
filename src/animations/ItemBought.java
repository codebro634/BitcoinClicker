 package animations;
 
 import graphics.ContentPane;
 import java.awt.Color;
 import java.awt.Graphics;
 import java.util.HashSet;
 
 
 public class ItemBought
   extends Animation
 {
   public static int itembought_animation_count = 0;
   
   private static final int CONFECT_COUNT = 300;
   
   private static final int CONFECT_SIZE = ContentPane.convertToScreenX(3.0F);
   
   private final HashSet<ball> balls = new HashSet<>();
   
   public ItemBought(long delay, int prio) {
     super(delay, prio);
     for (int i = 0; i < 300; i++) {
       double x1 = Math.random() * ContentPane.convertToScreenX(200.0F) - ContentPane.convertToScreenX(50.0F);
       double x2 = Math.random() * ContentPane.convertToScreenX(200.0F) - ContentPane.convertToScreenX(50.0F);
       double y1 = (CONFECT_SIZE - ContentPane.convertToScreenY((int)(Math.random() * 200.0D)));
       double y2 = ContentPane.convertToScreenY(103.0F);
       Color color = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
       this.balls.add(new ball(color, x1, y1, x2, y2, CONFECT_SIZE));
     } 
     itembought_animation_count++;
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
     if (this.balls.isEmpty()) {
       itembought_animation_count--;
       setActive(false);
     } 
   }
 }
