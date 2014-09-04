package autojedant;

import java.util.logging.Level;
import java.util.logging.Logger;
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
        if (e.getKeyCode() == NativeKeyEvent.VK_CONTROL) {
            while (true) {
                Program.answerNum++;
                if (Program.answerNum >= Program.answers.size()) {
                    System.out.println("final answer entered, eqiting program.");
                    GlobalScreen.unregisterNativeHook();
                    System.exit(0);
                }
                Program.keyboard.type(Program.answers.get(Program.answerNum));
                if (Program.answerNum == Program.answers.size() - 1) {
                    System.out.println("final answer entered, eqiting program.");
                    GlobalScreen.unregisterNativeHook();
                    System.exit(0);
                }
                
                Program.keyboard.getRobot().keyPress(NativeKeyEvent.VK_DOWN);
                try {
                    Thread.sleep(40);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GlobalKeyListener.class.getName()).log(Level.SEVERE, null, ex);
                }
                Program.keyboard.getRobot().keyRelease(NativeKeyEvent.VK_DOWN);
            }
        }

        if (e.getKeyCode() == NativeKeyEvent.VK_SHIFT) {

            if (Program.answerNum >= Program.answers.size()) {
                System.out.println("final answer entered, eqiting program.");
                GlobalScreen.unregisterNativeHook();
                System.exit(0);
            } else if (Program.answerNum == Program.answers.size() - 1) {
                System.out.println("All answers entered, next ctrl ends program.");
            }
            Program.keyboard.type(Program.answers.get(Program.answerNum));
        }
        if (e.getKeyCode() == NativeKeyEvent.VK_DELETE || e.getKeyCode() == NativeKeyEvent.VK_LEFT) {
            Program.answerNum--;
            System.out.println("Decromenting answer number.");
            if (Program.answerNum >= Program.answers.size()) {
                System.out.println("final answer entered, eqiting program.");
                GlobalScreen.unregisterNativeHook();
                System.exit(0);
            } else if (Program.answerNum == Program.answers.size() - 1) {
                System.out.println("All answers entered, next ctrl ends program.");
            }
            //Program.keyboard.type(Program.answers.get(Program.answerNum));
        }

        if (e.getKeyCode() == NativeKeyEvent.VK_RIGHT) {
            Program.answerNum++;
            System.out.println("Incromenting answer number.");
            if (Program.answerNum >= Program.answers.size()) {
                System.out.println("final answer entered, eqiting program.");
                GlobalScreen.unregisterNativeHook();
                System.exit(0);
            } else if (Program.answerNum == Program.answers.size() - 1) {
                System.out.println("All answers entered, next ctrl ends program.");
            }
            //Program.keyboard.type(Program.answers.get(Program.answerNum));
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {

    }
}
