package tim18.ftn.uns.ac.rs.paypalpayment.config;
import javax.persistence.AttributeConverter;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

public class StringConverter implements AttributeConverter<String, String> {

	@Autowired
	private StringEncryptor stringEncryptor;
	
	@Override
	public String convertToDatabaseColumn(String attribute) {
		return stringEncryptor.encrypt(attribute);
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		return stringEncryptor.decrypt(dbData);
	}

}