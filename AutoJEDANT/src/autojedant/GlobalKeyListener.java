package autojedant;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;


public class GlobalKeyListener implements NativeKeyListener {
        @Override
        public void nativeKeyPressed(NativeKeyEvent e) {
                
                if (e.getKeyCode() == NativeKeyEvent.VK_ESCAPE) {
                        GlobalScreen.unregisterNativeHook();
                }
        }

        @Override
        public void nativeKeyReleased(NativeKeyEvent e) {
            if (e.getKeyCode() == NativeKeyEvent.CTRL_MASK) {
                System.out.println("typeing ans");
            }
        }

        @Override
        public void nativeKeyTyped(NativeKeyEvent e) {
                
        }

        
}
