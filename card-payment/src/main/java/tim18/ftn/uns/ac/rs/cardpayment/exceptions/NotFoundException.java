package tim18.ftn.uns.ac.rs.cardpayment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {
	private static final long serialVersionUID = 4885186187823425301L;
	private Integer id;
	private String className;
	private String name;
	
	public NotFoundException(Integer id, String className) {
		super(className + " with id " + id.toString() + " not found.");
		this.id = id;
		this.className = className;
	}
	
	public NotFoundException(String name, String className) {
		super(className + " with name " + name + " not found.");
		this.name = name;
		this.className = className;
	}
	
	public String getFaultInfo() { 
		return super.getMessage();
	}

}
