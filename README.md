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

(✅) Le test précédent valide indirectement les tests liés sur le blocage de plusieurs portes

✅ ETANT DONNE un lecteur relié à deux portes dont une bloquée  
QUAND un badge est présenté  
ET que le lecteur est interrogé  
ALORS seule la porte bloquée ne s'ouvre pas