 package animations;
 
 import game.StateSupervisor;
 import graphics.ContentPane;
 import java.awt.Color;
 import java.awt.Graphics;
 
 
 class Body
 {
   private static final int size = ContentPane.convertToScreenX(1.0F);
   
   private int x;
   private int y;
   private final long birth = StateSupervisor.getTime();
   
   private final int direction = (int)(Math.random() * ContentPane.convertToScreenX(0.5F)) - ContentPane.convertToScreenX(0.25F);
   
   private final int upward_thrust = (int)(Math.random() * ContentPane.convertToScreenY(1.0F));
   
   private final Color color = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
   
   Body(int x, int y) {
     this.x = x;
     this.y = y;
   }
   
   void draw(Graphics g) {
     g.setColor(this.color);
     g.fillOval(this.x, this.y, size, size);
   }
   
   void tick() {
     this.x += this.direction;
     this.y -= this.upward_thrust;
     this.y = (int)(this.y + Math.pow(((float)(StateSupervisor.getTime() - this.birth) / 400.0F), 2.0D));
   }
 }
