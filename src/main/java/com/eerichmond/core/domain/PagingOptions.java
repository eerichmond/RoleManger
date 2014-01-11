package com.eerichmond.core.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PagingOptions extends BaseObject<BusinessKey> {

	public static final Integer DEFAULT_START_ROW = 0;
	public static final Integer DEFAULT_START_PAGE = 0;
	public static final Integer DEFAULT_ROWS_PER_PAGE = 20;
	
	private static final long serialVersionUID = 1L;
	private Integer startRow = DEFAULT_START_ROW;
	private Integer rowsPerPage = DEFAULT_ROWS_PER_PAGE;
	private Integer totalRows = 0;
	private String sortProperty;
	private Direction sortDir = Direction.ASC;
	private int sequenceNumber = 1;

	public PagingOptions() { super(); }

	/**
	 * Constructor
	 * @param rowsPerPage the number of rows per page (default = 20)
	 */
	public PagingOptions(Integer rowsPerPage) {
		this(0, rowsPerPage);
	}

	/**
	 * Constructor
	 * @param startRow the starting row (0 based)
	 * @param rowsPerPage the number of rows per page (default = 20)
	 */
	public PagingOptions(Integer startRow, Integer rowsPerPage) {
		super();
		
		this.startRow = startRow;
		this.rowsPerPage = rowsPerPage;
	}

	@BusinessKey
	public Integer getStartRow() { return startRow; }
	public void setStartRow(Integer startRow) {
		this.startRow = startRow != null && startRow > -1 ? startRow : this.startRow;
	}
	
	@BusinessKey
	public Integer getRowsPerPage() { return rowsPerPage; }
	public void setRowsPerPage(Integer rowsPerPage) {
		this.rowsPerPage = rowsPerPage != null && rowsPerPage > 0 ? rowsPerPage : this.rowsPerPage;
	}
	
	/**
	 * Returns the total number of rows returned by the search. This field is only non-zero after the first page. It
	 * is used for performance reasons to prevent re-querying for the total rows if the search criteria hasn't changed.
	 */
	public Integer getTotalRows() { return totalRows; }
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows != null && totalRows >= 0 ? totalRows : this.totalRows;
	}
	
	public Integer getEndRow() {
		return getStartRow() + getRowsPerPage();
	}
	
	public int getPageNumber() {
		return (int) Math.floor(getStartRow()/getRowsPerPage());
	}
	
	@BusinessKey
	public String getSortProperty() { return sortProperty; }
	public PagingOptions setSortProperty(String sortPropety) { this.sortProperty = sortPropety; return this; }
	
	@BusinessKey
	public Direction getSortDir() { return sortDir; }
	public PagingOptions setSortDir(Direction sortDir) {
		this.sortDir = sortDir;
		return this;
	}
	
	public Sort getSortOrder() {
		if (getSortDir() != null && StringUtils.isNotBlank(getSortProperty())) {
			return new Sort(new Sort.Order(getSortDir(), getSortProperty()));
		}
		
		return null;
	}
	
	public Pageable toPageable() {
		return new PageRequest(getPageNumber(), getRowsPerPage(), getSortOrder());
	}
	
	@BusinessKey
	public Integer getSequenceNumber() { return sequenceNumber; }
	public PagingOptions setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber != null && sequenceNumber > 0 && sequenceNumber < 1000 ? sequenceNumber : 1;
		return this;
	}

}
