--***VIZUALIZARE
select * from decor;
select * from recuzita;
select * from decor_recuzita;
select * from cabina;
select * from sala;
select * from actor;
select * from piesa;
select * from contract;
select * from reprezentatie;


-- afisare program teatru de la cea mai recenta piesa pana la cea mai indepartata
SELECT 'Reprezentatia nr. ' || id_reprezentatie || ':' || ' Pe data de ' || r.zi || ' de la ora '|| r.ora_inceput || ' pana la ' || r.ora_sfarsit || ' se va juca piesa ' || p.nume || ' scrisa de ' || p.autor || '. Spectacolul va avea loc in sala ' || s.nume || ' pe care o gasiti la etajul ' || s.etaj || ' in cladirea teatrului nostru.' 
FROM reprezentatie r, sala s, piesa p
where r.id_piesa= p.id_piesa AND r.id_sala=s.id_sala
order by zi;

-- afisare lista cu piese si decorul pentru fiecare
SELECT 'Pentru piesa ' || p.nume || ' este nevoie de decorul intitulat "' || d.nume || '". '
FROM piesa p, decor d
where p.id_decor= d.id_decor;

--afisare lista cu obiecte de recuzita necesare pentru realizarea fiecarui decor in parte
SELECT 'Pentru decorul '  || d.nume || ' este nevoie de ' || r.nume 
FROM decor_recuzita dr, recuzita r, decor d
where dr.decor_id_decor= d.id_decor and dr.recuzita_id_recuzita= r.id_recuzita
ORDER BY d.nume;

--afisare contracte existente
SELECT 'Contract :'||' Actorul '|| a. prenume || ' ' || a.nume || ' a semnat un contract pentru a juca in piesa ' || p.nume
FROM contract c, actor a, piesa p
where c.actor_id_actor=a.id_actor and c.piesa_id_piesa = p.id_piesa;

--afisare lista piese si actorii care joaca in ele
SELECT 'In piesa '|| p.nume|| ' joaca ' || a.nume || ' ' ||a.prenume ||'.'
FROM contract c, actor a, piesa p
where c.actor_id_actor=a.id_actor and c.piesa_id_piesa = p.id_piesa
Order by id_piesa;

--afisare detalii despre actori 
select 'Nume actor : ' || nume || ' ' || prenume || ' ; Data angajare: '|| data_angajare || ' ; Vechime: ' || trunc(months_between(sysdate, data_angajare)/12) || ' ani ; ' || 
' Data nastere: ' || data_nastere || ' ; ' || ' Varsta: '||trunc(months_between(sysdate, data_nastere)/12) 
|| ' ani ; ' || 'Telefon : ' ||telefon || '; Email: ' || nvl(email, 'nespecificat') || '; Marime haine: '|| nvl(marime_haine, 'nespecificat') 
|| ' ; Salariu: '|| salariu || ' lei'
from actor
order by nume;


-- afisare a tuturor planificarilor de reprezentatie a unei anumite piese in functie de numele piesei
select 'In ziua de ' || zi || ' de la ora ' || ora_inceput || ' pana la ora ' || ora_sfarsit || ' se va juca piesa Opt Femei' from reprezentatie where id_piesa=(select id_piesa from piesa where nume ='Opt femei');

-- afisare a detaliilor unei reprezentatii in functie de id-ul ei
select 'Detalii reprezentatie cu id-ul 2 : Piesa ' || (select nume from piesa where id_piesa=(select id_piesa from reprezentatie where id_reprezentatie=2) )  || ' scrisa de ' || (select autor from piesa where id_piesa=(select id_piesa from reprezentatie where id_reprezentatie=2)) ||' se va juca pe data de ' ||zi || ' de la ora ' || ora_inceput || ' pana la '|| ora_sfarsit from reprezentatie where id_reprezentatie=2;

--***MODIFICARE
update  decor set nume = 'livada' where nume = 'padure' ;
select * from decor;

update  recuzita set material = 'metal' where nume = 'trasura' ;
select * from recuzita;

update decor_recuzita set recuzita_id_recuzita=(select id_recuzita from recuzita where nume='pat') where recuzita_id_recuzita=(select id_recuzita from recuzita where nume='gard' ) ;
select * from decor_recuzita;

update decor_recuzita set cantitate=8 where recuzita_id_recuzita=(select id_recuzita from recuzita where nume='costum nobila' ) ;
select * from decor_recuzita;

update cabina set etaj=1 where id_cabina=2;
select * from cabina;

update sala set capacitate=280 where nume='Ion Creanga';
select * from sala;

update  actor set telefon = '0722678981' where nume = 'Lupu' and prenume= 'Ada' ;
select * from actor;

update actor set email='adabalada@gmail.com' where id_cabina=(select id_cabina from cabina where id_cabina=2);
select * from actor;

update piesa set durata=1 where nume='Luceafãrul';
select * from piesa;

update reprezentatie set ora_sfarsit=18 where id_piesa=(select id_piesa from piesa where nume='Luceafãrul') and ora_inceput=17;
select * from reprezentatie;

