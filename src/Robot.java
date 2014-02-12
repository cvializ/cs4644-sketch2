import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Image;


public class Robot
{
    private Image faceOpen;
    private Image faceClosed;
    private Graphics g;

    private boolean isSpeaking = false;

    public Robot(String folder, Graphics g) throws IOException
    {
        faceOpen = ImageIO.read(new File(folder + "/open.png"));
        faceClosed = ImageIO.read(new File(folder + "/closed.png"));
        this.g = g;
    }

    public void draw(boolean open)
    {
        Image i = open ? faceOpen : faceClosed;
        g.drawImage(i, 0, 0, null);
    }

    public void stop() {
        this.isSpeaking = false;
    }

    public void speak()
    {
        this.isSpeaking = true;
        boolean isFaceOpen = false;

        while(this.isSpeaking)
        {
            // Draw the next image
            this.draw(isFaceOpen);
            isFaceOpen = !isFaceOpen;

            try
            {
                Thread.sleep(100);
            }
            catch(Exception ex)
            {
                // If the thread sleep causes an exception, continue
                continue;
            }
        }
        this.draw(false);
        //g.dispose();
    }
}
