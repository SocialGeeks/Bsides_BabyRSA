/**
  * A real cryptosystem doesn't look like this.
  * This is not a real RSA implementation.
  * But it's sure fun anyway. :)
  */
trait RsaCryptosystem {
	def generateKey(p: Int, q: Int): RsaKey
	def generateRandomKey: RsaKey

	def encrypt(m: Int, e: Int, n: Int): Int
	def decrypt(m: Int, d: Int, n: Int): Int

	def encrypt(m: Seq[Int], e: Int, n: Int): Seq[Int]
	def decrypt(m: Seq[Int], d: Int, n: Int): Seq[Int]

	def encrypt(m: String, e: Int, n: Int): String
	def decrypt(m: String, d: Int, n: Int): String

	def encrypt(m: String, key: RsaKey): String
	def decrypt(m: String, key: RsaKey): String
}

object RsaCryptosystem extends RsaCryptosystem {
	private final val messageStringJoinVal = " "

	// The best prime number between 16 and 18
	final val e = 17

	// To keep things sane
	final val primeMax = 256

	// Generate an RSA key using given primes
	override def generateKey(p: Int, q: Int): RsaKey = {
		val n = p * q

		val phiN = MathHelper.eulerTotient(p, q)

		// Secret exponent d where e * d is congruent to 1 mod phi(n)
		// This could fail (on the .get if it's None) cause reasons if things aren't just so (rare but sometimes)
		val d = MathHelper.modularInverse(RsaCryptosystem.e, phiN).get

		RsaKey(RsaPublicKey(n, RsaCryptosystem.e), d, (p, q), phiN)
	}

	// Generate an RSA key using random primes
	override def generateRandomKey: RsaKey = {
		val primes = MathHelper.generatePrimes(2, RsaCryptosystem.primeMax)

		generateKey(primes.head, primes.tail.head)
	}

	// Encryption and decryption are the same!
	override def encrypt(m: Int, e: Int, n: Int): Int = BigInt(m).pow(e).mod(n).toInt
	override def decrypt(m: Int, d: Int, n: Int): Int = encrypt(m, d, n)

	override def encrypt(m: Seq[Int], e: Int, n: Int): Seq[Int] = for (x <- m) yield encrypt(x, e, n)
	override def decrypt(m: Seq[Int], d: Int, n: Int): Seq[Int] = for (x <- m) yield decrypt(x, d, n)

	override def encrypt(m: String, e: Int, n: Int): String = encrypt(m.toCharArray.map(_.toInt), e, n).mkString(messageStringJoinVal)
	override def decrypt(m: String, d: Int, n: Int): String = decrypt(m.split(messageStringJoinVal).map(_.toInt), d, n).map(_.toChar).mkString

	override def encrypt(m: String, key: RsaKey): String = encrypt(m, key.publicKey.e, key.publicKey.n)
	override def decrypt(m: String, key: RsaKey): String = decrypt(m, key.privateKey, key.publicKey.n)
}
