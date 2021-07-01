package it.algos.base.wrapper;

import it.algos.base.libreria.Lib;

import java.util.ArrayList;

/**
 * Wrapper per i messaggi di errore concatenati
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 4-apr-2008 ore  19:03
 */
public final class WrapErrori {

    /**
     * Lista delle stringhe di errore
     */
    private ArrayList<String> lista = new ArrayList<String>();

    /**
     * flag - true se c'è un errore
     */
    private boolean valido=true;


    /**
     * Costruttore senza parametri.
     */
    public WrapErrori() {
        /* rimanda al costruttore della superclasse */
        this(new ArrayList<String>(), true);
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     *
     * @param lista  - lista di stringhe di errore
     * @param valido - booleano di errore
     */
    public WrapErrori(ArrayList<String> lista, boolean valido) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setLista(lista);
        this.setValido(valido);

    }// fine del metodo costruttore completo


    /**
     * Aggiunge una riga di errore.
     * <p/>
     * @param stringa di errore
     */
    public void add(String stringa) {
        this.getLista().add(stringa);
        this.setValido(false);
    }

    /**
     * Restituisce il testo di errore concatenato.
     * <p/>
     * @return stringa di errore
     */
    public String getTesto() {
        return Lib.Testo.concatReturn(this.getLista().toArray(new String[0]));
    }


    public ArrayList<String> getLista() {
        return lista;
    }


    public void setLista(ArrayList<String> lista) {
        this.lista = lista;
    }


    public boolean isValido() {
        return valido;
    }


    public void setValido(boolean valido) {
        this.valido = valido;
    }


}// fine della classe