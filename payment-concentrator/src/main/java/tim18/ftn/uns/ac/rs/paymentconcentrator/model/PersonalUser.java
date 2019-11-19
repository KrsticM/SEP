package tim18.ftn.uns.ac.rs.paymentconcentrator.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//Database annotations
@Entity
@DiscriminatorValue("personal")
public class PersonalUser extends User {

}
