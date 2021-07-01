package it.algos.albergo.conto;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;


public class ContoPageable implements Pageable{

	private ContoPrintable printable;

	public ContoPageable(ContoPrintable printable) {
		super();
		this.printable = printable;
	}

	public int getNumberOfPages() {
		return printable.getQuantePagine();
	}

	public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException {
		return printable.getImpostazione().getPagina().getPageFormat();
	}

	public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException {
		return printable;
	}

}
