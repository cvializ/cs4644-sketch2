import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.*;

public class MicrophoneUtility
{
    public static void mute()
    {
        setMicrophone(true);
    }

    public static void unmute()
    {
        setMicrophone(false);
    }

    public static void setMicrophone(boolean mute)
    {
        Port microphone = null;
        try
        {
            microphone = (Port) AudioSystem.getLine(Port.Info.MICROPHONE);
            microphone.open();
            for (Control control : microphone.getControls())
            {
                if (control instanceof CompoundControl)
                {
                    CompoundControl cc = (CompoundControl) control;
                    for (Control control1 : cc.getMemberControls())
                    {
                        String name = control1.getType().toString();
                        if (name.equals("Select"))
                        {
                            BooleanControl select = (BooleanControl) control1;
                            select.setValue(true);
                        }
                        else if (name.equals("Microphone Boost"))
                        {
                            BooleanControl boost = (BooleanControl) control1;
                            boost.setValue(false);
                        }
                        else if (name.equals("Volume"))
                        {
                            FloatControl volume = (FloatControl) control1;
                            volume.setValue(mute ? volume.getMinimum() : volume.getMaximum());
                        }
                    }

                }
            }

        }
        catch (LineUnavailableException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (microphone != null)
                microphone.close();
        }
    }
}
