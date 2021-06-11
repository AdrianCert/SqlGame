INSERT INTO Question (ID, title, description, solution, value, reward)
VALUES (1, 'Care student are bursa?', 'Andrei ar dori sa afle numele, prenumele si codul matricol al studentilor care au bursa.', 'SELECT nume, prenume, nr_matricol FROM studenti WHERE bursa IS NOT NULL', 10, 20);

INSERT INTO Question (ID, title, description, solution, value, reward)
VALUES (2, 'Cine iubeste Logica?', 'Profesorul de logica vrea sa stie numele si prenumele studentilor pe care ii invata.', 'SELECT s.nume, s.prenume FROM studenti s JOIN cursuri c ON s.an = c.an WHERE c.id=1', 10, 20);

INSERT INTO Question (ID, title, description, solution, value, reward)
VALUES (3, 'Eu stiu sa scriu SQL query?', 'Eu vreau sa aflu numele si prenumele studentului cu codul matricol 608IZ8.', "SELECT * FROM studenti WHERE nr_matricol='608IZ8'", 0, 10);

INSERT INTO Question (ID, title, description, solution, value, reward)
VALUES (4, 'Vreau sa-mi stiu nota!?', 'Afisati numarul matricol al studentului, nota si cursul la care nota a fost pusa.', 'SELECT s.nr_matricol, n.valoare, c.titlu_curs FROM studenti s JOIN note n ON s.id=n.id_student JOIN cursuri c ON n.id_curs=c.id', 20, 40);

INSERT INTO Question (ID, title, description, solution, value, reward)
VALUES (5, 'Cine mi-a pus aceasta nota!?', 'Afisati numarul matricol al studentului, nota si profesorul care ar fi putut sa puna acea nota(numele si prenumele vor fi concatenate in aceasta ordine).', "SELECT s.nr_matricol, n.valoare, p.nume||' '||p.prenume AS "PROFESOR" FROM studenti s JOIN note n ON s.id=n.id_student JOIN didactic d ON d.id_curs=n.id_curs  JOIN profesori p ON p.id=d.id_profesor", 20, 40);

INSERT INTO Question (ID, title, description, solution, value, reward)
VALUES (6, 'Stiu cine sunt eu, dar cine imi sunt prietenii mei?', 'Afisati numarul matricol al studentului si ai prietenului sau.', 'SELECT p.nr_matricol, s2.nr_matricol FROM (SELECT s1.nr_matricol, p.id_student2 FROM studenti s1 JOIN prieteni p ON s1.id=p.id_student1) p JOIN studenti s2 ON p.id_student2=s2.id', 20, 40);
