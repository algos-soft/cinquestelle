package it.algos.albergo.tableau.test;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.modello.ModelloAlgos;
import it.algos.base.wrapper.SetValori;

public class TableauTestModello extends ModelloAlgos {
	
	private static final String NOME_TAVOLA = "testtableau";
	
    /**
     * Costruttore completo senza parametri.
     */
    public TableauTestModello() {
        super();
        super.setTavolaArchivio(NOME_TAVOLA);
    }// fine del metodo costruttore

    /**
     * Creazione dei campi.
     */
    protected void creaCampi() {
    	
        super.creaCampi();

        /* campo sigla  */
        Campo unCampo = CampoFactory.testo("sigla");
        this.addCampo(unCampo);
        
    }
    
	@Override
	protected boolean nuovoRecordAnte(SetValori set, Connessione conn) {
		
		TableauTestModulo mod = (TableauTestModulo)getModulo();
		mod.creaTableau();

		return super.nuovoRecordAnte(set, conn);
		
	}


}
