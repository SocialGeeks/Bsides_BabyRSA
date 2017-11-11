import scala.collection.mutable.ArrayBuffer
import scala.util.Random

object MathHelper {
	/**
	  * Find the multiplicative inverse of a number a over the integers modulo n
	  * See:
	  * https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm#Modular_integers
	  * https://en.wikipedia.org/wiki/RSA_(cryptosystem)#Code
	  */
	def modularInverse(a: Int, n: Int): Option[Int] = {
		var t = 0
		var newT = 1
		var r = n
		var newR = a % n

		while (newR != 0) {
			val quotient = r / newR

			val thisT = t
			t = newT
			newT = thisT - quotient * newT

			val thisR = r
			r = newR
			newR = thisR - quotient * newR
		}

		if (r > 1) return None // a is not invertible

		if (t < 0) t += n

		Some(t)
	}

	// Euler's totient for n = pq, primes p and q
	// https://en.wikipedia.org/wiki/RSA_(cryptosystem)#Key_generation
	// https://en.wikipedia.org/wiki/Euler%27s_totient_function
	def eulerTotient(p: Int, q: Int) = (p - 1) * (q - 1)

	// These algorithms are not guaranteed to succeed
	def generatePrimes(num: Int, max: Int): Seq[Int] = {
		val primes = new ArrayBuffer[Int](num)

		for (i <- 1 to num) {
			var testPrime = 0

			do {
				testPrime = Random.nextInt(max)
			} while (!isPrime(testPrime))

			primes += testPrime
		}

		primes
	}
	def generatePrime(max: Int) = generatePrimes(1, max).head

	def isPrime(n: Int): Boolean = {
		if (n < 2) return false

		if (n == 2) return true

		if (n % 2 == 0) return false

		for (i <- 3 to ceilRoot(n) by 2) if (n % i == 0) return false

		true
	}

	def ceilRoot(n: Int): Int = Math.ceil(Math.sqrt(n.toDouble)).toInt
}
