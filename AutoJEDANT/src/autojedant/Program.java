/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autojedant;

import java.awt.AWTException;
import java.awt.Button;
import java.awt.Robot;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jnativehook.GlobalScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Program {

    public static void main(String[] args) throws InterruptedException, MalformedURLException, AWTException {
        System.out.println("starting program");
        try {
            GlobalScreen.registerNativeHook();
        } catch (Exception ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
        System.out.println("regestered native hook");
        keyboard = new Keyboard();
        System.out.println("started keyboard");

        //System.setProperty("webdriver.firefox.bin", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe");
        //DesiredCapabilities dc = DesiredCapabilities.chrome();
        //WebDriver driver = new RemoteWebDriver(new URL("http://localhost:9515"), dc);
        String defaultURL = "http://phschool.com/webcodes10/index.cfm?wcprefix=*P*&wcsuffix=*N*&area=view";

        JFrame fr = new JFrame("Enter Web Code");
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel p = new JPanel();
        JButton b = new JButton("GO");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go = true;
            }
        });

        JTextField prefixField = new JTextField(3);
        JTextField suffixField = new JTextField(4);

        JTextField urlField = new JTextField(27);

        prefixField.setText("jed");

        urlField.setText("optional url (use instead of web code)");
        p.add(prefixField);
        p.add(suffixField);
        p.add(urlField);
        p.add(b);

        fr.add(p);
        fr.pack();
        fr.setVisible(true);

        while (!go) {
            Thread.sleep(100);
        }
        String url;
        WebDriver driver = new FirefoxDriver();
        if (!urlField.getText().startsWith("optional")) {
            url = urlField.getText();
        } else {
            defaultURL = defaultURL.replace("*P*", prefixField.getText());
            defaultURL = defaultURL.replace("*N*", suffixField.getText());

            driver.get(defaultURL);
            WebElement el = driver.findElement(By.xpath("/html/body/div[1]/div[3]/div[2]/div[1]/div[1]/ul/li[1]/a"));

            url = el.getAttribute("href");
        }
        System.out.println(url);

        driver.get(url.replace(".html", ".xml"));

        String xml = driver.getPageSource();
        int stindex = 0;
        int endindex = 0;
        while (true) {
            stindex = xml.indexOf("<A>", stindex + 1) + 3;
            if (stindex == 2) {
                break;
            }
            //Thread.sleep(1000);
            endindex = xml.indexOf("</A>", stindex + 1);
            String answ = xml.substring(stindex, endindex);
            answers.add(answ);
            System.out.println("Answer: " + answ);

        }
        GlobalScreen.getInstance().addNativeKeyListener(new GlobalKeyListener());
        System.out.println("Press ctrl to type answer");
        driver.get(url);

    }

    public static boolean go;
    public static Keyboard keyboard;
    public static int answerNum = -1;
    public static List<String> answers = new ArrayList<String>();

}
