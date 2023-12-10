 package graphics;
 
 import java.awt.Rectangle;
 import java.awt.event.MouseEvent;
 import java.awt.event.MouseListener;
 
 
 
 class Event
   implements MouseListener
 {
   static Event e = new Event();
   
   public void mouseClicked(MouseEvent arg0) {}
   
   public void mouseEntered(MouseEvent arg0) {}
   
   public void mouseExited(MouseEvent arg0) {}
   
   public void mousePressed(MouseEvent e) {
     if ((new Rectangle(e.getX() - 5, e.getY() - 27, 5, 5)).intersects(MessageFrame.getConfirmPos()))
       MessageFrame.close(); 
   }
   
   public void mouseReleased(MouseEvent e) {}
 }
