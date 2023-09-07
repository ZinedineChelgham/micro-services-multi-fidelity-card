#language: fr
Fonctionnalité: Chargement de la carte
  
  Scénario: Chargement réussi
    Étant donné que l'utilisateur a un compte valide
    Et il fournit les informations de paiement valides
    Et il indique le montant qu'il souhaite charger
    Quand il demande à charger sa carte
    Alors le montant est ajouté à la carte de l'utilisateur avec succès
    Et un reçu est envoyé à l'utilisateur par email

  Scénario: Chargement échoué - compte non valide
    Étant donné que l'utilisateur ne possède pas de compte valide
    Quand il demande à charger sa carte
    Alors un message d'erreur est affiché indiquant que l'utilisateur doit créer un compte valide
    Et le chargement n'est pas effectué

  Scénario: Chargement échoué - informations de paiement non valides
    Étant donné que l'utilisateur a un compte valide
    Et il fournit des informations de paiement non valides
    Et il indique le montant qu'il souhaite charger
    Quand il demande à charger sa carte
    Alors un message d'erreur est affiché indiquant que les informations de paiement sont incorrectes
    Et le chargement n'est pas effectué

  Scénario: Chargement échoué - montant non valide
    Étant donné que l'utilisateur a un compte valide
    Et il fournit des informations de paiement valides
    Et il indique un montant non valide (par exemple, négatif)
    Quand il demande à charger sa carte
    Alors un message d'erreur est affiché indiquant que le montant est incorrect
    Et le chargement n'est pas effectué