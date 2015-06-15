package fingerprint;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Antonio Rajic, Daniel O'Grady
 */
public class Soundbit {
	public final String title;
	private final byte[] fingerprint;
	private final List<int[]> peaks;

	/**
	 * @return the fingerprint of this soundbit
	 */
	public byte[] getFingerprint() {
		return fingerprint;
	}

	/**
	 * Constructor
	 *
	 * @param t
	 *            title of the soundbit
	 * @param fp
	 *            fingerprint file from MusicG
	 */
	public Soundbit(final String t, final byte[] fp) {
		title = t;
		fingerprint = fp;
		peaks = new ArrayList<int[]>();
		compress(5);
	}

	/**
	 * Checks, whether a peak is fully contained within this soundbit
	 *
	 * @param peak
	 *            the peak to check for
	 * @return true, if the peak is fully contained
	 */
	public boolean has(final int[] peak) {
		boolean match = false;
		int i = 0;
		while (!match && i < peaks.size()) {
			int j = 0;
			while (j < peak.length && peak[j] == peaks.get(i)[j]) {
				j++;
			}
			// this is the case iff the inner loop ran through all peaks.
			// Mismatches would have caused it to stop prematurely
			match = j >= peak.length;
			i++;
		}
		return match;
	}

	/**
	 * Compresses the peaks into peak-tuples of size <i>size</i>
	 *
	 * @param size
	 *            size of one peak-tuple
	 */
	private void compress(final int size) {
		for (int i = 0; i < fingerprint.length; i += size) {
			final int[] peaktuple = new int[size];
			for (int j = i; j < fingerprint.length && j < i + size; j++) {
				peaktuple[j - i] = Math.abs(fingerprint[i] - fingerprint[j]) / 10;
			}
			peaks.add(peaktuple);
		}
	}

	/**
	 * Compares the soundbit to another bit. The similarity is the
	 * hit-to-miss-ratio, where "hits" is the number of peak-tuples, they have
	 * in common and "misses" is the number of peaks they differ in
	 *
	 * @param other
	 *            other soundbit to compare this bit with
	 * @return ratio between 1 (equal) and 0 (totally different)
	 */
	public float compare(final Soundbit other) {
		float misses = 0;
		float hits = 0;
		for (final int[] peak : peaks) {
			if (other.has(peak)) {
				hits++;
			} else {
				misses++;
			}
		}
		return misses != 0 ? hits / misses : 1;
	}

	@Override
	public String toString() {
		return "Soundbit for '" + title + "'";
	}
}
