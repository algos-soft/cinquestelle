package it.algos.base.wrapper;

import it.algos.base.errore.Errore;

/**
 * Wrapper di diceci oggetti di tipo String.
 * </p>
 * <p/>
 * Si appoggia sull'oggetto di classe NoveStringhe <br>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 23-mar-2008 ore  09:22
 */
public final class DieciStringhe {

    /**
     * variabile di tipo NoveStringhe
     * le prime nove stringhe
     */
    private NoveStringhe primeNove;

    /**
     * variabile di tipo testo
     * decima stringa
     */
    private String decima;


    /**
     * Costruttore senza parametri.
     */
    public DieciStringhe() {
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
    public DieciStringhe(String precedente) {
        /* invoca il metodo sovrascritto della classe */
        this(precedente, "", "", "", "", "", "", "", "", "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con due stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public DieciStringhe(DueStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(), precedenti.getSeconda(), "", "", "", "", "", "", "", "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con tre stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public DieciStringhe(TreStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                "",
                "",
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
    public DieciStringhe(QuattroStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                "",
                "",
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
    public DieciStringhe(CinqueStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                precedenti.getQuinta(),
                "",
                "",
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
    public DieciStringhe(SeiStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                precedenti.getQuinta(),
                precedenti.getSesta(),
                "",
                "",
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
    public DieciStringhe(SetteStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                precedenti.getQuinta(),
                precedenti.getSesta(),
                precedenti.getSettima(),
                "",
                "",
                "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con otto stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public DieciStringhe(OttoStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                precedenti.getQuinta(),
                precedenti.getSesta(),
                precedenti.getSettima(),
                precedenti.getOttava(),
                "",
                "");
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri.
     * <p/>
     * Costruisce con nove stringhe <br>
     *
     * @param precedenti - l'istanza con le precedenti stringhe
     */
    public DieciStringhe(NoveStringhe precedenti) {
        /* invoca il metodo sovrascritto della classe */
        this(precedenti.getPrima(),
                precedenti.getSeconda(),
                precedenti.getTerza(),
                precedenti.getQuarta(),
                precedenti.getQuinta(),
                precedenti.getSesta(),
                precedenti.getSettima(),
                precedenti.getOttava(),
                precedenti.getNona(),
                "");
    }// fine del metodo costruttore


    /**
     * Costruttore completo con parametri.
     *
     * @param prima - prima stringa
     * @param seconda - seconda stringa
     * @param terza - terza stringa
     * @param quarta - quarta stringa
     * @param quinta - quinta stringa
     * @param sesta - sesta stringa
     * @param settima - settima stringa
     * @param ottava - ottava stringa
     * @param nona - nona stringa
     * @param decima - decima stringa
     */
    public DieciStringhe(String prima,
                         String seconda,
                         String terza,
                         String quarta,
                         String quinta,
                         String sesta,
                         String settima,
                         String ottava,
                         String nona,
                         String decima) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        NoveStringhe primeNove;
        primeNove = new NoveStringhe(prima,
                seconda,
                terza,
                quarta,
                quinta,
                sesta,
                settima,
                ottava,
                nona);

        this.setPrecedenti(primeNove);
        this.setDecima(decima);
    }// fine del metodo costruttore completo


    /**
     * metodo getter
     *
     * @return primeNove
     */
    private NoveStringhe getPrecedenti() {
        return primeNove;
    } // fine del metodo getter


    /**
     * metodo setter
     *
     * @param primeNove stringhe
     */
    private void setPrecedenti(NoveStringhe primeNove) {
        this.primeNove = primeNove;
    } // fine del metodo setter


    /**
     * Matrice dei dieci valori.
     *
     * @return matrice
     */
    public String[] getArray() {
        /* variabili e costanti locali di lavoro */
        String[] matrice = null;

        try { // prova ad eseguire il codice
            matrice = new String[10];
            matrice[0] = this.getPrima();
            matrice[1] = this.getSeconda();
            matrice[2] = this.getTerza();
            matrice[3] = this.getQuarta();
            matrice[4] = this.getQuinta();
            matrice[5] = this.getSesta();
            matrice[6] = this.getSettima();
            matrice[7] = this.getOttava();
            matrice[8] = this.getNona();
            matrice[9] = this.getDecima();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return matrice;
    } // fine del metodo getter


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
        return this.getPrecedenti().getSette();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return otto stringhe
     */
    public OttoStringhe getOtto() {
        return this.getPrecedenti().getOtto();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return nove stringhe
     */
    public NoveStringhe getNove() {
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
        return this.getPrecedenti().getOttava();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return nona
     */
    public String getNona() {
        return this.getPrecedenti().getNona();
    } // fine del metodo getter


    /**
     * metodo getter
     *
     * @return decima
     */
    public String getDecima() {
        return this.decima;
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
        this.getPrecedenti().setOttava(ottava);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param nona - nona stringa
     */
    public void setNona(String nona) {
        this.getPrecedenti().setNona(nona);
    } // fine del metodo setter


    /**
     * metodo setter
     *
     * @param decima - decima stringa
     */
    public void setDecima(String decima) {
        this.decima = decima;
    } // fine del metodo setter

}// fine della classe