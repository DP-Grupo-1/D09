
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Access(AccessType.PROPERTY)
//@Table(uniqueConstraints = {
//	@UniqueConstraint(columnNames = {
//		"userAccount_id"
//	})
//})
public class Administrator extends Actor {

}
