package net.codersgarage.iseeu.views;

import net.codersgarage.iseeu.listeners.StreamListener;
import net.codersgarage.iseeu.networks.ISeeUServer;
import net.codersgarage.iseeu.networks.ReceiverServer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.ByteArrayInputStream;

public class StreamPreview extends JFrame implements StreamListener {
    private ReceiverServer receiverServer;

    private ISeeUServer iSeeUServer;

    private JLabel streamView;

    private int windowWidth;
    private int windowHeight;

    public StreamPreview() {
        initComponents();
    }

    private void initComponents() {
        streamView = new JLabel();

        //======== this ========
        setSize(new Dimension(500, 500));
        setPreferredSize(new Dimension(500, 500));

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

        pack();
        setLocationRelativeTo(getOwner());

//        receiverServer = new ReceiverServer();
//        receiverServer.addStreamListener(this);
//        receiverServer.init();

        iSeeUServer = new ISeeUServer();
        iSeeUServer.addStreamListener(this);
        iSeeUServer.init();
    }

    @Override
    public void onStream(byte[] data) {
        try {
            streamView.setIcon(new ImageIcon(ImageIO.read(new ByteArrayInputStream(data))));
//            streamView.setIcon(new ImageIcon(Utils.resizeImage(ImageIO.read(new ByteArrayInputStream(data)), windowWidth, windowHeight)));
            revalidate();
        } catch (Exception e) {
            System.out.println("Error := " + e.getMessage());
        }
    }

    public static void main(String args[]) {
        new StreamPreview().setVisible(true);
    }
}
