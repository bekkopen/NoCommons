[![Maven Central](https://maven-badges.herokuapp.com/maven-central/no.bekk.bekkopen/nocommons/badge.svg)](https://maven-badges.herokuapp.com/maven-central/no.bekk.bekkopen/nocommons)
![](https://github.com/bekkopen/NoCommons/workflows/Java%20CI/badge.svg)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/bekkopen/NoCommons/blob/master/LICENSE.txt)

NoCommons er en samling hjelpeklasser for validering, generering og manipulering data knyttet til Norske domener. 
APIet er kjapt, standalone, offline og uten masse dependencies.    

> **Summary in english**: The NoCommons library is a collection of helper classes for validation, generation and manipulation of domains specific to Norway and Norwegian citizens.

Maven Site: [http://bekkopen.github.com/NoCommons/](http://bekkopen.github.com/NoCommons/)  

# Features
* Personnummer
  * validering
  * generering
* [Organisasjonsnummer](https://www.brreg.no/om-oss/oppgavene-vare/alle-registrene-vare/om-enhetsregisteret/organisasjonsnummeret/)
  * validering
  * generering
* Postnummer
  * oppslag
  * validering
* Kommunenummer / kommunenavn
  * oppslag
  * validering
* Fylkesnummer
  * oppslag
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
* Registreringsnummer
  * Validering av regnummer
  * Finn trafikkstasjon for bilnummer
  * Finn lenke på nettet til infoside
* Telefonnummer
  * Gyldig nummer (mange er reserverte)
  * "Korrekte" skrivemåter
  * Diverse logikk rundt nummerserier - finn leverandør, fylke etc
