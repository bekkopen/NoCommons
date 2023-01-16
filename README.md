[![Maven Central](https://maven-badges.herokuapp.com/maven-central/no.bekk.bekkopen/nocommons/badge.svg)](https://maven-badges.herokuapp.com/maven-central/no.bekk.bekkopen/nocommons)
![](https://github.com/bekkopen/NoCommons/workflows/Java%20CI/badge.svg)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/bekkopen/NoCommons/blob/master/LICENSE.txt)

NoCommons er en samling hjelpeklasser for validering, generering og manipulering data knyttet til Norske domener. 
APIet er kjapt, standalone, offline og uten masse dependencies.    

> **Summary in english**: The NoCommons library is a collection of helper classes for validation, generation and manipulation of domains specific to Norway and Norwegian citizens.

# Features
* Personnummer
  * validering
  * generering
* Organisasjonsnummer
  * [http://www.brreg.no/samordning/organisasjonsnummer.html](http://www.brreg.no/samordning/organisasjonsnummer.html)
  * validering
  * generering
* Postnummer
  * oppslag
  * validering
* Kommunenummer / kommunenavn
  * oppslag
  * validering
* Bank
  * Kontonummer validering og generering
  * KID generering
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
* Fylkesnummer
  * Finn fylkesnummer for fylke
  * Finn fylke for fylkesnummer
  * Validere fylke og / eller fylkesnummer
* Registreringsnummer
  * Validering av regnummer
  * Finn trafikkstasjon for bilnummer
  * Finn lenke på nettet til infoside
* Telefonnummer
  * Gyldig nummer (mange er reserverte)
  * "Korrekte" skrivemåter
  * Diverse logikk rundt nummerserier - finn leverandør, fylke etc

###

Update licence header for files with `mvn license:update-file-header -Dlicense.licenseName=mit`
