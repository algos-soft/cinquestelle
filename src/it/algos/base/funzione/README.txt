Ogni progetto e' suddiviso in pacchetti.

Questo pacchetto raccoglie classi che eseguono alcune funzioni semplici,
di norma senza rappresentazione grafica, praticamente dei mini-beans.

Queste funzioni (o metodi, o subroutine), anziche' come singole classi di
questo pacchetto, possono anche essere implementate come metodi statici
nella classe Libreria.
Lo scopo e l'utilizzo sono identici; la differenza e' data solo dalla
complessita' della funzione.

Queste funzioni sono ampiamente utilizzate da tutti gli altri pacchetti.

Tutte le classi sono derivate dalla superclasse astratta FunzioneBase, che
raggruppa alcune variabili protette, usate dalle sottoclassi stesse.
Costruita per intercettare eventuali futuri sviluppi, comuni a tutte le
classi del pacchetto task.

La classe FunzioneTest, col metodo main, esegue la prova di ogni funzione.