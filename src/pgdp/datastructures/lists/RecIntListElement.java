package pgdp.datastructures.lists;

public class RecIntListElement {
	private int value;
	private RecIntListElement next;
	private RecIntListElement prev;

	public RecIntListElement(int value) {
		this(value, null);
	}

	public RecIntListElement(int value, RecIntListElement prev) {
		this.value = value;
		next = null;
		this.prev = prev;
	}

	public RecIntListElement append(int value) {
		if (next != null) {
			return next.append(value);
		} else {
			next = new RecIntListElement(value, this);
			return next;
		}
	}

	public int get(int idx) {
		if (idx == 0) {
			return value;
		}
		if (next == null) {
			System.out.println("Invalid index: list is to short!");
			return Integer.MIN_VALUE;
		}
		return next.get(idx - 1);
	}

	public int size() {
		if (next == null) {
			return 1;
		}
		return 1 + next.size();
	}

	public boolean insert(int value, int idx) {
		if (idx < 0) {
			System.out.println("Cannot insert at negative index!");
			return false;
		}
		if (idx <= 1) {
			RecIntListElement n = new RecIntListElement(value, this);
			n.next = next;
			if (next != null) {
				next.prev = n;
			}
			next = n;
			if (idx == 0) {
				next.value = this.value;
				this.value = value;
			}
			return true;
		}
		if (next == null) {
			System.out.println("List is to short to insert at given index!");
			return false;
		}
		return next.insert(value, idx - 1);
	}

	public RecIntListElement getNext() {
		return next;
	}

	public long countThreshLess(int threshold) {
		if (next == null) {
			return value < threshold ? value : 0;
		}
		return value < threshold ? value + next.countThreshLess(threshold) : next.countThreshLess(threshold);
	}

	public long countThreshEqual(int threshold) {
		if (next == null) {
			return value == threshold ? value : 0;
		}
		return value == threshold ? value + next.countThreshEqual(threshold) : next.countThreshEqual(threshold);
	}

	public long countThreshGreater(int threshold) {
		if (next == null) {
			return value > threshold ? value : 0;
		}
		return value > threshold ? value + next.countThreshGreater(threshold) : next.countThreshGreater(threshold);
	}

	public void kinguinSort(boolean increasing) {
		// end of list
		if (next == null) {
			if (increasing) {
				if (value < prev.value) {
					prev.next = null;
					return;
				}
			}
			else {
				if (value > prev.value) {
					prev.next = null;
					return;
				}
			}
		}
		// in list
		if (increasing) {
			if (value < prev.value) {
				prev.next = next;
				next.prev = prev;
			}
		}
		else {
			if (value > prev.value) {
				prev.next = next;
				next.prev = prev;
			}
		}
		next.kinguinSort(increasing);
	}

	public RecIntListElement reverse() {
		if (this == null) {
			return null;
		}

		RecIntListElement temp = next;
		next = prev;
		prev = temp;

		if (prev == null) {
			return this;
		}
		return prev.reverse();
	}

	public static void zip(RecIntListElement e1, RecIntListElement e2) {
		if (e2 == null) {
			return;
		}
		if (e1 == null) {
			return;
		}
		e1.insert(e2.value, 1);
		if (e1.next.next == null) {
			e1.next.next = e2.next;
			e2.next.prev = e1.next;
			return;
		}
		zip(e1.next.next, e2.next);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		RecIntListElement tmp = this;
		do {
			sb.append(tmp.value).append(", ");
			tmp = tmp.next;
		} while (tmp != null);
		sb.setLength(sb.length() - 2);
		return sb.toString();
	}

	public String toConnectionString() {
		StringBuilder sb = new StringBuilder();
		RecIntListElement tmp = this;
		do {
			if (tmp.prev != null) {
				sb.append("<-");
			}
			sb.append(tmp.value);
			if (tmp.next != null) {
				sb.append("->");
			}
			tmp = tmp.next;
		} while (tmp != null);
		return sb.toString();
	}
}
