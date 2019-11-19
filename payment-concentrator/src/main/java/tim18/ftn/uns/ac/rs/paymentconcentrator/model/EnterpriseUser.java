package tim18.ftn.uns.ac.rs.paymentconcentrator.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//Database annotations
@Entity
@DiscriminatorValue("enterprise")
public class EnterpriseUser extends User {

}
