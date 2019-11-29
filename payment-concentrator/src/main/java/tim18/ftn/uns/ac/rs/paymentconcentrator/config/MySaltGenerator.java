package tim18.ftn.uns.ac.rs.paymentconcentrator.config;

import org.jasypt.salt.SaltGenerator;

public class MySaltGenerator implements SaltGenerator {

	@Override
	public byte[] generateSalt(int lengthBytes) {
		String str = "sifra123"; // Zatrazena duzina je uvek 8, tako da ovaj string mora da ima 8 karaktera
		return str.getBytes();
	}

	@Override
	public boolean includePlainSaltInEncryptionResults() {
		return false;
	}

}
