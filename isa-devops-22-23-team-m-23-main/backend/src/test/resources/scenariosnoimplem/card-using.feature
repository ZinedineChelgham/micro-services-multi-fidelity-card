#language: fr
Fonctionnalité: Utilisation de la carte

  Scénario: Utilisation réussie
    Étant donné que l'utilisateur a une carte valide avec un solde suffisant
    Et le commerçant est un partenaire valide
    Quand l'utilisateur utilise sa carte pour effectuer un achat
    Alors l'achat est autorisé et le montant est déduit du solde de la carte
    Et les points sont automatiquement crédités sur le compte de l'utilisateur en fonction du montant dépensé

  Scénario: Utilisation échouée - carte non valide
    Étant donné que l'utilisateur utilise une carte non valide
    Quand il tente d'effectuer un achat
    Alors un message d'erreur est affiché indiquant que la carte est invalide
    Et l'achat n'est pas autorisé

  Scénario: Utilisation échouée - solde insuffisant
    Étant donné que l'utilisateur a une carte valide mais avec un solde insuffisant pour effectuer l'achat
    Quand il tente d'effectuer un achat
    Alors un message d'erreur est affiché indiquant que le solde est insuffisant
    Et l'achat n'est pas autorisé

  Scénario: Utilisation échouée - commerçant non partenaire
    Étant donné que l'utilisateur a une carte valide avec un solde suffisant
    Et le commerçant n'est pas un partenaire valide
    Quand l'utilisateur utilise sa carte pour effectuer un achat
    Alors un message d'erreur est affiché indiquant que le commerçant n'est pas un partenaire
    Et l'achat n'est pas autorisé