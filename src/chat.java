

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
        voce.SpeechInterface.init("C:/voce-0.9.1/lib", true, true, "src/grammar", "tree");
        ChatterBotFactory factory = new ChatterBotFactory();

        System.out.println("3");
        ChatterBot bot2 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
        ChatterBotSession bot2session = bot2.createSession();
        System.out.println("4");

        VoiceManager voiceManager = VoiceManager.getInstance();
        Voice voice = voiceManager.getVoice("kevin16");
        voice.allocate();

        String call = "Hello";
        String response;
        while (true)
        {
            // It's speaking, so mute it so the voice recognizer doesn't
            // pick up on the microphone
            MicrophoneUtility.mute();

            response = bot2session.think(call);
            if (response.contains("<a"))
            {
                int i = response.indexOf("</a>");
                response = response.substring(i + 8);
            }

            System.out.println("bot> " + response);
            //voce.SpeechInterface.synthesize(response);

            synchronized(voice) {
                voice.speak(response);
            }
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
    }
}
