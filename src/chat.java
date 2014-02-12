

import java.io.IOException;
import java.awt.Graphics;
import java.awt.DisplayMode;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.sound.sampled.Control;
import javax.sound.sampled.Port;
import javax.sound.sampled.AudioSystem;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.util.Scanner;
import com.google.code.chatterbotapi.*;

public class Chat
{
    private Screen screen;
    private Exiter exit;
    private Robot robot;

    private Runnable robotRun = new Runnable() {
        @Override
        public void run()
        {
            robot.speak();
        }
    };

    private class Exiter implements KeyListener
    {
        private boolean exit = false;

        public boolean getExit()
        {
            return exit;
        }

        @Override
        public void keyPressed(KeyEvent e) { exit = true; }

        @Override
        public void keyReleased(KeyEvent e) { }

        @Override
        public void keyTyped(KeyEvent e) { }
    }



    public void chat()
    {
        System.out.println("1");
        voce.SpeechInterface.init("C:/voce-0.9.1/lib", true, true, "src/grammar", "tree");
        ChatterBotFactory factory = new ChatterBotFactory();

        System.out.println("3");
        ChatterBot bot2 = null;
        try
        {
            bot2 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
        }
        catch (Exception e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return;
        }
        ChatterBotSession bot2session = bot2.createSession();
        System.out.println("4");

        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice voice = voiceManager.getVoice("kevin16");
        voice.allocate();

        // Set up the display
        DisplayMode dm = new DisplayMode(800, 600, 16,
            DisplayMode.REFRESH_RATE_UNKNOWN);

        exit = new Exiter();
        screen = new Screen();
        screen.addKeyListener(exit);

        try {
            screen.setFullScreen(dm);

            try
            {
                robot = new Robot("images", screen.getFullScreenWindow().getGraphics());
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }

            String call = "Hello";
            String response = null;

            while (!exit.getExit())
            {
                // It's speaking, so mute it so the voice recognizer doesn't
                // pick up on the microphone
                MicrophoneUtility.mute();

                try
                {
                    response = bot2session.think(call);
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return;
                }

                if (response.contains("<a"))
                {
                    int i = response.indexOf("</a>");
                    response = response.substring(i + 8);
                }

                System.out.println("bot> " + response);

                // Asynchronously start speaking.
                new Thread(robotRun).start();
                voice.speak(response);

                // Synchronously stop speaking
                robot.stop();

                // It's listening
                MicrophoneUtility.unmute();
                while (true)
                {
                    call = voce.SpeechInterface.popRecognizedString();
                    if(call != "")
                    {
                        System.out.println(call);
                        break;
                    }
                }
            }
        } finally {
            screen.restoreScreen();
        }
    }

    public static void main(String[] args) {
        Chat c = new Chat();
        c.chat();
    }
}
