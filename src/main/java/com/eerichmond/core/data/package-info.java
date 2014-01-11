@TypeDefs({
	@TypeDef(name="PersistentDateTime", defaultForType=DateTime.class, typeClass=org.jadira.usertype.dateandtime.joda.PersistentDateTime.class),
	@TypeDef(name="ControlCodeEnumUserType", typeClass=ControlCodeEnumUserType.class)
})

package com.eerichmond.core.data;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.joda.time.DateTime;