package net.codersgarage.iseeu.views;

import net.codersgarage.iseeu.listeners.StreamListener;
import net.codersgarage.iseeu.networks.ISeeUServer;
import net.codersgarage.iseeu.utils.Settings;
import net.codersgarage.iseeu.utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.ByteArrayInputStream;

public class StreamPreview extends JFrame implements StreamListener {
    private JMenuBar menuBar;
    private JMenu mainMenu;
    private JMenuItem exitMenu;

    private JMenu settingsMenu;
    private JMenuItem settingsViewMenu;
    private JMenuItem settingsUpdateMenu;

    private ISeeUServer iSeeUServer;

    private JLabel streamView;

    private Settings settings;

    private int windowWidth;
    private int windowHeight;

    public StreamPreview() {
        initComponents();
    }

    private void initComponents() {
        settings = new Settings();

        streamView = new JLabel();

        //======== this ========
        setSize(new Dimension(500, 500));
        setPreferredSize(new Dimension(500, 500));

        menuBar = new JMenuBar();
        mainMenu = new JMenu("Menu");
        exitMenu = new JMenuItem("Exit");
        exitMenu.addActionListener(e -> onExitMenu());

        settingsMenu = new JMenu("Settings");

        settingsViewMenu = new JMenuItem("View");
        settingsViewMenu.addActionListener(e -> onSettingsViewMenu());
        settingsUpdateMenu = new JMenuItem("Update");
        settingsUpdateMenu.addActionListener(e -> onSettingsUpdateMenu());
        settingsMenu.add(settingsUpdateMenu);
        settingsMenu.add(settingsViewMenu);

        mainMenu.add(exitMenu);
        menuBar.add(mainMenu);
        menuBar.add(settingsMenu);


        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        contentPane.add(Box.createHorizontalGlue());
        contentPane.add(streamView);
        contentPane.add(Box.createHorizontalGlue());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                windowWidth = e.getComponent().getWidth();
                windowHeight = e.getComponent().getHeight();

                System.out.println(windowWidth + " : " + windowHeight);
                revalidate();
            }
        });

        setJMenuBar(menuBar);

        pack();
        setLocationRelativeTo(getOwner());

//        receiverServer = new ReceiverServer();
//        receiverServer.addStreamListener(this);
//        receiverServer.init();

        iSeeUServer = new ISeeUServer();
        iSeeUServer.addStreamListener(this);
        iSeeUServer.init();
    }

    private void onSettingsViewMenu() {
        JLabel hostValue = new JLabel("Host : " + settings.getHost());
        JLabel portValue = new JLabel("Port : " + String.valueOf(settings.getPort()));
        JCheckBox fullScreenValue = new JCheckBox("Full Screen");
        fullScreenValue.setSelected(settings.isFullScreen());
        Object objects[] = new Object[3];
        objects[0] = hostValue;
        objects[1] = portValue;
        objects[2] = fullScreenValue;
        JOptionPane.showMessageDialog(this, objects, "Settings", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onSettingsUpdateMenu() {
        JLabel hostLabel = new JLabel("Host");
        JTextField hostValue = new JTextField();
        JLabel portLabel = new JLabel("Port");
        JTextField portValue = new JTextField();
        JCheckBox fullScreenValue = new JCheckBox("Full Screen");
        Object objects[] = new Object[5];
        objects[0] = hostLabel;
        objects[1] = hostValue;
        objects[2] = portLabel;
        objects[3] = portValue;
        objects[4] = fullScreenValue;

        int x = JOptionPane.showConfirmDialog(this, objects, "Settings", JOptionPane.OK_CANCEL_OPTION);
        if (x == JOptionPane.OK_OPTION) {
            if (!hostValue.getText().isEmpty()) {
                settings.setHost(hostValue.getText());
            }
            if (!portValue.getText().isEmpty()) {
                try {
                    settings.setPort(Integer.parseInt(portValue.getText()));
                } catch (Exception ex) {

                }
            }
            settings.setFullScreen(fullScreenValue.isSelected());
            settings.write();
        }
    }

    private void onExitMenu() {
        System.exit(0);
    }
    
    @Override
    public void onStream(byte[] data) {
        try {
            if (!settings.isFullScreen())
                streamView.setIcon(new ImageIcon(ImageIO.read(new ByteArrayInputStream(data))));
            else
                streamView.setIcon(new ImageIcon(Utils.resizeImage(ImageIO.read(new ByteArrayInputStream(data)), windowWidth, windowHeight)));
            revalidate();
        } catch (Exception e) {
            System.out.println("Error := " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception ex) {

        }

        new StreamPreview().setVisible(true);
    }
}
