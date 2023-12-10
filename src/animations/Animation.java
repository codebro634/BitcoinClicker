 package animations;
 
 import game.StateSupervisor;
 import java.awt.Graphics;
 
 public abstract class Animation {
   private final long start = StateSupervisor.getTime();
   
   private final long delay;
   
   private final int priority;
   
   private boolean isActive = true;
   
   public Animation(long delay, int priority) {
     this.delay = delay;
     this.priority = priority;
   }
   
   public abstract void render();
   
   public abstract void draw(Graphics paramGraphics);
   
   public boolean isActive() {
     return this.isActive;
   }
   
   public void setActive(boolean isActive) {
     this.isActive = isActive;
   }
   
   public long getStart() {
     return this.start;
   }
   
   public long getAge() {
     return StateSupervisor.getTime() - getStart();
   }
   
   public long getDelay() {
     return this.delay;
   }
   
   public int getPriority() {
     return this.priority;
   }
 }