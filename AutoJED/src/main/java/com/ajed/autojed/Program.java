package com.ajed.autojed;

import java.net.MalformedURLException;
import java.net.URL;

import org.jnativehook.GlobalScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:9515"), dc);
        //WebDriver driver = new FirefoxDriver();
        driver.get("http://phschool.com/atschool/realidades/Activities/PrelimChapL3/PrelimL30001.html");
        WebElement el = driver.findElement(By.xpath("/html/body/object/embed"));
        el.click();
        Thread.sleep(2000);

        System.out.println("sending keys");

        //Thread.sleep(500);
    }

}
