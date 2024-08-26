package org.unibl.etf.mdp.library.helpers;

public class StringUtils {
	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.trim().isBlank() || str.trim().isEmpty())
			return true;
		return false;
	}
}
