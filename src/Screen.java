import java.awt.*;
import javax.swing.JFrame;

public class Screen extends JFrame
{

	private GraphicsDevice vc;

	public Screen()
	{
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		vc = env.getDefaultScreenDevice();
	}

	// two for res and bit depth and refresh rate
	public void setFullScreen(DisplayMode dm)
	{
		this.setUndecorated(true);
		this.setResizable(false);
		vc.setFullScreenWindow(this);

		if (dm != null && vc.isDisplayChangeSupported())
		{
			try {
				vc.setDisplayMode(dm);
			} catch (Exception ex) {
			    // If we can't set the display mode, just return
			    return;
			}
		}
	}

	public Window getFullScreenWindow()
	{
		return vc.getFullScreenWindow();
	}

	public void restoreScreen()
	{
		Window w = vc.getFullScreenWindow();
		if (w != null)
		{
			w.dispose();
		}
		vc.setFullScreenWindow(null);
	}
}
