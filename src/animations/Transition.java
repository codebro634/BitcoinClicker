 package animations;
 
 import graphics.ContentPane;
 import java.awt.Color;
 import java.awt.Graphics;
 
 public class Transition extends Animation {
   private float transperancy;
   
   public Transition(long delay, int priority) {
     super(delay, priority);
 
     
     this.transperancy = 1.0F;
   }
   public void draw(Graphics g) {
     g.setColor(new Color(0.0F, 0.0F, 0.0F, this.transperancy));
     g.fillRect(0, 0, ContentPane.convertToScreenX(100.0F), ContentPane.convertToScreenY(100.0F));
   }
   
   public void render() {
     this.transperancy = (float)(this.transperancy - Math.pow(getAge(), 2.0D) / 1.0E8D);
     if (this.transperancy <= 0.0F) {
       setActive(false);
       return;
     } 
   }
 }