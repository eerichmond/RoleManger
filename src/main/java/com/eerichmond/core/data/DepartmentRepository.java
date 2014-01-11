package com.eerichmond.core.data;

import com.eerichmond.core.domain.Department;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, Long> {

	Department findByDeptCode(String deptCode);

	/**
	 * Returns a list of departments using a case-insensitive like. The name passed in must contain the wildcards for
	 * the like to work.
	 * @param name the part of the name to search for, including the wildcards (eg "%Graduate%")
	 * @return a list of departments matching the name.
	 */
	@Query("from Department where nameSearchable like upper(:name)")
	List<Department> findByNameLike(@Param("name") String name);

}
