package ranges;

/** A {@link CharSearcher} that contains a {@link CharRange}
 * and a {@link CharSearcherMutable}
 * @author TeamworkGuy2
 * @since 2014-11-2
 */
public final class CharSearchSet implements CharSearcher {
	private CharSearcherMutable chars;
	private IntRangeSearcherMutableImpl ranges;
	private boolean locked;


	public CharSearchSet() {
		this.chars = new CharSearcherMutable();
		this.ranges = new IntRangeSearcherMutableImpl();
	}


	@Override
	public boolean isMatch(char ch) {
		return chars.isMatch(ch) || ranges.isMatch(ch);
	}


	@Override
	public int indexOfMatch(char ch) {
		int index = chars.indexOfMatch(ch);
		if(index > -1) {
			return index;
		}
		else {
			index = ranges.indexOfMatch(ch);
			if(index > -1) {
				return chars.size() + index;
			}
			else {
				return -1;
			}
		}
	}


	public int size() {
		return chars.size() + ranges.size();
	}


	public void addChar(int ch) {
		checkLocked();
		chars.addChar(ch);
	}


	public boolean removeCharAt(int index) {
		checkLocked();
		return chars.removeCharAt(index);
	}


	public boolean removeChar(char ch) {
		checkLocked();
		return chars.removeChar(ch);
	}


	public void addRange(int start, int end) {
		checkLocked();
		ranges.addRange(start, end);
	}


	public boolean removeRangeAt(int index) {
		checkLocked();
		ranges.removeRangeAt(index);
		return true;
	}


	public boolean removeRange(int start, int end) {
		checkLocked();
		return ranges.removeEqualRange(start, end);
	}


	public boolean isLocked() {
		return locked;
	}


	public void setLocked(boolean locked) {
		this.locked = locked;
	}


	private void checkLocked() {
		if(locked) {
			throw new IllegalStateException("cannot modify a locked CharRange");
		}
	}


	public CharSearcher toImmutable() {
		this.setLocked(true);
		return this;
	}

}
