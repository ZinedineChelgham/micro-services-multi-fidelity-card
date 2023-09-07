#language: fr
Fonctionnalité: Gain de point des suites d'un achat physique en presence du commercant

  Scénario: Scan Réussi
    Étant donné que l'utilisateur a une carte de fidélité valide
    Quand il le présente au commerçant qui scan un montant 15 euro
    Alors le compte utilisateur gagne des points

  Scénario: Scan échouer - le client n'est plus membre
    Étant donné que l'utilisateur a une carte de fidélité non valide
    Quand il le présente au commerçant qui scan un montant 15 euro
    Alors le compte utilisateur ne gagne pas de points