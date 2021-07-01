package it.algos.base.wrapper;

public interface Campi {

    public final static String legendaSigla = "sigla come appare nelle liste di altri moduli";

    public final static String legendaDescrizione = "descrizione completa della tabella";


    /**
     * Restituisce il nome interno del campo.
     * <p/>
     *
     * @return il nome interno del campo
     */
    public abstract String get();


    public abstract String getTitoloColonna();


    public abstract String getEtichettaScheda();


    public abstract String getLegenda();


    public abstract boolean isVisibileLista();

}
