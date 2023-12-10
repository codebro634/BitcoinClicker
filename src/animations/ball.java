 package animations;
 
 import graphics.ContentPane;
 import java.awt.Color;
 import java.awt.Graphics;
 import java.awt.image.BufferedImage;
 
 
 class ball
 {
   private static final float MOVESPEED = 25.0F;
   private final double[] vector = new double[2];
   
   private final int width;
   
   private double x;
   
   private double y;
   
   private final Color color;
   
   private final BufferedImage img;
   private boolean active = true;
   private final double[] points;
   
   public ball(BufferedImage img, double x1, double y1, double x2, double y2, int width) {
     this.points = new double[] { x1, x2, y1, y2 };
     this.width = width;
     this.color = null;
     this.img = img;
     this.vector[0] = x2 - x1;
     this.vector[1] = y2 - y1;
     double betrag = Math.sqrt(this.vector[0] * this.vector[0] + this.vector[1] * this.vector[1]);
     this.vector[0] = this.vector[0] / betrag;
     this.vector[1] = this.vector[1] / betrag;
     this.x = x1;
     this.y = y1;
   }
   
   public ball(Color color, double x1, double y1, double x2, double y2, int width) {
     this.points = new double[] { x1, x2, y1, y2 };
     this.width = width;
     this.color = color;
     this.img = null;
     this.vector[0] = x2 - x1;
     this.vector[1] = y2 - y1;
     double betrag = Math.sqrt(this.vector[0] * this.vector[0] + this.vector[1] * this.vector[1]);
     this.vector[0] = this.vector[0] / betrag;
     this.vector[1] = this.vector[1] / betrag;
     this.x = x1;
     this.y = y1;
   }
   
   public boolean isActive() {
     return this.active;
   }
   
   public void draw(Graphics g) {
     if (this.color != null) {
       g.setColor(this.color);
       g.fillOval((int)this.x, (int)this.y, this.width, this.width);
     } else {
       
       g.drawImage(this.img, (int)this.x, (int)this.y, this.width, this.width, null);
     } 
   }
   
   public void update() {
     this.x += 25.0D * this.vector[0];
     this.y += 25.0D * this.vector[1];
     if (Math.sqrt(Math.pow(this.x - this.points[1], 2.0D) + Math.pow(this.y - this.points[3], 2.0D)) <= 25.0D || Math.sqrt(Math.pow(this.x - this.points[1], 2.0D) + Math.pow(this.y - this.points[3], 2.0D)) >= ContentPane.convertToScreenX(300.0F))
       this.active = false; 
   }
 }
