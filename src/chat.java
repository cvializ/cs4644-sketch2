

import javax.sound.sampled.Control;
import javax.sound.sampled.Port;
import javax.sound.sampled.AudioSystem;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.util.Scanner;
import com.google.code.chatterbotapi.*;

public class chat {

    public static void main(String[] args) throws Exception {
        System.out.println("1");
        voce.SpeechInterface.init("C:/voce-0.9.1/lib", true, true, "src/grammar", "digits");
        ChatterBotFactory factory = new ChatterBotFactory();

        System.out.println("3");
        ChatterBot bot2 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
        ChatterBotSession bot2session = bot2.createSession();
        System.out.println("4");
        VoiceManager man = VoiceManager.getInstance();
//        Voice voices[] = man.getVoices();
//        for (Voice k : voices)
//        {
//            System.out.println(k.getName());
//        }

        Voice v = man.getVoice("kevin16");
        AudioSystem a;

        if (AudioSystem.isLineSupported(Port.Info.MICROPHONE)) {

                Port line = (Port) AudioSystem.getLine(
                    Port.Info.MICROPHONE);
                System.out.println(line.getLineInfo());
                Control c[] = line.getControls();
                for (Control o : c)
                {
                    System.out.println(o.getType().toString());
                }
        }

        v.allocate();
        Scanner in = new Scanner(System.in);
        String s = "Hello";
        //
        while (true)
        {

           // System.out.println("bot1> " + s);
            //v.speak(s);
            s = bot2session.think(s);
            if (s.contains("<a"))
            {
                int i = s.indexOf("</a>");
                s = s.substring(i + 8);
            }

            System.out.println("bot> " + s);
            voce.SpeechInterface.synthesize(s);
           v.speak(s);
           s = null;

           MicrophoneUtility.unmute();
           while (true)
           {
               s = "";
               s = voce.SpeechInterface.popRecognizedString();
               if(s != "")
               {
                   System.out.println(s);
                   break;
               }
           }
           MicrophoneUtility.mute();

           // s = in.nextLine();
        }
    }
}
