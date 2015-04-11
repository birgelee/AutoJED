/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autojedant;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jnativehook.GlobalScreen;

public class Program {

    public static void main(String[] args) throws InterruptedException, MalformedURLException, AWTException, IOException, URISyntaxException {
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

        fr.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                GlobalScreen.unregisterNativeHook();
                System.exit(0);
            }
        });
        fr.add(p);
        fr.pack();
        fr.setVisible(true);

        while (!go) {
            Thread.sleep(100);
        }
        String url;
        String url2 = "";
        if (!urlField.getText().startsWith("optional")) {
            url = urlField.getText();
            if (!url.startsWith("http://")) {
                url = "http://" + url;
            }
        } else {
            defaultURL = defaultURL.replace("*P*", prefixField.getText());
            defaultURL = defaultURL.replace("*N*", suffixField.getText());
            String page = fetchWebpage(defaultURL, false);// seach string = <li><a href="/atschool/
            int stindex = page.indexOf("<li><a href=\"/atschool/") + 13;
            int endindex = page.indexOf("\" onclick=\"openSmallerReal");
            url = page.substring(stindex, endindex);
            url = "http://www.phschool.com" + url;

            stindex = page.indexOf("<li><a href=\"/atschool/", stindex + 1);
            if (stindex != -1) {
                stindex += 13;
                endindex = page.indexOf("\" onclick=\"openSmallerReal", stindex);
                url2 = page.substring(stindex, endindex);
                url2 = "http://www.phschool.com" + url2;
                urlField.setText("!!!INFO: TWO JEDS FOR THIS CODE!!!");
            }

        }
        System.out.println(url + " and " + url2);
        
        
        GlobalScreen.getInstance().addNativeKeyListener(new GlobalKeyListener());
        parseAnswers(url);
        /*if (!url2.equals("")) {
            parseAnswers(url2);
        }*/
        GlobalScreen.unregisterNativeHook();
        System.exit(0);

    }

    public static boolean allAnswersEntered = false;

    public static boolean go;
    public static Keyboard keyboard;
    public static int answerNum = -1;
    public static List<String> answers = new ArrayList<String>();

    public static void parseAnswers(String url) throws IOException, URISyntaxException {
        answerNum = -1;
        String xml = fetchWebpage(url.replace(".html", ".xml"), true);
        if (xml.charAt(0) != '<') {
            xml = fetchWebpage(url.replace(".html", ".xml"), false);
        }
        System.out.println("xml: " + xml);
        int stindex = 0;
        int endindex = 0;
        answers.clear();
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
        System.out.println("Press ctrl to type answer 1");
        Desktop.getDesktop().browse(new URI(url));
        while (!allAnswersEntered) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String fetchWebpage(String urlString, boolean utf_16) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String result = "";

        String line;

        try {
            url = new URL(urlString);
            is = url.openStream();  // throws an IOException
            if (utf_16) {
                br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_16));
            } else {
                br = new BufferedReader(new InputStreamReader(is));
            }

            while ((line = br.readLine()) != null) {
                result += line + "\n";
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ioe) {
                // nothing to see here
            }
        }
        return result;
    }

}
