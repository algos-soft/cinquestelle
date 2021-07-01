package it.algos.base.modulo;

public interface OnEditingFinished {
	
	/**
	 * method notified when editing is finished (the form is closed)
	 * @param module - the edited module
	 * @param id - the id of the record
	 * @param confirmed - true if confirmed, false if discarded
	 */
	public void onEditingFinished(Modulo modulo, int id, boolean confirmed);

}
