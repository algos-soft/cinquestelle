package it.algos.base.wrapper;

/**
 * - @todo Manca la descrizione..
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 6-mar-2008 ore  07:54
 */
public final class CinqueStringhe {

    /**
     * variabile di tipo QuattroStringhe
     * le prime quattro stringhe
     */
    private QuattroStringhe primeQuattro;

    /**
     * variabile di tipo testo
     * quinta stringa
     */
    private String quinta;


    /**
     * Costruttore senza parametri.
     */
    public CinqueStringhe() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con una stringa <br>
     *
     * @param precedente stringa
     */
    public CinqueStringhe(String precedente) {
        /* invoca il metodo sovrascritto della classe */
        this(precedente, "", "", "", "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con due stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public CinqueStringhe(DueStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(), precedenti.getSeconda(), "", "", "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con tre stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public CinqueStringhe(TreStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(), precedenti.getSeconda(), precedenti.getTerza(), "", "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con quattro stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public CinqueStringhe(QuattroStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     *
     * @param prima - prima stringa
     * @param seconda - seconda stringa
     * @param terza - terza stringa
     * @param quarta - quarta stringa
     * @param quinta - quinta stringa
     */
    public CinqueStringhe(String prima,
                          String seconda,
                          String terza,
                          String quarta,
                          String quinta) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        QuattroStringhe primeQuattro;
        primeQuattro = new QuattroStringhe(prima, seconda, terza, quarta);

        this.setPrecedenti(primeQuattro);
        this.setQuinta(quinta);
    }// fine del metodo costruttore completo


    /**
     * metodo getter
     *
     * @return primeQuattro
     */
    private QuattroStringhe getPrecedenti() {
        return primeQuattro;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param primeQuattro stringhe
     */
    private void setPrecedenti(QuattroStringhe primeQuattro) {
        this.primeQuattro = primeQuattro;
    } // fine del metodo setter


    /**
     * metodo getter
     *
     * @return due stringhe
     */
    public DueStringhe getDue() {
        return this.getPrecedenti().getDue();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return tre stringhe
     */
    public TreStringhe getTre() {
        return this.getPrecedenti().getTre();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return quattro stringhe
     */
    public QuattroStringhe getQuattro() {
        return this.getPrecedenti();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return prima
     */
    public String getPrima() {
        return this.getPrecedenti().getPrima();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return seconda
     */
    public String getSeconda() {
        return this.getPrecedenti().getSeconda();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return terza
     */
    public String getTerza() {
        return this.getPrecedenti().getTerza();
    } // fine del metodo getter


    /**
     * /**
     * metodo getter
     *
     * @return quarta
     */
    public String getQuarta() {
        return this.getPrecedenti().getQuarta();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return quinta
     */
    public String getQuinta() {
        return this.quinta;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param prima - prima stringa
     */
    public void setPrima(String prima) {
        this.getPrecedenti().setPrima(prima);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param seconda - seconda stringa
     */
    public void setSeconda(String seconda) {
        this.getPrecedenti().setSeconda(seconda);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param terza - terza stringa
     */
    public void setTerza(String terza) {
        this.getPrecedenti().setTerza(terza);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param quarta - quarta stringa
     */
    public void setQuarta(String quarta) {
        this.getPrecedenti().setQuarta(quarta);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param quinta - quinta stringa
     */
    public void setQuinta(String quinta) {
        this.quinta = quinta;
    } // fine del metodo setter


}// fine della classe