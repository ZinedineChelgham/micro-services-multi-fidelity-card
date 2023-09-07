
#language: fr
Fonctionnalité: Modifier le catalogue d'offres

  Scénario: Modification réussie
  Étant donné que le commerçant est enrigistré
  Quand le commerçant effectue les modifications souhaitées
  Alors le catalogue d'offres est mis à jour avec les nouvelles informations

  Scénario: Modification échouée - accès non autorisé
  Étant donné que le commerçant n'est pas enrigistré
  Quand le commerçant tente de modifier le catalogue d'offres
  Alors un message d'erreur est affiché indiquant que l'accès est non autorisé
  Et le catalogue d'offres n'est pas mis à jour

  Scénario: Modification échouée - données invalides
  Étant donné que le commerçant est enrigistré
  Quand les données saisies sont invalides ou manquantes
  Alors un message d'erreur est affiché indiquant que les données sont invalides
  Et le catalogue d'offres n'est pas mis à jour

  Scénario: cénario: échec de la mise à jour de l'offre - le commerçant n'est pas le propriétaire de l'offre
  Étant donné : un commerçant enregistré
  Quand : le commerçant essaie de mettre à jour ou de supprimer une offre qui ne lui appartient pas
  Alors : les modifications ne peuvent pas être effectuées et un message d'erreur est affiché indiquant qu'il n'est pas le propriétaire.


   Scénario: cénario : Succès de la mise à jour de l'offre - le commerçant est le propriétaire de l'offre
   Étant donné : un commerçant enregistré
   Lorsque : le commerçant essaie de mettre à jour ou de supprimer une offre qui lui appartient
   Alors : les modifications sont effectuées avec succès et un message de confirmation est affiché indiquant qu'il est le propriétaire.