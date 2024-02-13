# ControleAcces

# Use Cases
✅ ETANT DONNE un lecteur lié à une porte  
QUAND un badge est présenté  
ET que ce lecteur est interrogé  
ALORS cette porte s'ouvre

✅ ETANT DONNE un lecteur lié à une porte  
QUAND aucun badge n'est présenté  
ET que ce lecteur est interrogé  
ALORS la porte ne s'ouvre pas

✅ ETANT DONNE un lecteur lié à deux portes  
QUAND un badge est présenté  
ET que ce lecteur est interrogé  
ALORS les portes s'ouvrent

✅ ETANT DONNE un lecteur lié à une porte  
QUAND un badge bloqué est présenté  
ET que ce lecteur est interrogé  
ALORS cette porte ne s'ouvre pas

✅ ETANT DONNE un lecteur lié à une porte  
QUAND un badge est bloqué puis débloqué est présenté  
ET que ce lecteur est interrogé  
ALORS cette porte s'ouvre

✅ ETANT DONNE un lecteur lié à une porte  
QUAND un badge est présenté  
ET que le lecteur n'est interrogé  
ALORS la porte ne s'ouvre pas

✅ ETANT DONNE plusieurs lecteurs lié à une porte  
QUAND un badge est passé devant le deuxième lecteur  
ET que ces lecteurs sont interrogés  
ALORS la porte est déverrouillée

✅ ETANT DONNE plusieurs lecteurs lié à leur porte  
QUAND un badge est passé devant le deuxième lecteur  
ET que ces lecteurs sont interrogés  
ALORS seule la deuxième porte s'ouvre

✅ ETANT DONNE un lecteur lié à une porte  
QUAND un badge est présenté  
ET que le lecteur est interrogé deux fois  
ALORS la porte ne s'ouvre qu'une fois

✅ ETANT DONNE une porte bloquée  
QUAND un badge est présenté  
ET que le lecteur est interrogé  
ALORS la porte ne s'ouvre pas

✅ ETANT DONNE un lecteur relié à deux portes dont une bloquée  
QUAND un badge est présenté  
ET que le lecteur est interrogé  
ALORS seule la porte bloquée ne s'ouvre pas

✅ ETANT DONNE un lecteur relié à une porte  
QUAND l'heure actuelle est dans la plage horaire  
ET que le lecteur est interrogé  
ALORS la porte s'ouvre

✅ ETANT DONNE un lecteur relié à une porte  
QUAND l'heure actuelle est dans la plage horaire  
ET que le badge est bloqué  
ALORS la porte s'ouvre

ETANT DONNE un lecteur relié à une porte  
QUAND l'heure actuelle est dans la plage horaire  
ET que le badge est débloqué  
ALORS la porte s'ouvre

ETANT DONNE un lecteur relié à une porte  
QUAND l'heure actuelle n'est pas dans la plage horaire  
ALORS la porte n'est pas déverrouillée

ETANT DONNE un lecteur relié à une porte  
QUAND l'heure actuelle n'est pas dans la plage horaire  
ET que le badge est bloqué  
ALORS la porte n'est pas déverrouillée

ETANT DONNE un lecteur relié à une porte  
QUAND l'heure actuelle n'est pas dans la plage horaire  
ET que le badge est débloqué  
ALORS la porte n'est pas déverrouillée

ETANT DONNE un lecteur relié à deux portes  
QUAND l'heure actuelle est dans la plage horaire  
ALORS la porte s'ouvrir

ETANT DONNE un lecteur relié à deux portes  
QUAND l'heure actuelle n'est pas dans la plage horaire  
ALORS la porte n'est pas déverrouillée

ETANT DONNE un lecteur lié à une porte  
QUAND l'heure actuelle est dans la plage horaire  
ET un badge est bloqué puis débloqué  
ALORS cette porte s'ouvre

ETANT DONNE un lecteur lié à une porte  
QUAND l'heure actuelle n'est pas dans la plage horaire  
ET un badge est bloqué puis débloqué  
ALORS la porte n'est pas déverrouillée

ETANT DONNE un lecteur lié à deux portes  
QUAND l'heure actuelle est dans la plage horaire pour la première porte  
ET l'heure actuelle n'est pas dans la plage horaire pour la deuxième porte  
ALORS la première porte s'ouvre

ETANT DONNE un lecteur lié à deux portes  
QUAND l'heure actuelle n'est pas dans la plage horaire pour la première porte  
ET l'heure actuelle est dans la plage horaire pour la deuxième porte  
ALORS la deuxième porte s'ouvre

ETANT DONNE un lecteur lié à une porte sans plage horaire définie  
QUAND l'heure actuelle est dans la plage horaire  
ALORS la porte ne s'ouvre pas

TEST 25 :
ETANT DONNE un lecteur lié à une porte avec une plage horaire non valide (plage horaire de fin avant plage horaire de début)  
QUAND l'heure actuelle est dans la plage horaire  
ALORS la porte ne s'ouvre pas

