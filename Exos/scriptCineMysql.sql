CREATE DATABASE IF NOT EXISTS gesCinema;
USE gesCinema;

-- Creation des tables
create table cinema
(numC int not null,
 nomC varchar(15) not null,
 adresse varchar(15),
 CP int default 000000,
 ville varchar(10),
 tel int,
 nbsalle int default 00000,
 tarifetudiant int,
 tarifnormal int,
 tarifspecial int,
 nomM varchar(25),
 numQ int,
 constraint cinema_numC_pk primary key(numC)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table film
(numF int not null,
 titreF varchar(10) not null,
 realisateur varchar(15),
 dureeF int ,
 anneedesortie date,
 titreabrege varchar(3),
 codeG int,
 constraint film_numF_pk primary key(numF)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table acteur
(numA int not null,
 nomA varchar(10) not null,
 prenomA varchar(20) ,
 constraint acteur_numA_pk primary key(numA)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table genre
(codeG int not null,
 libelleG varchar(20),
 constraint genre_codeG_pk primary key(codeG)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table seance
(numS int not null,
 heuredebut varchar(6),
 constraint seance_numS_pk primary key(numS)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table metro
(nomM varchar(25) not null,
 constraint metro_nomM_pk primary key(nomM)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table ligne
(numL int not null,
 libelleL varchar(20),
 constraint ligne_numL_pk primary key(numL)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table quartier
(numQ int  not null,
 nomQ varchar(25) not null,
 constraint quartier_numQ_pk primary key(numQ)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table joue
(numF int not null,
 numA int not null,
 nordre int,
 constraint joue_numF_numA_pk primary key(numF,numA)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table comprend
(nomM varchar(25) not null,
 numL int not null,
 constraint comprend_nomM_numL_pk primary key(nomM,numL)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

create table sort
(numC int not null,
 numF int not null,
 numS int not null,
 constraint sort_numC_numF_numS_pk primary key(numC,numF,numS)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Ajout des contraintes d'intégrité de clé étrangères
alter table cinema
 add constraint cinema_metro_fk
   foreign key (nomM) references metro(nomM);

alter table cinema
 add constraint cinema_quartier_fk
  foreign key (numQ) references quartier (numQ);

alter table film 
 add constraint film_genre_fk
  foreign key (codeG) references genre(codeG);

alter table joue
 add constraint film_joue_numF_fk
  foreign key(numF) references film(numF);

alter table joue
 add constraint acteur_joue_numA_fk
  foreign key(numA) references acteur(numA);

alter table comprend
 add constraint metro_comprend_nomM_fk
  foreign key(nomM) references metro(nomM);

alter table comprend
 add constraint ligne_comprend_numL_fk
  foreign key(numL) references ligne(numL);

alter table sort
 add constraint cinema_sort_numC_fk
  foreign key(numC) references cinema(numC);

alter table sort
 add constraint film_sort_numF_fk
  foreign key(numF) references film(numF);

alter table sort
 add constraint seance_sort_numS_fk
  foreign key(numS) references seance(numS);

-- Insertion dans les tables
insert into acteur(numA,nomA,prenomA) values(1,'delon','alain');
insert into acteur(numA,nomA,prenomA) values(2,'bronson','peeter');
insert into acteur(numA,nomA,prenomA) values(3,'antony','marc');
insert into acteur(numA,nomA,prenomA) values(4,'sidnet','florent');

insert into metro(nomM) values('gare de l''est');
insert into metro(nomM) values('gare du nord');
insert into metro(nomM) values('nation');

insert into genre(codeG,libelleG) values(1,'comedie');
insert into genre(codeG,libelleG) values(2,'action');
insert into genre(codeG,libelleG) values(3,'espionnage');
insert into genre(codeG,libelleG) values(4,'drame');

insert into quartier(numQ,nomQ) values(1,'barbes');
insert into quartier(numQ,nomQ) values(2,'champs elysée');
insert into quartier(numQ,nomQ) values(3,'st lasard');
insert into quartier(numQ,nomQ) values(4,'cliancourt');

insert into film(numF,titreF,codeG) values(1,'les amis',1);
insert into film(numF,titreF,codeG) values(2,'les 7 nain',2);
insert into film(numF,titreF,codeG) values(3,'crocodiles',1);
insert into film(numF,titreF,codeG) values(4,'toto et dago',3);
insert into film(numF,titreF,codeG) values(5,'bouba',1);

insert into seance(numS,heuredebut) values(1,'08:30');
insert into seance(numS,heuredebut) values(2,'09:30');
insert into seance(numS,heuredebut) values(3,'10:30');
insert into seance(numS,heuredebut) values(4,'12:30');
insert into seance(numS,heuredebut) values(5,'13:30');

insert into joue(numF,numA,nordre) values(1,1,1);
insert into joue(numF,numA,nordre) values(1,2,2);
insert into joue(numF,numA,nordre) values(1,3,3);
insert into joue(numF,numA,nordre) values(2,1,1);
insert into joue(numF,numA,nordre) values(2,2,2);
insert into joue(numF,numA,nordre) values(3,4,1);

insert into ligne(numL,libelleL) values(1,'direct');
insert into ligne(numL,libelleL) values(2,'diferé');
insert into ligne(numL,libelleL) values(3,'danger');

insert into comprend(nomM,numL) values('gare de l''est',1);
insert into comprend(nomM,numL) values('gare du nord',2);
insert into comprend(nomM,numL) values('nation',1);
insert into comprend(nomM,numL) values('nation',2);
insert into comprend(nomM,numL) values('nation',3);

insert into cinema
 values(1,'orient','01 bp 52 abj',01,'abidjan', 20382554,4,500,1000,1500,'nation',1);
insert into cinema
 values(2,'studio','23 bp 512 bass',23,'bassam',21382554,2,500,800,1700,'nation',1);
insert into cinema
 values(3,'django','17 bp 472 bono',01,'bonoua',21302554,5,800,1000,1500,'gare de l''est',1);
insert into cinema 
 values(4,'jager','171bp 5247 abj',171,'abidjan',23457841,6,200,500,600,'nation',3);
insert into cinema
 values(5,'akadi','01 bp 62 abj',01,'yakro',63215478,1,300,500,700,'gare du nord',2);
insert into cinema
 values(6,'le paris','12 bp 42 abj',12,'abidjan',22382554,4,700,1500,2000,'gare du nord',2);

insert into sort(numC,numF,numS) values(1,1,1);
insert into sort(numC,numF,numS) values(1,1,3);
insert into sort(numC,numF,numS) values(1,3,1);
insert into sort(numC,numF,numS) values(2,1,2);
insert into sort(numC,numF,numS) values(3,3,2);
insert into sort(numC,numF,numS) values(3,4,4);
insert into sort(numC,numF,numS) values(4,2,1);
insert into sort(numC,numF,numS) values(4,2,2);
insert into sort(numC,numF,numS) values(1,3,4);



