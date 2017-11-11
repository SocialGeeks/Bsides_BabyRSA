case class RsaPublicKey(n: Int, e: Int)
case class RsaKey(publicKey: RsaPublicKey, privateKey: Int, primes: (Int, Int), eulerTotient: Int) {
	override def toString: String =
		s"""
		  |---------- Public ----------
		  |     n: ${publicKey.n}
		  |     e: ${publicKey.e}
		  |---------- Private ---------
		  |     d: $privateKey
		  |
		  |     p: ${primes._1}
		  |     q: ${primes._2}
		  |
		  |phi(n): $eulerTotient
		  |----------------------------
		""".stripMargin
}