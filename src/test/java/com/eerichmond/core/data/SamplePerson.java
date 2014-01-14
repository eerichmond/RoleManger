package com.eerichmond.core.data;

import com.google.common.base.Objects;

class SamplePerson {

	private int id;
	
	public SamplePerson(int id) {
		this.id = id;
	}
	
	int getId() { return id; }

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null || !SamplePerson.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		
		return Objects.equal(((SamplePerson)obj).getId(), getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}
	
}
