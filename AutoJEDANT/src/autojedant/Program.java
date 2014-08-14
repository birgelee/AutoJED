/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package autojedant;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.jnativehook.GlobalScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class Program {

    public static void main(String[] args) throws InterruptedException, MalformedURLException {
        System.out.println("starting program");
        try {
            GlobalScreen.registerNativeHook();
        } catch (Exception ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }
        GlobalScreen.getInstance().addNativeKeyListener(new GlobalKeyListener());
        
        System.out.println("regestered native hook");
        
        System.setProperty("webdriver.firefox.bin", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe");
        DesiredCapabilities dc = DesiredCapabilities.chrome();
        //WebDriver driver = new RemoteWebDriver(new URL("http://localhost:9515"), dc);
        WebDriver driver = new FirefoxDriver();
        driver.get("http://phschool.com/atschool/realidades/Activities/PrelimChapL3/PrelimL30001.xml");
        //WebElement el = driver.findElement(By.xpath("/html/body/object/embed"));
        //el.click();
        String xml = driver.getPageSource();
        int stindex = 0;
        int endindex = 0;
        while (true) {
            stindex = xml.indexOf("<A>", stindex + 1) + 3;
            if (stindex == 1) {
                break;
            }
            endindex = xml.indexOf("</A>", stindex + 1);
            answers.add(xml.substring(stindex, endindex));
            
            
        }
        driver.get("http://phschool.com/atschool/realidades/Activities/PrelimChapL3/PrelimL30001.html");
        System.out.println("Press ctrl to type answer");

        //Thread.sleep(500);
    }
    int answerNum = 0;
    public static List<String> answers = new ArrayList<String>();

}

