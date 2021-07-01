package it.algos.albergo.stampeobbligatorie.notifica;

import java.util.ArrayList;

/**
 * Wrapper rapresentante un gruppo arrivato
 */
public class WrapGruppoArrivato {
	
	private int codCapo;
	private int codPeriodo;
	private int idRecordNotifica;
	private ArrayList<Integer> codMembri;

	public WrapGruppoArrivato(int codCapo, int codPeriodo, int idRecordNotifica) {
		super();
		this.codCapo = codCapo;
		this.codPeriodo = codPeriodo;
		this.idRecordNotifica=idRecordNotifica;
		codMembri = new ArrayList<Integer>();
	}

	public void addMembro(int cod) {
		codMembri.add(cod);
	}
	
	/**
	 * @return true se c'è solo il capogruppo
	 */
	public boolean isSingolo(){
		return (codMembri.size()==0);
	}
	
	public int getCodCapo() {
		return codCapo;
	}
	
	public int getCodPeriodo() {
		return codPeriodo;
	}

	public int[] getCodMembri() {
		int[] codici = new int[codMembri.size()];
		for (int i = 0; i < codMembri.size(); i++) {
			codici[i] =codMembri.get(i);
		}
		return codici;
	}


	@Override
	public String toString() {
		return codCapo+" "+codMembri.toString();
	}
	
	/**
	 * @return il numero di persone totali compreso il capogruppo
	 */
	public int getPersoneTotali(){
		return codMembri.size()+1;
	}

	public int getIdRecordNotifica() {
		return idRecordNotifica;
	}
	
	

}
