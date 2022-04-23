import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;    
import javax.swing.SwingUtilities;

public class Main extends JFrame implements ActionListener {

    // for debuging
    public static <E> void print(E data){
        System.out.println(data);
    }
    public static <E> void print(){
        System.out.println();
    }
        // conter is for fun
        int counter = 0;
        JPanel ipPanal;
        JLabel baner, resultLabel, bgImage, M4A28;
        JTextField urlEntry;
        JButton findButton;

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

            ipPanal = new JPanel(null);
            bgImage = new JLabel(new ImageIcon("img/globe.png"));
            baner = new JLabel("Enter URL", JLabel.CENTER);
            urlEntry = new JTextField();
            findButton = new JButton("Get IP Address");
            resultLabel = new JLabel("", JLabel.CENTER);
            M4A28 = new JLabel("www.twitter.com/M4A28", JLabel.CENTER);

            ipPanal.setPreferredSize(new Dimension(600, 350));
            bgImage.setBounds(0, 0, 600, 350);
            baner.setBounds(200, 75, 200, 30);
            urlEntry.setBounds(150, 115, 300, 36);
            findButton.setBounds(200, 165, 200, 36);
            resultLabel.setBounds(0, 225, 600, 30);
            M4A28.setBounds(0, 300, 600, 30);

            baner.setFont(new Font("Arial", Font.PLAIN, 20));
            M4A28.setFont(new Font("Arial", Font.PLAIN, 20));
            baner.setForeground(Color.WHITE);
            M4A28.setForeground(Color.WHITE);

            urlEntry.setFont(new Font("Arial", Font.PLAIN, 18));
            urlEntry.setForeground(Color.LIGHT_GRAY);
            urlEntry.setBackground(new Color(10, 10, 10));
            urlEntry.setCaretColor(Color.WHITE);
            urlEntry.setFocusable(true);
            urlEntry.setBorder(BorderFactory.createCompoundBorder(
                    urlEntry.getBorder(),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));

            findButton.setFont(new Font("Arial", Font.BOLD, 16));
            findButton.setForeground(Color.LIGHT_GRAY);
            findButton.setBackground(Color.DARK_GRAY);
            findButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
            findButton.setFocusable(false);
            resultLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            ipPanal.add(baner);
            ipPanal.add(urlEntry);
            ipPanal.add(findButton);
            ipPanal.add(resultLabel);
            ipPanal.add(resultLabel);
            ipPanal.add(M4A28);
            ipPanal.add(bgImage);
            findButton.addActionListener(this);

            add(ipPanal);

            setTitle("IP Finder");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String url = urlEntry.getText();
            counter++;

            if(!url.isEmpty())
            {
                try {
                    InetAddress address = InetAddress.getByName(url);
                    String ip = address.getHostAddress();
                    resultLabel.setText(ip);
                    resultLabel.setForeground(Color.GREEN);
                }
                catch (UnknownHostException ex) {
                    resultLabel.setText("Invalid URL or Network is Down");
                    resultLabel.setForeground(Color.RED);
                }
            }
            else {
                resultLabel.setText("Please enter a valid URL");
                resultLabel.setForeground(Color.RED);
            }
            if(counter == 5 && url.isEmpty()){
                resultLabel.setText("are you OK ???");
                resultLabel.setForeground(Color.RED);
                counter = 0;
                try {
                    openWebpage(new URL("https://twitter.com/M4A28"));
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            if(e.getSource().equals(M4A28)){
                try {
                    openWebpage(new URL("https://twitter.com/M4A28"));
                } catch (MalformedURLException ex) {
                    throw new RuntimeException(ex);
                }
            }
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


}
