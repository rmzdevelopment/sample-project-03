package com.some.vendor.api;

/**
 * This implementation of Secret uses a non-additive function
 * @author Ross
 *
 */
public class NonAdditiveSecret  implements Secret {

		public int secret(int x) {
			return x*x;
		}
}
