package com.test.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.some.vendor.api.AdditiveSecret;
import com.some.vendor.api.NonAdditiveSecret;
import com.some.vendor.api.Secret;

/**
 * This class is used for testing if specific implementations of the Secret.secret()
 * API is additive for all prime numbers in the range of [0,N) or not.
 * @author Ross
 *
 */
public class TestSecrets {
	public final static int DEFAULT_N = 57;
	
	public static void main(String [] args) {
		//get value of N from user
		System.out.println("N:");
		System.out.println("Please enter N as a positive integer value:");
		int maxRange = TestSecrets.DEFAULT_N;
		try {
			Scanner reader = new Scanner(System.in);
			maxRange = reader.nextInt();
			reader.close();
			
		} catch (Exception e) {
			System.out.println("Invalid number. Defaulting N to: " + TestSecrets.DEFAULT_N);
			maxRange = TestSecrets.DEFAULT_N;
		}

		//validate that N is integer greater than zero
		if(maxRange <= 0) {
			System.out.println("Non-positive number. Defaulting N to: " + TestSecrets.DEFAULT_N);
			maxRange = TestSecrets.DEFAULT_N;
		}
		
		//Add some extra blanks
		System.out.println("\nCreating instances of Secret with additive and non-additive versions of method secret()...\n");
		List<Secret> secrets = new ArrayList<Secret>();
		secrets.add(new AdditiveSecret());
		secrets.add(new NonAdditiveSecret());
		for(Secret secret : secrets) {
			System.out.println("Testing " + secret.getClass().getName()+":");
			if(TestSecrets.isAdditiveForSpecifiedPrimes(secret, maxRange)) {
				System.out.println("\t-> This implementation of the .secret() method is additive for the given range of primes.");
			} else {
				System.out.println("\t-> This implementation of the .secret() method is not additive for the given range of primes.");
			}
			System.out.println();
		}
	}
	
	/**
	 * This method confirms if a specific implementation of the Secret.secret()
	 * API is additive for all prime numbers in the range of [0,N) or not.
	 * 
	 * Assumptions"
	 * 1) Prime number values will be in the range of 0 to N-1
	 * 2) Combinations will only consist of sets of two
	 * 3) Values will not be duplicated for combinations
	 */
	public static boolean isAdditiveForSpecifiedPrimes(Secret s, int maxRange) {
		boolean isAdditiveMethod = false;
		
		Set<Integer> primeSet = TestSecrets.getPrimeSet(maxRange);
		if(!primeSet.isEmpty()) {
			System.out.println("\t-> Testing .secret() with the following set of prime numbers:\n\t   " + primeSet.toString());
			boolean isAdditiveForSet = true;
			
			//create outer loop label
			comboCheck:
			for(Integer i : primeSet) {
				for(Integer j : primeSet) {
					if(i.equals(j)) {
						continue; //skip duplicates
					}
					if(s.secret(i) + s.secret(j) != s.secret(i+j)) {
						System.out.println("\t-> .secret(i="+i+") + .secret(j="+j+") equals "+(s.secret(i) + s.secret(j)));
						System.out.println("\t-> .secret(i+j="+(i+j)+") equals " + (s.secret(i+j)));
						isAdditiveForSet = false;
						//C-C-C-COMBO BREAKER!
						break comboCheck; //break from outer loop
					}
				}
			}
			if(isAdditiveForSet) {
				System.out.println("\t-> The .secret method was additive with all paired combinations of the above primes.");
				isAdditiveMethod = true;
			}
		}
		
		return isAdditiveMethod;
	}
	
	/**
	 * Method to determine the set of all prime numbers from [0,N)
	 * @param maxRange - Non-inclusive max range (aka N)
	 * @return
	 */
	public static Set<Integer> getPrimeSet(int maxRange) {
		Set<Integer> primeSet = new HashSet<Integer>();
		Set<Integer> nonPrimes = new HashSet<Integer>();
		
		//By definition, a prime number is greater than one
		//Additionally, due to the wording "prime numbers less than N",
		//it is assumed that N (aka maxRange) is non-inclusive for the range.
		//Therefore, N must be at least 3 in order to get the lowest prime (2).
		if(maxRange > 2) {
			//Use the Sieve of Eratosthenes to find all prime numbers from 2 to N
			for(int i = 2; i < maxRange; i++) {
				if(!nonPrimes.contains(i)){
					primeSet.add(i);
					for(int multiplier = i; (i*multiplier) < maxRange; multiplier++){
						nonPrimes.add(i*multiplier);
					}
				}
			}
		}
		return primeSet;
	}
}
