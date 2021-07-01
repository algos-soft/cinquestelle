package it.algos.base.aggiornamento;

/**
 * - @todo Manca la descrizione..
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 10-apr-2008 ore  08:12
 */
public final class FileWrap {

    /**
     * variabile di tipo testo
     * nome breve del file
     */
    private String nome;

    /**
     * variabile di tipo intero
     * dimensioni del file
     */
    private int dimensioni;


    /**
     * Costruttore senza parametri.
     */
    public FileWrap() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     *
     * @param nome - nome breve del file
     * @param dimensioni - dimensioni del file
     */
    public FileWrap(String nome, int dimensioni) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setNome(nome);
        this.setDimensioni(dimensioni);
    }// fine del metodo costruttore completo


    /**
     * metodo getter
     *
     * @return nome
     */
    public String getNome() {
        return nome;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param nome - nome breve del file
     */
    private void setNome(String nome) {
        this.nome = nome;
    } // fine del metodo setter


    /**
     * metodo getter
     *
     * @return dimensioni
     */
    public int getDimensioni() {
        return dimensioni;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param dimensioni - dimensioni del file
     */
    private void setDimensioni(int dimensioni) {
        this.dimensioni = dimensioni;
    } // fine del metodo setter
}// fine della classe