[![Build Status](https://travis-ci.org/bekkopen/NoCommons.png?branch=master)](http://travis-ci.org/bekkopen/NoCommons) 


NoCommons er en samling hjelpeklasser for validering, generering og manipulering data knyttet til Norske domener. 
APIet er kjapt, standalone, offline og uten masse dependencies. 

Verdier og lister som endrer seg over tid slik som kommunelister, poststed etc. er typisk ikke bygd inn.   

> **Summary in english**: The NoCommons library is a collection of helper classes for validation, generation and manipulation of domains specific to Norway and Norwegian citizens.

Maven Site: [http://bekkopen.github.com/NoCommons/](http://bekkopen.github.com/NoCommons/)

# Features
* Personnummer
  * validering
  * generering
* Organisasjonsnummer
  * [http://www.brreg.no/samordning/organisasjonsnummer.html](http://www.brreg.no/samordning/organisasjonsnummer.html)
  * validering
  * generering
* Postnummer
  * validering
* Bank
  * Kontonummer validering og generering
  * KID validering
* Norsk språk o.l.
  * Konvertering av norske bokstaver i en tekst til de engelske erstatningene aa, ae og oe
  * Generering av typisk Norske personnavn
* Norsk datofunksjonalitet
  * Sjekk om dato er helligdag
  * Sjekk om dato er arbeidsdag (dvs. ikke helligdag eller helg)
  * Legg til x antall arbeidsdager til en gitt dato
  * Liste ut alle helligdager
  

## Features som ikke er implementert, men som kan passe inn
* Bank
  * KID generator
* Fylkesnummer
  * Finn fylkesnummer for fylke
  * Finn fylke for fylkesnummer
  * Validere fylke og / eller fylkesnummer
* Kommunenummer
  * Finn kommunenummer for kommune
  * Finn kommune for kommunenummer
  * Validere kommune og / eller kommunenummer
* Registreringsnummer
  * Validering av regnummer
  * Finn trafikkstasjon for bilnummer
  * Finn lenke på nettet til infoside
* Telefonnummer
  * Gyldig nummer (mange er reserverte)
  * "Korrekte" skrivemåter
  * Diverse logikk rundt nummerserier - finn leverandør, fylke etc
* Postnummer
  * Finn poststed basert på postnummer og omvendt
  * Finn poststed basert på starten av postnummer og omvendt
  * Basere seg på lister fra posten: [http://epab.posten.no/Norsk/Nedlasting/NedlastingMeny.htm](http://epab.posten.no/Norsk/Nedlasting/NedlastingMeny.htm])