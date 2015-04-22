package autojedant;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

public class GlobalKeyListener implements NativeKeyListener {

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VK_CONTROL && Program.armKeyListener) {
            while (true) {
                Program.answerNum++;
                if (Program.answerNum >= Program.answers.size()) {
                    System.out.println("final answer entered.");
                    Program.allAnswersEntered = true;
                    break;
                }
                Program.keyboard.type(Program.answers.get(Program.answerNum));
                if (Program.answerNum == Program.answers.size() - 1) {
                    System.out.println("final answer entered.");
                    Program.allAnswersEntered = true;
                    break;
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
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {

    }
}
