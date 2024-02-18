# A FAIRE

- revoir tous les tests (structure du test, vérifier que tous les tests sont bien listés/codés)
- ✅ ajouter les jours de la semaine pour les permissions des portes (par défaut tous les jours, mais possibilité de
  bloquer certains jours)
- ✅ ajouter un badge admin qui bypass toutes les restrictions (porte bloquée, plage horaire...)
- ✅ ajouter la fermeture automatique du site pour maintenance (22h-5h), ce qui bloque toutes les portes
- ✅ ajouter des zones qui regroupent des portes (on renseigne la zone dans la porte, par défaut zone A), et on affecte
  des badges à des zones
- faire en sorte de pouvoir bloquer une zone (accessible par un admin)
- faire en sorte de ne pas pouvoir bloquer une porte réservée aux techniciens
- les portes des techniciens sont bloquees pour les admins
- une zone qui regroupe des portes qui concernent sont ouvertes

--------------------

15: ETANT DONNE un lecteur lié à une zone qui regroupe des portes  
ET que le badge n'a pas accès à la zone  
QUAND un badge est présenté  
ALORS les portes ne s'ouvrent pas

16: ETANT DONNE un lecteur lié à une zone qui regroupe des portes  
QUAND on affecte cette zone au badge  
ET qu'un badge est présenté  
ALORS les portes s'ouvrent

17: ETANT DONNE un lecteur lié à une zone qui regroupe des portes  
QUAND un badge administrateur est présenté  
ALORS les portes s'ouvrent

18: ETANT DONNE un lecteur lié à une zone qui regroupe des portes  (TODO)
ET que la zone est bloquée
QUAND un badge utilisateur est présenté  
ALORS les portes ne s'ouvrent pas

18: ETANT DONNE un lecteur lié à une porte bloquée hors maintenance
ET que cette porte possède un accès réservé aux techniciens  
QUAND un badge technicien est présenté  
ALORS la porte s'ouvre

19: ETANT DONNE un lecteur lié à une zone qui regroupe des portes  (TODO)
ET que la zone est bloquée
QUAND un badge administrateur est présenté  
ALORS les portes s'ouvrent

21: ETANT DONNE un lecteur relié à une porte  
QUAND un badge utilisateur est présenté  
ET que l'heure actuelle est dans la plage horaire de maintenance (0h-3h)  
ALORS la porte ne s'ouvre pas

21: ETANT DONNE un lecteur relié à une porte  
QUAND un badge visiteur est présenté  
ET que l'heure actuelle est dans la plage horaire de maintenance (0h-3h)  
ALORS la porte ne s'ouvre pas

22: ETANT DONNE un lecteur relié à une porte  
QUAND un badge administrateur est présenté   
ET que l'heure actuelle est dans la plage horaire de maintenance  
ALORS la porte ne s'ouvre pas

22: ETANT DONNE un lecteur relié à une porte  
QUAND un badge technicien est présenté   
ET que l'heure actuelle est dans la plage horaire de maintenance  
ALORS la porte s'ouvre

24: ETANT DONNE un lecteur relié à une zone qui regroupe des portes (TODO)
QUAND un badge technicien est présenté  
ET que cette zone comporte une porte avec un accès réservé aux techniciens
ALORS seule cette porte s'ouvre

24: ETANT DONNE un lecteur relié à une zone qui regroupe des portes (TODO)
QUAND un badge utilisateur est présenté  
ET que cette zone comporte une porte avec un accès réservé aux admins
ALORS seule la porte réservée aux admins ne s'ouvre pas

25: ETANT DONNE un lecteur relié à une porte avec un accès réservé aux techniciens   
QUAND un badge administrateur est présenté  
ALORS la porte ne s'ouvre pas

25: ETANT DONNE un lecteur relié à une porte avec un accès réservé aux techniciens   
QUAND un badge utilisateur est présenté  
ALORS la porte ne s'ouvre pas

XX: (A VOIR) ETANT DONNE un lecteur lié à une porte avec une plage horaire non valide (plage horaire de fin avant plage
horaire de début)   (TODO)
QUAND l'heure actuelle est dans la plage horaire  
ALORS la porte ne s'ouvre pas
