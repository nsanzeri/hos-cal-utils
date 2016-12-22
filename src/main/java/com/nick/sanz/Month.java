package com.nick.sanz;

import java.text.DateFormatSymbols;

public enum Month {
	Jan(1), Feb(2), Mar(3), Apr(4), May(5), Jun(6), Jul(7), Aug(8), Sep(9), Oct(10), Nov(11), Dec(12);

	private static DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
	private static final int[] LAST_DAY_OF_MONTH = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public int index;

	Month(int index) {
		this.index = index;
	}

	public static Month make(int monthIndex) {
		for (Month m : Month.values()) {
			if (m.index == monthIndex)
				return m;
		}
		throw new IllegalArgumentException("Invalid month index " + monthIndex);
	}

	public int lastDay() {
		return LAST_DAY_OF_MONTH[index];
	}

	public int quarter() {
		return 1 + (index - 1) / 3;
	}

	public String toString() {
		return dateFormatSymbols.getMonths()[index - 1];
	}

	public String toShortString() {
		return dateFormatSymbols.getShortMonths()[index - 1];
	}

	public static Month parse(String s) {
		s = s.trim();
		for (Month m : Month.values())
			if (m.matches(s))
				return m;

		try {
			return make(Integer.parseInt(s));
		} catch (NumberFormatException e) {
		}
		throw new IllegalArgumentException("Invalid month " + s);
	}

	private boolean matches(String s) {
		return s.equalsIgnoreCase(toString()) || s.equalsIgnoreCase(toShortString());
	}
}
