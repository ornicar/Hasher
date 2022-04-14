package test.roundeights.hasher

import org.specs2.mutable._

import java.io.ByteArrayInputStream
import java.io.StringReader
import scala.io.Source

class ImplicitTest extends Specification {

    val data = TestData.test

    "Implicit converstion to a Hasher" should {

        import com.roundeights.hasher.Implicits._
        import com.roundeights.hasher.Hasher

        "work for Strings" in {
            val value: Hasher = data.str
            ok
        }
        "work for StringBuilders" in {
            val value: Hasher = data.builder
            ok
        }
        "work for Byte Arrays" in {
            val value: Hasher = data.bytes
            ok
        }
        "work for InputStreams" in {
            val value: Hasher = data.stream
            ok
        }
        "work for Readers" in {
            val value: Hasher = data.reader
            ok
        }
        "work for Sources" in {
            val value: Hasher = data.source
            ok
        }
    }

    "The implicit hashing methods" should {

        import com.roundeights.hasher.Implicits._

        "MD5 hash" in {
            data.str.md5.hex must_== data.md5ed
        }
        "SHA-1 hash" in {
            data.str.sha1.hex must_== data.sha1ed
        }
        "SHA-256 hash" in {
            data.str.sha256.hex must_== data.sha256ed
        }
        "SHA-384 hash" in {
            data.str.sha384.hex must_== data.sha384ed
        }
        "SHA-512 hash" in {
            data.str.sha512.hex must_== data.sha512ed
        }
        "HMAC-MD5 hash" in {
            data.str.hmac("secret").md5.hex must_== data.hmacMd5ed
        }
        "HMAC-SHA1 hash" in {
            data.str.hmac("secret").sha1.hex must_== data.hmacSha1ed
        }
        "HMAC-SHA256 hash" in {
            data.str.hmac("secret").sha256.hex must_== data.hmacSha256ed
        }
        "CRC32 hash" in {
            data.str.crc32.hex must_== data.crc32ed
        }
        "PBKDF2 hash" in {
            data.str.pbkdf2("secret", 1000, 128).hex must_== data.pbkdf2ed.get
        }
        "BCrypt hash" in {
            data.str.bcrypt.hex must beMatching("^[a-zA-Z0-9]{120}$")
        }
        "BCrypt hash with a round count" in {
            data.str.bcrypt(12).hex must beMatching("^[a-zA-Z0-9]{120}$")
        }
    }

    "The implicit compare methods" should {

        import com.roundeights.hasher.Implicits._

        "be comparable to an MD5 Hash" in {
            (data.str.md5 hash_= data.md5ed) must beTrue
            (data.str.md5 hash_= "AHashThatIsWrong") must beFalse
            (data.str.md5 hash_= "SomeHashThatIsWrong") must beFalse
            (data.str.md5 hash_= "") must beFalse
            (data.str.md5 hash_= ("other".md5)) must beFalse
        }

        "be comparable to a SHA1 Hash" in {
            (data.str.sha1 hash_= data.sha1ed) must beTrue
            (data.str.sha1 hash_= "AHashThatIsWrong") must beFalse
            (data.str.sha1 hash_= "SomeHashThatIsWrong") must beFalse
            (data.str.sha1 hash_= "") must beFalse
            (data.str.sha1 hash_= ("other".sha1)) must beFalse
        }

        "be comparable to a SHA256 Hash" in {
            (data.str.sha256 hash_= data.sha256ed) must beTrue
            (data.str.sha256 hash_= "AHashThatIsWrong") must beFalse
            (data.str.sha256 hash_= "SomeHashThatIsWrong") must beFalse
            (data.str.sha256 hash_= "") must beFalse
            (data.str.sha256 hash_= ("other".sha256)) must beFalse
        }

        "be comparable to a SHA384 Hash" in {
            (data.str.sha384 hash_= data.sha384ed) must beTrue
            (data.str.sha384 hash_= "AHashThatIsWrong") must beFalse
            (data.str.sha384 hash_= "SomeHashThatIsWrong") must beFalse
            (data.str.sha384 hash_= "") must beFalse
            (data.str.sha384 hash_= ("other".sha384)) must beFalse
        }

        "be comparable to a SHA512 Hash" in {
            (data.str.sha512 hash_= data.sha512ed) must beTrue
            (data.str.sha512 hash_= "AHashThatIsWrong") must beFalse
            (data.str.sha512 hash_= "SomeHashThatIsWrong") must beFalse
            (data.str.sha512 hash_= "") must beFalse
            (data.str.sha512 hash_= ("other".sha512)) must beFalse
        }

        "be comparable to a PBKDF2 Hash" in {
            (data.str.pbkdf2("secret", 1000, 128) hash_=
                data.pbkdf2ed.get) must beTrue
            (data.str.pbkdf2("secret", 1000, 128) hash_=
                "AHashThatIsWrong") must beFalse
            (data.str.pbkdf2("secret", 1000, 128) hash_=
                "SomeHashThatIsWrong") must beFalse
            (data.str.pbkdf2("secret", 1000, 128) hash_= "") must beFalse
            (data.str.pbkdf2("secret", 1000, 128) hash_=
                ("other".pbkdf2("secret", 1000, 128))) must beFalse
        }

        "be comparable to a CRC32 Hash" in {
            (data.str.crc32 hash_= data.crc32ed) must beTrue
            (data.str.crc32 hash_= "AHashThatIsWrong") must beFalse
            (data.str.crc32 hash_= "SomeHashThatIsWrong") must beFalse
            (data.str.crc32 hash_= "") must beFalse
            (data.str.crc32 hash_= ("other".crc32)) must beFalse
        }

        "be comparable to a BCrypt Hash" in {
            (data.str.bcrypt hash_= data.bcrypted) must beTrue
            (data.str.bcrypt hash_= "AHashThatIsWrong") must beFalse
            (data.str.bcrypt hash_= "SomeHashThatIsWrong") must beFalse
            (data.str.bcrypt hash_= "") must beFalse
            (data.str.bcrypt hash_= ("other".bcrypt)) must beFalse
        }

        "be comparable to a BCrypt Hash with a round count" in {
            (data.str.bcrypt(12) hash_= data.bcrypted12) must beTrue
            (data.str.bcrypt(12) hash_= "AHashThatIsWrong") must beFalse
            (data.str.bcrypt(12) hash_= "SomeHashThatIsWrong") must beFalse
            (data.str.bcrypt(12) hash_= "") must beFalse
            (data.str.bcrypt(12) hash_= ("other".bcrypt(12))) must beFalse
        }
    }

    "The implicit salt method" should {

        import com.roundeights.hasher.Implicits._

        val str: String = "test"
        val md5ed = "d615489ad65aad3f6138728a02221e95"

        "change the hash" in {
            str.salt("one").salt("two").md5.hex must_== md5ed
            (str.salt("one").salt("two").md5 hash_= md5ed) must beTrue
        }
    }

}

