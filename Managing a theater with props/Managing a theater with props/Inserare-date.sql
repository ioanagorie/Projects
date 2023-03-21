
insert into decor values(null ,'gara');
insert into decor values(null,'strada');
insert into decor values(null,'padure');
insert into decor values(null,'sala de bal');
insert into decor values(null,'scoala');
insert into decor values(null,'sufragerie');
insert into decor values(null,'bâlci');

select * from decor;

insert into recuzita values(null ,'scaun','lemn');
insert into recuzita values(null ,'pat','lemn');
insert into recuzita values(null ,'noptiera','lemn');
insert into recuzita values(null ,'gard','lemn');
insert into recuzita values(null ,'fantana','caramida');
insert into recuzita values(null ,'costum nobila','combinatie');
insert into recuzita values(null ,'costum nobil','combinatie');
insert into recuzita values(null ,'trasura','lemn');

select * from recuzita;

insert into decor_recuzita values((select id_decor from decor where nume='gara'),(select id_recuzita from recuzita where nume='costum nobila'),7,null);
insert into decor_recuzita values((select id_decor from decor where nume='gara'),(select id_recuzita from recuzita where nume='costum nobil'),7,null);
insert into decor_recuzita values((select id_decor from decor where nume='padure'),(select id_recuzita from recuzita where nume='fantana'),1,null);
insert into decor_recuzita values((select id_decor from decor where nume='bâlci'),(select id_recuzita from recuzita where nume='gard'),12,null);
insert into decor_recuzita values((select id_decor from decor where nume='sala de bal'),(select id_recuzita from recuzita where nume='costum print'),16,null);
insert into decor_recuzita values((select id_decor from decor where nume='sala de bal'),(select id_recuzita from recuzita where nume='costum printesa'),22,null);

select * from decor_recuzita;

insert into cabina values(null ,2);
insert into cabina values(null ,2);
insert into cabina values(null ,3);
insert into cabina values(null ,1);
insert into cabina values(null ,1);
insert into cabina values(null ,2);

select * from cabina;


insert into sala values(null,'Ion Creanga',2,300);
insert into sala values(null,'George Bacovia',0,100);
insert into sala values(null,'Marin Sorescu',1,50);
insert into sala values(null,'Mihai Eminescu',1,200);
insert into sala values(null,'Teodor Mazilu',2,120);

select * from sala;


insert into actor values (null,'Mãlãele','Horatiu',TO_DATE('10/05/1962','DD/MM/YYYY'),'0756789234','h_malaele@gmail.com',TO_DATE('10/05/1999','DD/MM/YYYY'),9800,'XL',1);
insert into actor values (null,'Lupu','Ada',TO_DATE('10/08/1988','DD/MM/YYYY'),'0751589239','adalupu@gmail.com',TO_DATE('11/07/2016','DD/MM/YYYY'),3800,'S',2);
insert into actor values (null,'Sava','Andrei',TO_DATE('22/02/1973','DD/MM/YYYY'),'0728990123','savaandrei@gmail.com',TO_DATE('19/11/2009','DD/MM/YYYY'),5200,'L',3);
insert into actor values (null,'Iorga','Livia',TO_DATE('19/08/1982','DD/MM/YYYY'),'0751583323','livyiorg@gmail.com',TO_DATE('11/07/2012','DD/MM/YYYY'),4600,'M',4);
insert into actor values (null,'Busuioc','Daniela',TO_DATE('22/09/1985','DD/MM/YYYY'),'0622567890','busudany@gmail.com',TO_DATE('13/06/2007','DD/MM/YYYY'),5500,'L',5);

select * from actor;

insert into piesa values(null, 'Colivia', 'Georges Feydeau', 'comedie', 2,15,(select id_decor from decor where nume='sufragerie'));
insert into piesa values(null, 'Gaitele' , 'Alexandru Kiritescu', 'comedie', 2,12,(select id_decor from decor where nume='bâlci'));
insert into piesa values(null, 'Domnul vulpe', 'Larry Gelbart', 'drama', 3,18,(select id_decor from decor where nume='padure'));
insert into piesa values(null, 'Opt femei', 'Robert Thomas ', 'comedie', 2,15,(select id_decor from decor where nume='sala de bal'));
insert into piesa values(null, 'Luceafãrul', 'Mihai Eminescu', 'comedie', 2,null,(select id_decor from decor where nume='sufragerie'));
insert into piesa values(null, 'Anna Karenina', 'Dostoevsky', 'romantic', 2,null,(select id_decor from decor where nume='gara'));
insert into piesa values(null, 'La scãldat', 'Ion Creangã', 'satira', 1,null,(select id_decor from decor where nume='padure'));

select * from piesa;

insert into contract values((select id_piesa from piesa where nume='Anna Karenina'),(select id_actor from actor where nume='Iorga' and prenume='Livia'));
insert into contract values((select id_piesa from piesa where nume='Anna Karenina'),(select id_actor from actor where nume='Sava' and prenume='Andrei'));
insert into contract values((select id_piesa from piesa where nume='Domnul vulpe'),(select id_actor from actor where nume='Sava' and prenume='Andrei'));
insert into contract values((select id_piesa from piesa where nume='Gaitele'),(select id_actor from actor where nume='Lupu' and prenume='Ada'));
insert into contract values((select id_piesa from piesa where nume='Luceafãrul'),(select id_actor from actor where nume='Lupu' and prenume='Ada'));
insert into contract values((select id_piesa from piesa where nume='Opt femei'),(select id_actor from actor where nume='Lupu' and prenume='Ada'));
insert into contract values((select id_piesa from piesa where nume='Domnul vulpe'),(select id_actor from actor where nume='Mãlãele' and prenume='Horatiu'));

select * from contract;


insert into reprezentatie values(null,to_date('29/12/2022','DD/MM/YYYY'),17,19,(select id_sala from sala where nume='George Bacovia'),(select id_piesa from piesa where nume='Luceafãrul'));
insert into reprezentatie values(null,to_date('13/10/2023','DD/MM/YYYY'),18,20,(select id_sala from sala where nume='Mihai Eminescu'),(select id_piesa from piesa where nume='Opt femei'));
insert into reprezentatie values(null,to_date('15/10/2023','DD/MM/YYYY'),12,14,(select id_sala from sala where nume='Mihai Eminescu'),(select id_piesa from piesa where nume='Opt femei'));
insert into reprezentatie values(null,to_date('15/10/2023','DD/MM/YYYY'),19,21,(select id_sala from sala where nume='Teodor Mazilu'),(select id_piesa from piesa where nume='Luceafãrul'));
insert into reprezentatie values(null,to_date('16/10/2023','DD/MM/YYYY'),19,21,(select id_sala from sala where nume='George Bacovia'),(select id_piesa from piesa where nume='Gaitele'));


select * from reprezentatie;