update reprezentatie set ora_sfarsit=20 where id_piesa=(select id_piesa from piesa where nume='Luceafãrul') and ora_inceput=19;
select * from reprezentatie;

update contract set actor_id_actor=(select id_actor from actor where nume='Iorga' and prenume='Livia') where actor_id_actor=(select id_actor from actor where nume='Lupu' and prenume='Ada') ;
select * from contract;

--***STERGERE
delete from decor where nume='scoala';
select * from decor;

delete from recuzita where nume='scaun';
select * from recuzita;

delete from decor_recuzita where decor_id_decor=(select id_decor from decor where nume='gara'); 
select * from decor_recuzita;

delete from cabina where id_cabina=6;
select * from cabina;

delete from sala where nume='Marin Sorescu';
select * from sala;

delete from actor where salariu BETWEEN 5300 AND 5600;
select * from actor;

delete from piesa where gen='satira';
select * from piesa;

delete from contract where piesa_id_piesa=(select id_piesa from piesa where nume='Gaitele');
select * from contract;

delete from reprezentatie where zi=to_date('29/12/2022','DD/MM/YYYY');
select * from reprezentatie;

--***VALIDARE (constrangeri)

--length(nume) > 2
insert into actor values (null,'P','Florin',TO_DATE('10/05/1962','DD/MM/YYYY'),'0756789234','f_piersic@gmail.com',TO_DATE('10/05/1999','DD/MM/YYYY'),9800,'XL',1); 

--numar de telefon
insert into actor values (null,'Piersic','Florin',TO_DATE('10/05/1962','DD/MM/YYYY'),'1756789','f_piersic@gmail.com',TO_DATE('10/05/1999','DD/MM/YYYY'),9800,'XL',1);

--email
insert into actor values (null,'Piersic','Florin',TO_DATE('10/05/1962','DD/MM/YYYY'),'0756789234','f_piersic@',TO_DATE('10/05/1999','DD/MM/YYYY'),9800,'XL',1);

--marime haine in lista celor posibile
insert into actor values (null,'Piersic','Florin',TO_DATE('10/05/1962','DD/MM/YYYY'),'0756789234','f_piersic@gmail.com',TO_DATE('10/05/1999','DD/MM/YYYY'),9800,'XM',1);

--telefon unique
insert into actor values (null,'Piersic','Florin',TO_DATE('10/05/1962','DD/MM/YYYY'),'0756789234','f_piersic@gmail.com',TO_DATE('10/05/1999','DD/MM/YYYY'),9800,'XL',1);

--email unique
insert into actor values (null,'Piersic','Florin',TO_DATE('10/05/1962','DD/MM/YYYY'),'0756789234','h_malaele@gmail.com',TO_DATE('10/05/1999','DD/MM/YYYY'),9800,'XL',1);

--cabina sa fie la un etaj de la 1 la 3
insert into cabina values(null ,4);

--nume decor sa fie in lista de valori posibile ( 'bâlci','centru',etc.)
insert into decor values(null ,'parc subacvatic');

--nume autor al piesei mai lung de 4 caractere
insert into piesa values(null, 'Gaitele' , 'Al K', 'comedie', 2,12,(select id_decor from decor where nume='bâlci'));

--genul piesei sa fie in lista de valori ('comedie', 'drama',etc)
insert into piesa values(null, 'Gaitele' , 'Alexandru Kiritescu', 'opereta', 2,12,(select id_decor from decor where nume='bâlci'));

--durata piesei sa fie intre 1 si 5 ore
insert into piesa values(null, 'Gaitele' , 'Alexandru Kiritescu', 'comedie', 6,12,(select id_decor from decor where nume='bâlci'));

--varsta minima acceptata pentru a viziona o piesa sa fie in lista de valori impuse ('12','13',etc)
insert into piesa values(null, 'Gaitele' , 'Alexandru Kiritescu', 'comedie', 2,8,(select id_decor from decor where nume='bâlci'));

--nume recuzita sa fie in lista de valori date ('armura cavaler', 'banca',etc.)
insert into recuzita values(null ,'sanie','lemn');

--maerialul recuzitei sa fie in cele din lista('caramida','carton',etc.)
insert into recuzita values(null ,'tribuna','carbune');

--ora de inceput a reprezentatiei sa fie intre 8 si 21
insert into reprezentatie values(null,to_date('29/12/2022','DD/MM/YYYY'),7,9,(select id_sala from sala where nume='George Bacovia'),(select id_piesa from piesa where nume='Luceafãrul'));

--ora de sfarsit a reprezentatiei sa fie intre 9 si 22
insert into reprezentatie values(null,to_date('29/12/2022','DD/MM/YYYY'),21,23,(select id_sala from sala where nume='George Bacovia'),(select id_piesa from piesa where nume='Luceafãrul'));

--numele salii sa fie intre cele din lista ('George Bacovia',etc.)
insert into sala values(null,'Ana Blandiana',1,50);

--sala sa fie pe un etaj intre 0 si 4
insert into sala values(null,'Marin Sorescu',5,50);

--capacitatea salii intre 10 si 500 persoane
insert into sala values(null,'Marin Sorescu',1,5);