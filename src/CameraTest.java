


import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import org.freedesktop.gstreamer.Bin;
import org.freedesktop.gstreamer.Gst;
import org.freedesktop.gstreamer.Pipeline;

/**
 *
 * @author Neil C Smith (http://neilcsmith.net)
 */
public class CameraTest {

    /**
     * @param args the command line arguments
     */
    
    private static Pipeline pipe;
    
    public static void main(String[] args) {

        Gst.init("CameraTest", args);
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                SimpleVideoComponent vc = new SimpleVideoComponent();
                Bin bin = Bin.launch("udpsrc port=5001 ! application/x-rtp, payload=96 ! rtph264depay ! avdec_h264 ! videoconvert", true);
                pipe = new Pipeline();
                pipe.addMany(bin, vc.getElement());
                Pipeline.linkMany(bin, vc.getElement());           

                JFrame f = new JFrame("Camera Test");
                f.add(vc);
                vc.setPreferredSize(new Dimension(640, 480));
                f.pack();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                pipe.play();
                f.setVisible(true);
            }
        });
    }

}
