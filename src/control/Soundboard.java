 package control;
 
 import game.StateSupervisor;
 import java.util.ConcurrentModificationException;
 import java.util.HashMap;
 import java.util.HashSet;
 import java.util.Map;
 import javax.sound.sampled.Clip;
 import javax.sound.sampled.LineEvent;
 import javax.sound.sampled.LineListener;
 
 public class Soundboard
   implements LineListener {
   private static boolean muted = false;
   
   public static boolean isMuted() {
     return muted;
   }
   
   public static void toggleMuted() {
     boolean old_value = muted;
     while (muted == old_value) {
       muted = !muted;
       try {
         if (muted) {
           for (Soundboard sb : playing) {
             sounds_on_mute.put(sb, new long[] { sb.sound.getMicrosecondPosition() / 1000L, StateSupervisor.getTime() });
             sb.stop();
           } 
         } else {
           for (Map.Entry<Soundboard, long[]> entry : sounds_on_mute.entrySet()) {
             Soundboard sound = entry.getKey();
             long current_pos = StateSupervisor.getTime() - ((long[])entry.getValue())[1] + ((long[])entry.getValue())[0];
             if (current_pos * 1000L < sound.sound.getMicrosecondLength())
               sound.play(current_pos); 
           } 
           sounds_on_mute.clear();
         } 
       } catch (ConcurrentModificationException e) {
         muted = !muted;
       } 
       Resources.blop.play();
     } 
   }
   
   private static Map<Soundboard, long[]> sounds_on_mute = (Map)new HashMap<>();
   
   private static HashSet<Soundboard> playing = new HashSet<>();
   
   private static HashSet<Soundboard> on_repeat = new HashSet<>();
   
   public static void update() {
     for (Soundboard sb : on_repeat) {
       if (sb.isPlayCompleted())
         sb.play(); 
     } 
   }
   private final Clip sound;
   public static void putOnRepeat(Soundboard sb) {
     if (sb != null)
       on_repeat.add(sb); 
   }
   
   public static void stopRepeat(Soundboard sb) {
     if (sb != null) {
       on_repeat.remove(sb);
     }
   }
 
   
   public Soundboard(Clip sound) {
     this.sound = sound;
   }
   
   public void play() {
     play(0L);
   }
   
   public void play(long pos) {
     if (isMuted())
       return; 
     this.sound.stop();
     this.sound.setMicrosecondPosition(pos * 1000L);
     this.sound.start();
   }
   
   public void stop() {
     this.sound.stop();
   }
   
   public boolean isPlayCompleted() {
     return !playing.contains(this);
   }
   
   public void update(LineEvent event) {
     LineEvent.Type type = event.getType();
     if (type == LineEvent.Type.STOP)
       playing.remove(this); 
     if (type == LineEvent.Type.START)
       playing.add(this); 
   }
 }
