package fingerprint;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.musicg.fingerprint.FingerprintManager;

/**
 * Sheet 7, Ex 3
 *
 * @author Antonio Rajic, Daniel O'Grady
 *
 *         Antwort auf die Frage:
 *         <ul>
 *         <li>coldplay.wav - ritmo.wav (~12%)
 *         <li>gotorio.wav -
 *         <li>ritmo.wav (~6%) ritmo.wav - coldplay.wav (~10%)
 *         <li>top_of_the_world_7_.wav - gotorio.wav (~5%)
 *         </ul>
 *
 *         Der Höreindruck deckt sich mit den Ergebnissen. Erstaunlich ist
 *         lediglich, dass die Ähnlichkeit zwischen gotorio.wav und ritmo.wav
 *         prozentual kaum größer ist, als die zwischen top_of_the_world_7.wav
 *         und gotorio.wav
 */
public class Main {
	public static final String FINGERPRINT_DIRECTORY = "out";

	public static void main(final String[] args) {
		final List<Soundbit> soundbits = new ArrayList<Soundbit>();
		final FingerprintManager fpm = new FingerprintManager();
		// load all fingerprints as soundbits
		for (final String path : new File(FINGERPRINT_DIRECTORY).list()) {
			soundbits.add(new Soundbit(path, fpm.getFingerprintFromFile(FINGERPRINT_DIRECTORY + File.separator + path)));
		}
		// comparation
		for (int i = 0; i < soundbits.size(); i++) {
			for (int j = 0; j < soundbits.size(); j++) {
				if (i != j) {
					System.out.println(soundbits.get(i) + " vs " + soundbits.get(j));
					System.out.println(soundbits.get(i).compare(soundbits.get(j)));
				}
			}
		}
	}
}
