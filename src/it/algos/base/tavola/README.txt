il punto di partenza e' la classe TavolaBase; di tipo JPanel per essere inserita nella finestra

puo' essere usata in due modi: o nella FinestraLista e la occupa tutta, oppure in una FinestraScheda
di un altro modulo e deve essere dimensionata e posizionata

se usata nella scheda, viene estesa da una sottoclasse per disegnare i bottoni di comando

TavolaVista e' la JTable che mantiene tutte le informazioni grafiche: font, colore, dimensione, ecc

TavolaModello e' il modello astratto dei dati: una matrice bidimensionale per i dati ed una matrice per
i titoli

la cartella renderer contiene le classi specializzate per presentare ogni tipo di colonna: numeri, testo,
booleani, liste, ecc

TavolaOrdine consente l'ordinamento cliccando sul titolo di ogni colonna
