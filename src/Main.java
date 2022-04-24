import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.awt.TrayIcon.MessageType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

public class Main extends JFrame {

    // for debuging
    public static <E> void print(E data){
        System.out.println(data);
    }
    public static <E> void print(){
        System.out.println();
    }
        private static final String URL_REGEX =
                "^((((https?|ftps?|gopher|telnet|nntp)://| www.)|(mailto:|news:))" +
                        "(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)" +
                        "([).!';/?:,][[:blank:]])?$";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
        // counter is for fun only
        int counter = 0;
        JPanel mainPanel;
        JLabel banerLabel, resultLabel, bgImage, M4A28;
        JTextField urlEntry;
        JButton findButton;
        JButton goButton;
        String font = "Arial";


    public Main() {
            createAndShowGUI();
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new Main();
                }
            });
        }

        private void createAndShowGUI() {

            mainPanel = new JPanel(null);
            bgImage = new JLabel(new ImageIcon("img/globe.png"));
            banerLabel = new JLabel("Enter URL", JLabel.CENTER);
            urlEntry = new JTextField();
            findButton = new JButton("Get IP Address");
            goButton = new JButton("Go to website");
            resultLabel = new JLabel("", JLabel.CENTER);
            M4A28 = new JLabel("www.twitter.com/M4A28", JLabel.CENTER);

            mainPanel.setPreferredSize(new Dimension(600, 350));
            bgImage.setBounds(0, 0, 600, 350);
            banerLabel.setBounds(200, 75, 200, 30);
            urlEntry.setBounds(150, 115, 300, 36);
            findButton.setBounds(150, 165, 140, 36);
            goButton.setBounds(310, 165, 140, 36);
            resultLabel.setBounds(0, 225, 600, 30);
            M4A28.setBounds(0, 300, 600, 30);

            banerLabel.setFont(new Font(font, Font.PLAIN, 20));
            M4A28.setFont(new Font(font, Font.PLAIN, 20));
            banerLabel.setForeground(Color.WHITE);
            M4A28.setForeground(Color.WHITE);

            urlEntry.setFont(new Font(font, Font.PLAIN, 18));
            urlEntry.setForeground(Color.LIGHT_GRAY);
            urlEntry.setBackground(new Color(10, 10, 10));
            urlEntry.setCaretColor(Color.WHITE);
            urlEntry.setFocusable(true);
            urlEntry.setBorder(BorderFactory.createCompoundBorder(
                    urlEntry.getBorder(),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            findButton.setFont(new Font(font, Font.BOLD, 16));
            findButton.setForeground(Color.LIGHT_GRAY);
            findButton.setBackground(Color.DARK_GRAY);
            findButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            findButton.setFocusable(false);

            goButton.setFont(new Font(font, Font.BOLD, 16));
            goButton.setForeground(Color.LIGHT_GRAY);
            goButton.setBackground(Color.DARK_GRAY);
            goButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            goButton.setFocusable(false);

            resultLabel.setFont(new Font(font, Font.PLAIN, 18));
            mainPanel.add(banerLabel);
            mainPanel.add(urlEntry);
            mainPanel.add(findButton);
            mainPanel.add(goButton);
            mainPanel.add(resultLabel);
            mainPanel.add(resultLabel);
            mainPanel.add(M4A28);
            mainPanel.add(bgImage);
            findButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String url = urlEntry.getText();
                    counter++;
                    if(!url.isEmpty()) {
                        try {
                            InetAddress address = InetAddress.getByName(url);
                            String ip = address.getHostAddress();
                            result(true, ip);
                        }
                        catch (UnknownHostException ex) {
                            result(false,"Please enter a valid URL" );
                        }
                    }
                    else {
                        result(false,"Please enter a valid URL" );
                    }
                    funny(url);
                }
            });
            goButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        String url = urlEntry.getText();
                        if(!url.isEmpty() && isUrlValied(url)) {
                            try {
                                openWebpage(new URL(urlEntry.getText().toString()));
                            }
                            catch (Exception ex) {
                                result(false,"Please enter a valid URL" );
                            }
                        }
                        else {
                            result(false,"Please enter a valid URL" );
                        }
                    }
            });

            add(mainPanel);

            setTitle("IP Finder");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }


        private void funny(String url){
            if(counter == 5 && url.isEmpty()){
                result(false, "are you ok");
                counter = 0;
                try {
                    String title = "popup";
                    String msg = "you well go to my twitter account";
                    popup(title, msg);
                    openWebpage(new URL("https://twitter.com/M4A28"));
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    private void popup (String title, String msg){
        if (SystemTray.isSupported()) {
            try{
                displayTray(title, msg);
            }catch(AWTException ex){
            }catch(MalformedURLException ex){
            }
        }
        else
            JOptionPane.showMessageDialog(null, msg);
    }
    public static boolean openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean openWebpage(URL url) {
        try {
            return openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean isUrlValied(String url) {
        if (url == null) {
            return false;
        }
        Matcher matcher = URL_PATTERN.matcher(url);
        return matcher.matches();
    }

     private void result(boolean ok, String msg){
        if(ok){
            resultLabel.setText(msg);
            resultLabel.setForeground(Color.GREEN);
        }
        else{
            resultLabel.setText(msg);
            resultLabel.setForeground(Color.RED);
        }
    }

    public void displayTray(String titel, String text) throws AWTException, MalformedURLException {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        TrayIcon trayIcon = new TrayIcon(image);
        trayIcon.setImageAutoSize(true);
        tray.add(trayIcon);
        trayIcon.displayMessage(titel, text, MessageType.INFO);
    }
}
