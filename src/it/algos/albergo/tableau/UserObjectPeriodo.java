/**
 * Title:     UserObjectPeriodo
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-feb-2009
 */
package it.algos.albergo.tableau;

import it.algos.albergo.AlbergoLib;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;

import java.util.Date;
import java.util.HashMap;

/**
 * UserObject di una cella di Periodo nel grafo
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 25-feb-2009 ore 19.12.04
 */
class UserObjectPeriodo {
	
    private HashMap<String,Object> values = new HashMap<String,Object>();
    
    public static final String KEY_CLIENTE="cliente";
    public static final String KEY_AGENZIA="agenzia";
    public static final String KEY_NOME_CAMERA="camera";
    public static final String KEY_PROVENIENZA="camProvenienza";
    public static final String KEY_DESTINAZIONE="camDestinazione";
    public static final String KEY_DATAINIZIO="dataInizio";
    public static final String KEY_DATAFINE="dataFine";
    public static final String KEY_OPZIONE="opzione";
    public static final String KEY_CONFERMATA="confermata";
    public static final String KEY_DATASCADENZA="dataScadenza";
    public static final String KEY_ARRIVATO="arrivato";
    public static final String KEY_PARTITO="partito";
    public static final String KEY_NUMAD="numAd";
    public static final String KEY_NUMBA="numBa";
    public static final String KEY_TRATTAMENTO="trattamento";
    public static final String KEY_PREPARAZIONE="preparazione";
    public static final String KEY_CODPERIODO="codPeriodo";
    public static final String KEY_IDSORGENTE="isSorgente";
    public static final String KEY_NOTE="note";


    private int idRisorsa;
    
    /**
     * Costruttore
     * @param idRisorsa l'id della risorsa rappresentata
     */
    public UserObjectPeriodo(int idRisorsa) {
		super();
		this.idRisorsa = idRisorsa;
	}


	public int getIdRisorsa() {
		return idRisorsa;
	}


	/**
     * Ritorna una stringa con nome del cliente e tra parentesi nome agenzia.
     * <p/>
     *
     * @return la stringa cliente (agenzia)
     */
    public String getClienteAgenzia() {
        String text = this.getCliente();
        if (Lib.Testo.isValida(this.getAgenzia())) {
            text += " (" + this.getAgenzia() + ")";
        }// fine del blocco if
        return text;
    }

    

    private String getCliente() {
    	return getString(KEY_CLIENTE);
    }


    private String getAgenzia() {
    	return getString(KEY_AGENZIA);
    }

    public boolean isOpzione() {
    	return getBool(KEY_OPZIONE);
    }

    private boolean isConfermata() {
    	return getBool(KEY_CONFERMATA);
    }

    public Date getDataScadenza() {
    	return getDate(KEY_DATASCADENZA);
    }

    private boolean isArrivato() {
    	return getBool(KEY_ARRIVATO);
    }

    private boolean isPartito() {
    	return getBool(KEY_PARTITO);
    }
    
    public String getTrattamento() {
    	return getString(KEY_TRATTAMENTO);
    }

    public String getPreparazione() {
    	return getString(KEY_PREPARAZIONE);
    }

    public int getCodPeriodo() {
    	return getInt(KEY_CODPERIODO);
    }

    public boolean isPresente() {
        /* variabili e costanti locali di lavoro */
        boolean presente, arrivato, partito;

        arrivato = this.isArrivato();
        partito = this.isPartito();
        presente = (arrivato && !partito);

        /* valore di ritorno */
        return presente;
    }


    /**
     * Determina se il periodo appartiene a una prenotazione scaduta.
     * <p/>
     * @return true se appartiene a una prenotazione scaduta
     */
    public boolean isPrenScaduta() {
        /* variabili e costanti locali di lavoro */
        boolean scaduta = false;
        Date oggi, scadenza;

        try {    // prova ad eseguire il codice
            if (!this.isPartito()) {
                if (!this.isPresente()) {
                    if (!this.isConfermata()) {
                        if (!this.isOpzione()) {
                            oggi = AlbergoLib.getDataProgramma();
                            scadenza = this.getDataScadenza();
                            if (Lib.Data.isPrecedente(oggi, scadenza)) {
                                scaduta = true;
                            }// fine del blocco if
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return scaduta;
    }




    public void put(String key, Object value){
    	values.put(key, value);
    }
    
    public Object get(String key){
    	return values.get(key);
    }
    
    public String getString(String key){
    	return Libreria.getString(values.get(key));
    }
    
    public boolean getBool(String key){
    	return Libreria.getBool(values.get(key));
    }

    public Date getDate(String key){
    	return Libreria.getDate(values.get(key));
    }
    
    public int getInt(String key){
    	return Libreria.getInt(values.get(key));
    }

    public HashMap<String,Object> getValues(){
    	return values;
    }



}// fine della classe
