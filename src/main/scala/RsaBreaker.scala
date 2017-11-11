/**
  * You will definitely need to do some work in this file.
  */

trait RsaBreaker {
	def recoverKey(publicKey: RsaPublicKey): RsaKey
	def bruteForceDecrypt(m: String, publicKey: RsaPublicKey): String
}

object RsaBreaker extends RsaBreaker {
	// This presumes n = p * q
	override def recoverKey(publicKey: RsaPublicKey): RsaKey = {
		throw new NotImplementedError("You must implement recoverKey(publicKey) yourself")
	}

	// Call this with the RsaPublicKey() that you create and the message m you have
	override def bruteForceDecrypt(m: String, publicKey: RsaPublicKey): String = RsaCryptosystem.decrypt(m, recoverKey(publicKey))

	private def factor(n: Int): Seq[Int] = {
		throw new NotImplementedError("You must implement factor(n) yourself")
	}
}
