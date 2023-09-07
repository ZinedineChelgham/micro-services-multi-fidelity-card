#language: fr
Fonctionnalité: Acheter et recevoir le code d'une offre

  Etant donné que l'utilisateur a des points
  Quand il prend une offre
  Alors il obtient sont code d'offre

  Scénario: Achat echouer - manque de points
    Étant donné que l'utilisateur a un certain nombre de points insuffisant
    Quand il achete sont offre en ligne
    Alors l'utilisateur ne recoit pas de code