CREATE TABLESPACE tbs_perm_tw_file
  DATAFILE 'd:\db\tbs_perm_tw_file.dat' 
    SIZE 50M
    REUSE
    AUTOEXTEND ON NEXT 50M MAXSIZE 2000M
/

CREATE TEMPORARY TABLESPACE tbs_temp_tw_file
  TEMPFILE 'd:\db\tbs_temp_tw_file.dbf'
    SIZE 50M
    AUTOEXTEND ON
/

CREATE UNDO TABLESPACE tbs_undo_tw_file
  DATAFILE 'd:\db\tbs_undo_tw_file.f'
    SIZE 50M 
    AUTOEXTEND ON
  RETENTION GUARANTEE
/

drop user tw;

create user TW identified by TW;
alter user TW default tablespace tbs_perm_tw_file quota 1990M on tbs_perm_tw_file;

grant connect to tw;

GRANT CONNECT TO TW; -- userul a primit dreptul sa se conecteze la baza de date. Gasiti in partea de jos a paginii comanda pentru conectare.
GRANT CREATE TABLE TO TW; -- userul a primit dreptul sa creeze tabele, constrainturi si indecsi (urmatoarele 5 comentarii vor fi foarte similare, fac apel la rabdarea voastra sa le suportati).
GRANT CREATE VIEW TO TW; -- userul a primit dreptul sa creeze viewuri
GRANT CREATE SEQUENCE TO TW; -- userul a primit dreptul sa creeze secvente
GRANT CREATE TRIGGER TO TW; -- userul a primit dreptul sa creeze triggere
GRANT CREATE SYNONYM TO TW; -- userul a primit dreptul sa creeze sinonime
GRANT CREATE PROCEDURE TO TW; -- userul a primit dreptul sa creeze proceduri, functii si pachete
GRANT CREATE TYPE TO TW; -- userul a primit dreptul de a crea tipuri de date (de exemplu tabele imbricate)
