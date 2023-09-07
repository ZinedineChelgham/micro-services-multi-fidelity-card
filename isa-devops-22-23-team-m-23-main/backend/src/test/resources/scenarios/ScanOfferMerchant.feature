#language: fr
Fonctionnalité: Scanner une offre cliente

  Scénario: Scan reussi
    Étant donné que Le client se presente
    Quand il a un code valide
    Alors le marchant scan et obtient bien l'offre ne question

  #Scénario: scan echouer - code invalide
   # Étant donné que Le client se presente
   # Quand il a un code NON valide
   # Alors le marchant scan et obtient une erreur   scenario marche pas, probleme avec l'id du customer egale a 0 ??