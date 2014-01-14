package com.eerichmond.core.domain;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PagingOptionsTest {
	@Test
	public void setStartRow_2_returns2() throws Exception {
		PagingOptions pagingOptions = new PagingOptions();

		pagingOptions.setStartRow(2);

		assertThat(pagingOptions.getStartRow(), is(2));
	}

	@Test
	public void setStartRow_minus1_returns1() throws Exception {
		PagingOptions pagingOptions = new PagingOptions();

		pagingOptions.setStartRow(-1);

		assertThat(pagingOptions.getStartRow(), is(PagingOptions.DEFAULT_START_ROW));
	}

	@Test
	public void setRowsPerPage_2_returns2() throws Exception {
		PagingOptions pagingOptions = new PagingOptions();

		pagingOptions.setRowsPerPage(2);

		assertThat(pagingOptions.getRowsPerPage(), is(2));
	}

	@Test
	public void setRowsPerPage_minus1_returns1() throws Exception {
		PagingOptions pagingOptions = new PagingOptions();

		pagingOptions.setRowsPerPage(-1);

		assertThat(pagingOptions.getRowsPerPage(), is(PagingOptions.DEFAULT_ROWS_PER_PAGE));
	}

	/*
	 * Zero based pages
	 */
	@Test
	public void getPageNumber_startRow31rowsPerPage30_returns1() throws Exception {
		PagingOptions pagingOptions = new PagingOptions();

		pagingOptions.setStartRow(31);
		pagingOptions.setRowsPerPage(30);

		assertThat(pagingOptions.getPageNumber(), is(1));
	}

	@Test
	public void setSequenceNumber_2_returns2() throws Exception {
		PagingOptions pagingOptions = new PagingOptions();

		pagingOptions.setSequenceNumber(2);

		assertThat(pagingOptions.getSequenceNumber(), is(2));
	}

	@Test
	public void setSequenceNumber_minus1_returns1() throws Exception {
		PagingOptions pagingOptions = new PagingOptions();

		pagingOptions.setSequenceNumber(-1);

		assertThat(pagingOptions.getSequenceNumber(), is(1));
	}
}
