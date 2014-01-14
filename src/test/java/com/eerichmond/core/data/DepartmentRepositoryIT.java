package com.eerichmond.core.data;

import com.eerichmond.core.domain.Department;
import com.eerichmond.core.utils.JsonMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import javax.inject.Inject;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DepartmentRepositoryIT extends BaseIntegrationTest {

	@Inject
	DepartmentRepository repository;

	@Test
	public void findByNameLike_Gryffin_returnGraduateStudies() throws Exception {
		List<Department> departments = repository.findByNameLike("Gryffin%");

		assertThat(departments.size(), is(1));
		assertThat(departments.get(0).getName(), is("Gryffindor House"));
	}

	@Test
	public void findByDeptCode_S_returnsSlytherin() {
		Department department = repository.findByDeptCode("S");

		assertThat(department.getName(), is("Slytherin House"));
	}

	@Test
	public void jsonSerialize_serializesNameAndCode() throws IOException {
		Department department = repository.findByDeptCode("S");

		StringWriter writer = new StringWriter();

		new JsonMapper().writeValue(writer, department);

		String json = writer.toString();

		assertThat(JsonPath.<String>read(json, "$.name"), is("Slytherin House"));
		assertThat(JsonPath.<String>read(json, "$.deptCode"), is("S"));
	}
}
