package it.algos.base.wrapper;

/**
 * Wrapper di otto oggetti di tipo String.
 * </p>
 * <p/>
 * Si appoggia sull'oggetto di classe SetteStringhe <br>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 23-mar-2008 ore  09:22
 */
public final class OttoStringhe {

    /**
     * variabile di tipo SetteStringhe
     * le prime sette stringhe
     */
    private SetteStringhe primeSette;

    /**
     * variabile di tipo testo
     * ottava stringa
     */
    private String ottava;


    /**
     * Costruttore senza parametri.
     */
    public OttoStringhe() {
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
    public OttoStringhe(String precedente) {
        /* invoca il metodo sovrascritto della classe */
        this(precedente, "", "", "", "", "", "", "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con due stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public OttoStringhe(DueStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(), precedenti.getSeconda(), "", "", "", "", "", "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con tre stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public OttoStringhe(TreStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                "",
                "",
                "",
                "",
                "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con quattro stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public OttoStringhe(QuattroStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                "",
                "",
                "",
                "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con cinque stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public OttoStringhe(CinqueStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                precedenti.getQuinta(),
                "",
                "",
                "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con sei stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public OttoStringhe(SeiStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                precedenti.getQuinta(),
                precedenti.getSesta(),
                "",
                "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con sette stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public OttoStringhe(SetteStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                precedenti.getQuinta(),
                precedenti.getSesta(),
                precedenti.getSettima(),
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
     * @param sesta - sesta stringa
     * @param settima - settima stringa
     * @param ottava - ottava stringa
     */
    public OttoStringhe(String prima,
                        String seconda,
                        String terza,
                        String quarta,
                        String quinta,
                        String sesta,
                        String settima,
                        String ottava) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        SetteStringhe primeSette;
        primeSette = new SetteStringhe(prima, seconda, terza, quarta, quinta, sesta, settima);

        this.setPrecedenti(primeSette);
        this.setOttava(ottava);
    }// fine del metodo costruttore completo


    /**
     * metodo getter
     *
     * @return primeSette
     */
    private SetteStringhe getPrecedenti() {
        return primeSette;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param primeSette stringhe
     */
    private void setPrecedenti(SetteStringhe primeSette) {
        this.primeSette = primeSette;
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
        return this.getPrecedenti().getQuattro();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return cinque stringhe
     */
    public CinqueStringhe getCinque() {
        return this.getPrecedenti().getCinque();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return sei stringhe
     */
    public SeiStringhe getSei() {
        return this.getPrecedenti().getSei();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return sette stringhe
     */
    public SetteStringhe getSette() {
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
        return this.getPrecedenti().getQuinta();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return sesta
     */
    public String getSesta() {
        return this.getPrecedenti().getSesta();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return settima
     */
    public String getSettima() {
        return this.getPrecedenti().getSettima();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return ottava
     */
    public String getOttava() {
        return this.ottava;
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
        this.getPrecedenti().setQuinta(quinta);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param sesta - sesta stringa
     */
    public void setSesta(String sesta) {
        this.getPrecedenti().setSesta(sesta);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param settima - settima stringa
     */
    public void setSettima(String settima) {
        this.getPrecedenti().setSettima(settima);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param ottava - ottava stringa
     */
    public void setOttava(String ottava) {
        this.ottava = ottava;
    } // fine del metodo setter
}// fine della classe