package fingerprint;
import java.io.File;

import com.musicg.fingerprint.FingerprintManager;
import com.musicg.wave.Wave;

/**
 * Not entirely sure, why we need to store the fingerprints just to load them
 * again. But here it is. Assuming that we need it for another exercise in the
 * future.
 *
 * @author Antonio Rajic, Daniel O'Grady
 */
public class Fingerprint {
	public static final String FINGERPRINT_DIRECTORY = "out";

	/**
	 * Saves a fingerprint to a file.
	 *
	 * @param file
	 *            file to save the fingerprint to. Will be of format
	 *            {@value #FINGERPRINT_DIRECTORY}/FILENAME.fp
	 */
	public static void saveFingerprint(final File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				for (final File f : file.listFiles()) {
					saveFingerprint(f);
				}
			} else {
				final File directory = new File(FINGERPRINT_DIRECTORY);
				if (!directory.isDirectory()) {
					directory.mkdir();
				}
				final Wave wave = new Wave(file.getAbsolutePath());
				final byte[] fingerprint = wave.getFingerprint();

				final FingerprintManager fpm = new FingerprintManager();
				fpm.saveFingerprintAsFile(fingerprint, FINGERPRINT_DIRECTORY
						+ File.separator + file.getName() + ".fp");
			}
		}
	}

	/**
	 * Loads a fingerprint from a file
	 *
	 * @param file
	 *            file to load the fingerprint form
	 * @return the loaded fingerprint
	 */
	public static byte[] loadFingerprint(final File file) {
		return new FingerprintManager().getFingerprintFromFile(file
				.getAbsolutePath());
	}
}
