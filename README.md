# ControleAcces

Le projet simule un système de contrôle d'accès. On peut y trouver des lecteurs, des portes, des badges, des zones et
des plages horaires. Le but est de tester les différentes fonctionnalités du système et ainsi s'assurer de son bon
fonctionnement.  
On utilise la méthode ATDD (Acceptance Test Driven Design) pour développer le projet. La première étape est de faire un
test qui échoue. Ensuite, on développe le code pour que le test passe. Enfin, on refactorise le code.

# Use Cases

1: ✅ ETANT DONNE un lecteur lié à une porte  
QUAND un badge est présenté  
ET que ce lecteur est interrogé  
ALORS cette porte s'ouvre

2: ✅ ETANT DONNE un lecteur lié à une porte  
QUAND aucun badge n'est présenté  
ET que ce lecteur est interrogé  
ALORS la porte ne s'ouvre pas

3: ✅ ETANT DONNE un lecteur lié à deux portes  
QUAND un badge est présenté  
ET que ce lecteur est interrogé  
ALORS les portes s'ouvrent

4: ✅ ETANT DONNE un lecteur lié à une porte  
QUAND un badge bloqué est présenté  
ET que ce lecteur est interrogé  
ALORS cette porte ne s'ouvre pas

5: ✅ ETANT DONNE un lecteur lié à une porte  
QUAND un badge est bloqué puis débloqué est présenté  
ET que ce lecteur est interrogé  
ALORS cette porte s'ouvre

6: ✅ ETANT DONNE un lecteur lié à une porte  
QUAND un badge est présenté  
ET que le lecteur n'est interrogé  
ALORS la porte ne s'ouvre pas

7: ✅ ETANT DONNE plusieurs lecteurs lié à une porte  
QUAND un badge est passé devant le deuxième lecteur  
ET que ces lecteurs sont interrogés  
ALORS la porte est déverrouillée

8: ✅ ETANT DONNE plusieurs lecteurs lié à leur porte  
QUAND un badge est passé devant le deuxième lecteur  
ET que ces lecteurs sont interrogés  
ALORS seule la deuxième porte s'ouvre

9: ✅ ETANT DONNE un lecteur lié à une porte  
QUAND un badge est présenté  
ET que le lecteur est interrogé deux fois  
ALORS la porte ne s'ouvre qu'une fois

10: ✅ ETANT DONNE une porte bloquée  
QUAND un badge est présenté  
ET que le lecteur est interrogé  
ALORS la porte ne s'ouvre pas

11: ✅ ETANT DONNE un lecteur relié à deux portes dont une bloquée  
QUAND un badge est présenté  
ET que le lecteur est interrogé  
ALORS seule la porte bloquée ne s'ouvre pas

12: ✅ ETANT DONNE un lecteur relié à une porte  
ET QUE l'heure actuelle est dans la plage horaire  
QUAND le lecteur est interrogé  
ALORS la porte s'ouvre

13: ✅ ETANT DONNE un lecteur relié à une porte  
ET QUE l'heure actuelle est dans la plage horaire  
QUAND le badge est bloqué  
ALORS la porte s'ouvre

14: ✅ ETANT DONNE un lecteur relié à une porte  
ET que le lecteur est interrogé un jour de la semaine (hors week-end)  
QUAND un badge est présenté  
ALORS la porte s'ouvre

15: ✅ ETANT DONNE une porte bloquée  
ET que le lecteur est interrogé  
QUAND un badge administrateur est présenté  
ALORS la porte s'ouvre

16: ETANT DONNE un lecteur relié à une porte  
ET que toutes les portes sont fermées de 22h à minuit (maintenance)  
QUAND un badge est présenté  
ALORS la porte ne s'ouvre pas


