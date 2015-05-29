package com.some.vendor.api;

/**
 * This implementation of Secret uses an additive function
 * @author Ross
 *
 */
public class AdditiveSecret implements Secret {

	public int secret(int x) {
		return x;
	}
}
