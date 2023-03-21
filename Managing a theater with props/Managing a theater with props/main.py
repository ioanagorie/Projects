import cx_Oracle
from tkinter import *

user = "bd038"
pw = "bd038"
dsn = "bd-dc.cs.tuiasi.ro:1539/orcl"


def init_app():
    global field
    global root
    root = Tk()

    root.title("Gestionare teatru cu recuzita")
    root['bg'] = 'lightsalmon'

    field = Text(root, wrap=WORD, bg='ivory')
    field.grid(row=2, column=4, rowspan=9,columnspan=10, padx=5, pady=5)

    OPTIONS = [
        "Please select a table",
        "Actor",
        "Cabina",
        "Decor",
        "Piesa",
        "Recuzita"
    ]
    global variable
    variable = StringVar(root)
    variable.set(OPTIONS[0])

    list_table = OptionMenu(root, variable, *OPTIONS)
    list_table.grid(row=0, column=1, padx=5, pady=5)
    button = Button(root, text="Change table", command=button_click)
    button.grid(row=0, column=2, padx=5, pady=5)


    OPTIONS2 = [
        "Please select an option",
        "Program",
        "Montaj Decor",
        "Contracte",
        "Distribuția"
    ]

    global variable2
    variable2 = StringVar(root)
    variable2.set(OPTIONS2[0])

    list_table2 = OptionMenu(root, variable2, *OPTIONS2)
    list_table2.grid(row=0, column=5, padx=5, pady=5)
    button2 = Button(root, text="Change Option", command=button_click2)
    button2.grid(row=0, column=6, padx=5, pady=5)

    button3 = Button(root, text="Erase Field", command=button_click3)
    button3.grid(row=0, column=7, padx=5, pady=5)

    global line_delete_button, column_delete_button
    line_delete_button = 12
    column_delete_button=6

    global line_read_button, column_read_button
    line_read_button = 1
    column_read_button = 6


    root.mainloop()

dictionar_actori = dict()
dictionar_cabine = dict()


def button_click():
    def clear_previous_table():
        field.delete("1.0","end")
        for i in range(1, 13):
            for j in range(0, 4):
                color_label = Label(root, bg='lightsalmon', width=21, height=2)
                color_label.grid(row=i, column=j, padx=5, pady=5, sticky=W)
        for i in [1, 11, 12]:
            for j in [5, 6]:
                color_label = Label(root, bg='lightsalmon', width=37, height=2)
                color_label.grid(row=i, column=j, padx=5, pady=5, sticky=W)
    clear_previous_table()
    selected_table = variable.get()
    global previous_table
    if selected_table == "Actor":
        paint_actor()
    if selected_table == "Cabina":
        paint_cabina()
    if selected_table == "Decor":
        paint_decor()
    if selected_table == "Piesa":
        paint_piesa()
    if selected_table == "Recuzita":
        paint_recuzita()

def button_click2():
    def clear_previous_table():
        field.delete("1.0", "end")
        for i in range(1, 13):
            for j in range(0, 4):
                color_label = Label(root, bg='lightsalmon', width=21, height=2)
                color_label.grid(row=i, column=j, padx=5, pady=5, sticky=W)
        for i in [1, 11, 12]:
            for j in [5, 6]:
                color_label = Label(root, bg='lightsalmon', width=37, height=2)
                color_label.grid(row=i, column=j, padx=5, pady=5, sticky=W)
    clear_previous_table()
    selected_option = variable2.get()
    global previous_table
    if selected_option == "Program":
        paint_program()
    if selected_option == "Montaj Decor":
        paint_montaj_decor()
    if selected_option == "Contracte":
        paint_contracte()
    if selected_option == "Distribuția":
        paint_distributia()

def button_click3():
    field.delete("1.0", "end")


def paint_program():
    print('Programul')
    label_terminal = Label(root, text="Read", bg='lightsalmon', font='Corbel 20')
    label_terminal.grid(row=1, column=5, padx=5, pady=5)

    def read_program(event):
        curs = conn.cursor()
        field.insert(END, "\n\t\t\tProgram: \n\n")
        curs.execute(
            '''SELECT 'Reprezentatia nr. ' || id_reprezentatie || ':' || ' Pe data de ' || r.zi || ' de la ora '||
             r.ora_inceput || ' pana la ' || r.ora_sfarsit || ' se va juca piesa ' || p.nume || ' scrisa de ' || 
             p.autor || '. Spectacolul va avea loc in sala ' || s.nume || ' pe care o gasiti la etajul ' || s.etaj ||
              ' in cladirea teatrului nostru.' FROM reprezentatie r, sala s, piesa p WHERE r.id_piesa= p.id_piesa AND 
              r.id_sala=s.id_sala order by zi''')
        res = curs.fetchall()
        for row in res:
            field.insert(END, row)
            field.insert(END, "\n\n\n")
        curs.close()

    btn_read_program = Button(root, text="Read program", bg="firebrick2")
    btn_read_program.grid(row=1, column=6, padx=5, pady=5, sticky=W)
    btn_read_program.bind("<Button>", read_program)

def paint_montaj_decor():
    print('Montajul Decorului')
    label_terminal = Label(root, text="Read", bg='lightsalmon', font='Corbel 20')
    label_terminal.grid(row=1, column=5, padx=5, pady=5)
    global btn_read_actor

    def read_montaj_decor(event):
        curs = conn.cursor()
        field.insert(END, "\n\t\t\tMontaj Decor: \n\n")
        curs.execute(
            "SELECT 'Pentru decorul '  || d.nume || ' este nevoie de ' || r.nume FROM decor_recuzita dr, recuzita r, decor d where dr.decor_id_decor= d.id_decor and dr.recuzita_id_recuzita= r.id_recuzita ORDER BY d.nume")
        res = curs.fetchall()
        for row in res:
            field.insert(END, row)
            field.insert(END, "\n\n\n")
        curs.close()


    btn_read_montaj_decor = Button(root, text="Read Montaj Decor", bg="firebrick2")
    btn_read_montaj_decor.grid(row=1, column=6, padx=5, pady=5, sticky=W)
    btn_read_montaj_decor.bind("<Button>", read_montaj_decor)

def paint_contracte():
    print('Contracte')
    label_terminal = Label(root, text="Read", bg='lightsalmon', font='Corbel 20')
    label_terminal.grid(row=1, column=5, padx=5, pady=5)
    global btn_read_actor

    def read_contracte(event):
        curs = conn.cursor()
        field.insert(END, "\n\t\t\tContracte: \n\n")
        curs.execute(
            "SELECT 'Contract :'||' Actorul '|| a. prenume || ' ' || a.nume || ' a semnat un contract pentru a juca in piesa ' || p.nume FROM contract c, actor a, piesa p where c.actor_id_actor=a.id_actor and c.piesa_id_piesa = p.id_piesa")
        res = curs.fetchall()
        for row in res:
            field.insert(END, row)
            field.insert(END, "\n\n\n")
        curs.close()


    btn_read_contracte = Button(root, text="Read Contracte", bg="firebrick2")
    btn_read_contracte.grid(row=1, column=6, padx=5, pady=5, sticky=W)
    btn_read_contracte.bind("<Button>", read_contracte)

def paint_distributia():
    print('Distribuția')
    label_terminal = Label(root, text="Read", bg='lightsalmon', font='Corbel 20')
    label_terminal.grid(row=1, column=5, padx=5, pady=5)
    global btn_read_actor

    def read_distributia(event):
        curs = conn.cursor()
        field.insert(END, "\n\t\t\tDistributia: \n\n")
        curs.execute(
            "SELECT 'In piesa '|| p.nume|| ' joaca ' || a.nume || ' ' ||a.prenume ||'.' FROM contract c, actor a, piesa p where c.actor_id_actor=a.id_actor and c.piesa_id_piesa = p.id_piesa Order by id_piesa")
        res = curs.fetchall()
        for row in res:
            field.insert(END, row)
            field.insert(END, "\n\n\n")
        curs.close()


    btn_read_distributia = Button(root, text="Read Distributia", bg="firebrick2")
    btn_read_distributia.grid(row=1, column=6, padx=5, pady=5, sticky=W)
    btn_read_distributia.bind("<Button>", read_distributia)


def update_dp_actor_delete():
    global dp_delete_actor
    global variable_delete_actors

    list_actori = list()
    dictionar_actori.clear()
    curs = conn.cursor()
    curs.execute("SELECT id_actor, nume, prenume FROM actor")
    res = curs.fetchall()
    for row in res:
        string_actor = str(row[1]) + " " + str(row[2])
        dictionar_actori[string_actor] = int(row[0])
        list_actori.append(string_actor)

    variable_delete_actors = StringVar(root)
    variable_delete_actors.set(list_actori[0])


    linie_actor = 11
    paint_label("Delete Actor with ID", linie_actor, 5)
    dp_delete_actor = OptionMenu(root, variable_delete_actors, *list_actori)
    dp_delete_actor.grid(row=linie_actor, column=6, padx=5, pady=5)


def update_dp_actor_update():
    global dp_select_actor
    global dp_update_cabina_actor

    global variable_select_actor
    global variable_update_cabina_actor

    list_actori = list()
    dictionar_actori.clear()
    curs = conn.cursor()
    curs.execute("SELECT id_actor, nume, prenume FROM actor")
    res = curs.fetchall()
    for row in res:
        string_actor = str(row[1]) + " " + str(row[2])
        dictionar_actori[string_actor] = int(row[0])
        list_actori.append(string_actor)

    variable_select_actor = StringVar(root)
    variable_select_actor.set(list_actori[0])

    linie_actor = 2
    paint_label("Id actor", linie_actor, 2)
    dp_select_actor = OptionMenu(root, variable_select_actor, *list_actori)
    dp_select_actor.grid(row=linie_actor, column=3, padx=5, pady=5)

    linie_actor = 11

    list_cabine = list()
    dictionar_cabine.clear()
    curs = conn.cursor()
    curs.execute("SELECT id_cabina, etaj FROM cabina")
    res = curs.fetchall()
    string_cabina = "New dress room"
    dictionar_cabine[string_cabina] = -1
    list_cabine.append(string_cabina)

    for row in res:
        string_cabina = f"id: {str(row[0])} etaj {str(row[1])}"
        dictionar_cabine[string_cabina] = int(row[0])
        list_cabine.append(string_cabina)

    variable_update_cabina_actor = StringVar(root)
    variable_update_cabina_actor.set(list_cabine[0])
    paint_label("Id cabina", linie_actor, 2)
    dp_update_cabina_actor = OptionMenu(root, variable_update_cabina_actor, *list_cabine)
    dp_update_cabina_actor.grid(row=linie_actor, column=3, padx=5, pady=5)

def update_dp_actor_create():
    global dp_create_cabina_actor
    global variable_create_cabina_actor
    list_cabine = list()
    dictionar_cabine.clear()
    curs = conn.cursor()
    curs.execute("SELECT id_cabina, etaj FROM cabina")
    res = curs.fetchall()
    string_cabina = "New dress room"
    dictionar_cabine[string_cabina] = -1
    list_cabine.append(string_cabina)
    for row in res:
        string_cabina = f"id: {str(row[0])} etaj {str(row[1])}"
        dictionar_cabine[string_cabina] = int(row[0])
        list_cabine.append(string_cabina)
    variable_create_cabina_actor = StringVar(root)
    variable_create_cabina_actor.set(list_cabine[0])
    linie_actor = 10
    paint_label("Id cabina", linie_actor, 0)
    dp_create_cabina_actor = OptionMenu(root, variable_create_cabina_actor, *list_cabine)
    dp_create_cabina_actor.grid(row=linie_actor, column=1, padx=5, pady=5)



def paint_actor():
    global entry_actor_id_actor, entry_actor_nume, entry_actor_prenume, entry_actor_data_nastere, entry_actor_telefon
    global entry_actor_email, entry_actor_data_angajare, entry_actor_salariu, entry_actor_marime_haine
    global btn_inserare_actor

    #Create
    linie_actor = 1
    paint_label("Create", linie_actor, 0)
    linie_actor += 1
    paint_label("Nume", linie_actor, 0)
    entry_actor_nume = Entry(root)
    entry_actor_nume.grid(row=linie_actor, column=1, padx=5, pady=5)
    linie_actor += 1

    paint_label("Prenume", linie_actor, 0)
    entry_actor_prenume = Entry(root)
    entry_actor_prenume.grid(row=linie_actor, column=1, padx=5, pady=5)
    linie_actor += 1

    paint_label("Data Nastere", linie_actor, 0)
    entry_actor_data_nastere = Entry(root)
    entry_actor_data_nastere.grid(row=linie_actor, column=1, padx=5, pady=5)
    linie_actor += 1

    paint_label("Telefon", linie_actor, 0)
    entry_actor_telefon = Entry(root)
    entry_actor_telefon.grid(row=linie_actor, column=1, padx=5, pady=5)
    linie_actor += 1

    paint_label("Email", linie_actor, 0)
    entry_actor_email = Entry(root)
    entry_actor_email.grid(row=linie_actor, column=1, padx=5, pady=5)
    linie_actor += 1

    paint_label("Angajare", linie_actor, 0)
    entry_actor_data_angajare = Entry(root)
    entry_actor_data_angajare.grid(row=linie_actor, column=1, padx=5, pady=5)
    linie_actor += 1

    paint_label("Salariu", linie_actor, 0)
    entry_actor_salariu = Entry(root)
    entry_actor_salariu.grid(row=linie_actor, column=1, padx=5, pady=5)
    linie_actor += 1

    paint_label("Marime haine", linie_actor, 0)
    entry_actor_marime_haine = Entry(root)
    entry_actor_marime_haine.grid(row=linie_actor, column=1, padx=5, pady=5)
    linie_actor += 1
    linie_actor += 1

    update_dp_actor_create()

    def create_actor(event):
        curs = conn.cursor()
        string_querry = "INSERT INTO actor VALUES(NULL"
        nume = entry_actor_nume.get()
        if nume == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{nume}'"
        prenume = entry_actor_prenume.get()
        if prenume == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{prenume}'"

        data_nastere = entry_actor_data_nastere.get()
        if data_nastere == "":
            string_querry += ", NULL"
        else:
            string_querry += f", TO_DATE('{data_nastere}', 'DD/MM/YYYY')"

        telefon = entry_actor_telefon.get()
        if telefon == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{telefon}'"

        email = entry_actor_email.get()
        if email == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{email}'"

        data_angajare = entry_actor_data_angajare.get()
        if data_angajare == "":
            string_querry += ", NULL"
        else:
            data_angajare = f"TO_DATE('{data_angajare}', 'DD/MM/YYYY')"
            string_querry += f", {data_angajare}"

        salariu = entry_actor_salariu.get()
        if salariu == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{salariu}'"

        marime_haine = entry_actor_marime_haine.get()
        if marime_haine == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{marime_haine}'"

        id_cabina = variable_create_cabina_actor.get()
        if dictionar_cabine[id_cabina] == -1:
            string_querry += ", NULL"
        else:
            string_querry += f", '{dictionar_cabine[id_cabina]}'"
        string_querry += ")"
        try:
            print(string_querry)
            curs.execute(string_querry)
            curs.execute('commit')
            field.insert(END, "[SUCCES]: Actorul a fost inserat!\n")
            dp_delete_actor.destroy()
            dp_update_cabina_actor.destroy()
            dp_create_cabina_actor.destroy()
            update_dp_actor_delete()
            update_dp_actor_create()
            update_dp_actor_update()
        except Exception as e:
            print(e)
            field.insert(END, "[EROARE]: Informatii actor incorecte: Verificati datele din nou\n")
        curs.close()

    btn_inserare_actor = Button(root, text="Inserare actor", bg="firebrick2")
    btn_inserare_actor.grid(row=linie_actor, column=1, padx=5, pady=5, sticky=W)
    btn_inserare_actor.bind("<Button>", create_actor)
    linie_actor += 1

    #Update
    global entry_actor_u_id_actor, entry_actor_u_nume, entry_actor_u_prenume, entry_actor_u_data_nastere, entry_actor_u_telefon
    global entry_actor_u_email, entry_actor_u_data_angajare, entry_actor_u_salariu, entry_actor_u_marime_haine
    global entry_actor_u_id_cabina
    global btn_update_actor


    linie_actor = 1
    paint_label("Update", linie_actor, 2)
    linie_actor += 2



    paint_label("Nume", linie_actor, 2)
    entry_actor_u_nume = Entry(root)
    entry_actor_u_nume.grid(row=linie_actor, column=3, padx=5, pady=5)
    linie_actor += 1

    paint_label("Prenume", linie_actor, 2)
    entry_actor_u_prenume = Entry(root)
    entry_actor_u_prenume.grid(row=linie_actor, column=3, padx=5, pady=5)
    linie_actor += 1

    paint_label("Data Nastere", linie_actor, 2)
    entry_actor_u_data_nastere = Entry(root)
    entry_actor_u_data_nastere.grid(row=linie_actor, column=3, padx=5, pady=5)
    linie_actor += 1

    paint_label("Telefon", linie_actor, 2)
    entry_actor_u_telefon = Entry(root)
    entry_actor_u_telefon.grid(row=linie_actor, column=3, padx=5, pady=5)
    linie_actor += 1

    paint_label("Email", linie_actor, 2)
    entry_actor_u_email = Entry(root)
    entry_actor_u_email.grid(row=linie_actor, column=3, padx=5, pady=5)
    linie_actor += 1

    paint_label("Angajare", linie_actor, 2)
    entry_actor_u_data_angajare = Entry(root)
    entry_actor_u_data_angajare.grid(row=linie_actor, column=3, padx=5, pady=5)
    linie_actor += 1

    paint_label("Salariu", linie_actor, 2)
    entry_actor_u_salariu = Entry(root)
    entry_actor_u_salariu.grid(row=linie_actor, column=3, padx=5, pady=5)
    linie_actor += 1

    paint_label("Marime haine", linie_actor, 2)
    entry_actor_u_marime_haine = Entry(root)
    entry_actor_u_marime_haine.grid(row=linie_actor, column=3, padx=5, pady=5)
    linie_actor += 1
    update_dp_actor_update()


    def update_actor(event):
        minim_un_element = 0
        curs = conn.cursor()
        string_querry = "UPDATE actor SET "
        nume = entry_actor_u_nume.get()
        if nume != "":
            string_querry += f" nume= '{nume}' "
            minim_un_element = 1
        prenume = entry_actor_u_prenume.get()
        if prenume != "":
            if(minim_un_element == 1):
                string_querry += f", prenume= '{prenume}'"
            else:
                string_querry += f" prenume= '{prenume}'"
                minim_un_element = 1

        data_nastere = entry_actor_u_data_nastere.get()
        if data_nastere != "":
            if (minim_un_element == 1):
               string_querry += f", data_nastere = TO_DATE('{data_nastere}', 'DD/MM/YYYY')"
            else:
                string_querry += f" data_nastere = TO_DATE('{data_nastere}', 'DD/MM/YYYY')"
                minim_un_element = 1

        telefon = entry_actor_u_telefon.get()
        if telefon != "":
            if (minim_un_element == 1):
                string_querry += f", telefon= '{telefon}'"
            else:
                string_querry += f" telefon= '{telefon}'"
                minim_un_element = 1

        email = entry_actor_u_email.get()
        if email != "":
            if (minim_un_element == 1):
                string_querry += f", email = '{email}'"
            else:
                string_querry += f" email = '{email}'"
                minim_un_element = 1

        data_angajare = entry_actor_u_data_angajare.get()
        if data_angajare != "":
            if (minim_un_element == 1):
                string_querry += f" , data_angajare = TO_DATE('{data_angajare}', 'DD/MM/YYYY')"
            else:
                string_querry += f" data_angajare = TO_DATE('{data_angajare}', 'DD/MM/YYYY')"
                minim_un_element = 1

        salariu = entry_actor_u_salariu.get()
        if salariu != "":
            if (minim_un_element == 1):
                string_querry += f", salariu = '{salariu}'"
            else:
                string_querry += f" salariu = '{salariu}'"
                minim_un_element = 1

        marime_haine = entry_actor_u_marime_haine.get()
        if marime_haine != "":
            if (minim_un_element == 1):
                string_querry += f", marime_haine= '{marime_haine}'"
            else:
                string_querry += f" marime_haine = '{marime_haine}'"
                minim_un_element=1


        id_cabina = variable_update_cabina_actor.get()
        if dictionar_cabine[id_cabina] != -1:
            if (minim_un_element == 1):
                string_querry += f", id_cabina = {dictionar_cabine[id_cabina]}"
            else:
               string_querry += f" id_cabina = {dictionar_cabine[id_cabina]}"

        id_actor = variable_select_actor.get()
        string_querry += f" WHERE id_actor = {dictionar_actori[id_actor]}"
        try:
            print(string_querry)
            curs.execute(string_querry)
            curs.execute('commit')
            field.insert(END, "[SUCCES]: Datele Actorului au fost actualizate cu succes!\n")
            dp_delete_actor.destroy()
            dp_update_cabina_actor.destroy()
            dp_create_cabina_actor.destroy()
            update_dp_actor_delete()
            update_dp_actor_create()
            update_dp_actor_update()
        except Exception as e:
            print(e)
            field.insert(END, "[EROARE]: Informatii actor incorecte: Verificati datele din nou\n")
        curs.close()
    linie_actor += 1
    btn_update_actor = Button(root, text="Update actor", bg="firebrick2")
    btn_update_actor.grid(row=linie_actor, column=3, padx=5, pady=5, sticky=W)
    btn_update_actor.bind("<Button>", update_actor)
    linie_actor += 1



    #Read
    label_terminal = Label(root, text="Read", bg='lightsalmon', font='Corbel 20')
    label_terminal.grid(row=1, column=5, padx=5, pady=5)
    global btn_read_actor
    def read_actor(event):
        curs = conn.cursor()
        field.insert(END, "\n\t\t\tActori\n\n")
        curs.execute(
            "SELECT id_actor, nume, prenume, data_nastere, telefon, email, data_angajare, salariu, marime_haine, id_cabina FROM actor")
        res = curs.fetchall()
        for row in res:
            field.insert(END, "ID: " + str(row[0]) + ", Nume: " + str(row[1]) + ", Prenume: " + str(
                row[2]) + ", Data nastere: " + str(row[3]) + ", Telefon: " + str(row[4]) + ", Email: " + str(
                row[5])  + ", Data Angajare: " + str(row[6]) + ", Salariu: " + str(row[7]) + ", Marime haine: " + str(row[8]) +
                         ", Id cabina: " + str(row[9]) + "\n\n")
        curs.close()

    btn_read_actor = Button(root, text="Read actori", bg="firebrick2")
    btn_read_actor.grid(row=1, column=6, padx=5, pady=5, sticky=W)
    btn_read_actor.bind("<Button>", read_actor)

    #Delete
    global btn_delete_actor
    update_dp_actor_delete()
    def delete_actor(event):
        curs = conn.cursor()
        try:
            print(dictionar_actori[variable_delete_actors.get()])
            querry = "DELETE FROM actor where id_actor = '%s'" % (dictionar_actori[variable_delete_actors.get()])
            curs.execute(querry)
            curs.execute('commit')
            field.insert(END, "[SUCCES]: Actorul a fost sters!\n")
        except Exception as e:
            print(e)
            field.insert(END, "[EROARE]: Id-ul nu este corect. Verificati sa existe\n")
        dp_delete_actor.destroy()
        dp_update_cabina_actor.destroy()
        dp_create_cabina_actor.destroy()
        update_dp_actor_delete()
        update_dp_actor_create()
        update_dp_actor_update()
    btn_delete_actor = Button(root, text="Delete actor", bg="firebrick2")
    btn_delete_actor.grid(row=linie_actor - 1, column=6, padx=5, pady=5, sticky=W)
    btn_delete_actor.bind("<Button>", delete_actor)
    linie_actor += 1

def update_drop_down_delete_cabina():
    global list_delete_cabina
    global dictionar_cabine

    list_cabine = list()
    dictionar_cabine = dict()
    curs = conn.cursor()
    curs.execute("SELECT id_cabina, etaj FROM cabina ")
    res = curs.fetchall()
    for row in res:
        string_cabina = "id:"+str(row[0]) + " etaj:" + str(row[1])
        dictionar_cabine[string_cabina] = int(row[0])
        list_cabine.append(string_cabina)

    global variable_delete_cabina
    variable_delete_cabina = StringVar(root)
    variable_delete_cabina.set(list_cabine[0])

    linie_cabina = 11
    paint_label("Delete Cabina with ID", linie_cabina, 5)
    list_delete_cabina = OptionMenu(root, variable_delete_cabina, *list_cabine)
    list_delete_cabina.grid(row=linie_cabina, column=6, padx=5, pady=5)

def update_drop_down_update_cabina():
    global list_update_cabina
    global dictionar_cabine

    list_cabine = list()
    dictionar_cabine.clear()
    curs = conn.cursor()
    curs.execute("SELECT id_cabina, etaj FROM cabina ")

    res = curs.fetchall()
    for row in res:
        string_cabina = "id:"+str(row[0]) + " etaj:" + str(row[1])
        dictionar_cabine[string_cabina] = int(row[0])
        list_cabine.append(string_cabina)

    global variable_update_cabina
    variable_update_cabina = StringVar(root)
    variable_update_cabina.set(list_cabine[0])

    linie_cabina = 2
    paint_label("Id cabina", linie_cabina, 2)
    list_update_cabina = OptionMenu(root, variable_update_cabina, *list_cabine)
    list_update_cabina.grid(row=linie_cabina, column=3, padx=5, pady=5)

def paint_cabina():
    global entry_cabina_etaj

    #Create
    linie_cabina = 1
    paint_label("Create", linie_cabina, 0)
    linie_cabina += 1
    paint_label("Etaj", linie_cabina, 0)
    entry_cabina_etaj = Entry(root)
    entry_cabina_etaj.grid(row=linie_cabina, column=1, padx=5, pady=5)
    linie_cabina +=1

    def create_cabina(event):
        curs = conn.cursor()
        string_querry = "INSERT INTO cabina VALUES(NULL"
        etaj = entry_cabina_etaj.get()
        if etaj == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{etaj}'"
        string_querry += ")"
        try:
            print(string_querry)
            curs.execute(string_querry)
            curs.execute('commit')
            field.insert(END, "[SUCCES]: Cabina a fost inserata!\n")
            list_delete_cabina.destroy()
            list_update_cabina.destroy()
            update_drop_down_delete_cabina()
            update_drop_down_update_cabina()
        except Exception as e:
            print(e)
            field.insert(END, "[EROARE]: Informatii cabina incorecte: Verificati datele din nou\n")
        curs.close()

    btn_inserare_cabina = Button(root, text="Inserare cabina", bg="firebrick2")
    btn_inserare_cabina.grid(row=linie_cabina, column=1, padx=5, pady=5, sticky=W)
    btn_inserare_cabina.bind("<Button>", create_cabina)
    linie_cabina += 1

    # Update
    global entry_cabina_u_id_cabina, entry_cabina_u_etaj
    global btn_update_cabina

    linie_cabina = 1
    paint_label("Update", linie_cabina, 2)
    linie_cabina += 1
    linie_cabina += 1

    paint_label("Etaj", linie_cabina, 2)
    entry_cabina_u_etaj = Entry(root)
    entry_cabina_u_etaj.grid(row=linie_cabina, column=3, padx=5, pady=5)
    linie_cabina += 1

    update_drop_down_update_cabina()

    def update_cabina(event):
        curs = conn.cursor()
        string_querry = "UPDATE cabina SET "
        etaj = entry_cabina_u_etaj.get()
        if etaj != "":
            string_querry += f" etaj= '{etaj}' "
        id_cabina = variable_update_cabina.get()
        string_querry += f" WHERE id_cabina = '{dictionar_cabine[id_cabina]}'"
        print(string_querry)
        try:
            print(string_querry)
            curs.execute(string_querry)
            curs.execute('commit')
            field.insert(END, "[SUCCES]: Datele Cabinei au fost actualizate cu succes!\n")
            list_delete_cabina.destroy()
            list_update_cabina.destroy()
            update_drop_down_delete_cabina()
            update_drop_down_update_cabina()
        except Exception as e:
            print(e)
            field.insert(END, "[EROARE]: Informatii cabina incorecte: Verificati datele din nou\n")
        curs.close()

    btn_update_cabina = Button(root, text="Update cabina", bg="firebrick2")
    btn_update_cabina.grid(row=linie_cabina, column=3, padx=5, pady=5, sticky=W)
    btn_update_cabina.bind("<Button>", update_cabina)
    linie_cabina += 1

    # Read
    label_terminal = Label(root, text="Read", bg='lightsalmon', font='Corbel 20')
    label_terminal.grid(row=1, column=5, padx=5, pady=5)
    global btn_read_actor

    def read_cabina(event):
        curs = conn.cursor()
        field.insert(END, "\n\t\t\tCabine\n\n")
        curs.execute(
            "SELECT id_cabina, etaj FROM cabina")
        res = curs.fetchall()
        for row in res:
            field.insert(END, "ID: " + str(row[0]) + ", Etaj: " + str(row[1])  + "\n\n")
        curs.close()

    btn_read_cabina = Button(root, text="Read cabina", bg="firebrick2")
    btn_read_cabina.grid(row=1, column=6, padx=5, pady=5, sticky=W)
    btn_read_cabina.bind("<Button>", read_cabina)

    # Delete

    global btn_delete_cabina
    update_drop_down_delete_cabina()

    def delete_cabina(event):
        curs = conn.cursor()
        try:
            print(dictionar_cabine[variable_delete_cabina.get()])
            querry = "DELETE FROM cabina where id_cabina = '%s'" % (dictionar_cabine[variable_delete_cabina.get()])
            curs.execute(querry)
            curs.execute('commit')
            field.insert(END, "[SUCCES]: Cabina a fost stearsa!\n")
        except Exception as e:
            print(e)
            field.insert(END, "[EROARE]: Id-ul nu este corect. Verificati sa existe\n")
        list_delete_cabina.destroy()
        update_drop_down_delete_cabina()

    btn_delete_cabina = Button(root, text="Delete cabina", bg="firebrick2")
    btn_delete_cabina.grid(row=line_delete_button, column=column_delete_button, padx=5, pady=5, sticky=W)
    btn_delete_cabina.bind("<Button>", delete_cabina)
    linie_cabina += 1



def paint_decor():
    global entry_decor_nume

    #Create
    linie_decor = 1
    paint_label("Create", linie_decor, 0)
    linie_decor += 1
    paint_label("Nume", linie_decor, 0)
    entry_decor_nume = Entry(root)
    entry_decor_nume.grid(row=linie_decor, column=1, padx=5, pady=5)
    linie_decor +=1

    def create_decor(event):
        curs = conn.cursor()
        string_querry = "INSERT INTO decor VALUES(NULL"
        nume = entry_decor_nume.get()
        if nume == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{nume}'"
        string_querry += ")"
        try:
            print(string_querry)
            curs.execute(string_querry)
            curs.execute('commit')
            field.insert(END, "[SUCCES]: Decorul a fost inserat!\n")
            list_delete_decor.destroy()
            update_drop_down_delete()
        except Exception as e:
            print(e)
            field.insert(END, "[EROARE]: Informatii decor incorecte: Verificati datele din nou\n")
        curs.close()

    btn_inserare_decor = Button(root, text="Inserare decor", bg="firebrick2")
    btn_inserare_decor.grid(row=linie_decor, column=1, padx=5, pady=5, sticky=W)
    btn_inserare_decor.bind("<Button>", create_decor)
    linie_decor += 1

    # Update
    global entry_decor_u_id_decor, entry_decor_u_nume
    global btn_update_decor

    linie_decor = 1
    paint_label("Update", linie_decor, 2)
    linie_decor += 1

    paint_label("Id decor", linie_decor, 2)
    entry_decor_u_id_decor = Entry(root)
    entry_decor_u_id_decor.grid(row=linie_decor, column=3, padx=5, pady=5)
    linie_decor += 1

    paint_label("Nume", linie_decor, 2)
    entry_decor_u_nume = Entry(root)
    entry_decor_u_nume.grid(row=linie_decor, column=3, padx=5, pady=5)
    linie_decor += 1

    def update_decor(event):
        curs = conn.cursor()
        string_querry = "UPDATE decor SET "
        nume = entry_decor_u_nume.get()
        if nume != "":
            string_querry += f" nume= '{nume}' "
        id_decor = entry_decor_u_id_decor.get()
        string_querry += f" WHERE id_decor = '{id_decor}'"
        print(string_querry)
        try:
            print(string_querry)
            curs.execute(string_querry)
            curs.execute('commit')
            field.insert(END, "[SUCCES]: Datele decorului au fost actualizate cu succes!\n")
            list_delete_decor.destroy()
            update_drop_down_delete()
        except Exception as e:
            print(e)
            field.insert(END, "[EROARE]: Informatii cabina incorecte: Verificati datele din nou\n")
        curs.close()

    btn_update_decor= Button(root, text="Update decor", bg="firebrick2")
    btn_update_decor.grid(row=linie_decor, column=3, padx=5, pady=5, sticky=W)
    btn_update_decor.bind("<Button>", update_decor)
    linie_decor += 1

    # Read
    label_terminal = Label(root, text="Read", bg='lightsalmon', font='Corbel 20')
    label_terminal.grid(row=1, column=5, padx=5, pady=5)
    global btn_read_decor

    def read_cabina(event):
        curs = conn.cursor()
        field.insert(END, "\n\t\t\tCabine\n\n")
        curs.execute(
            "SELECT id_decor, nume FROM decor")
        res = curs.fetchall()
        for row in res:
            field.insert(END, "ID: " + str(row[0]) + ", Nume: " + str(row[1])  + "\n\n")
        curs.close()

    btn_read_decor = Button(root, text="Read decor", bg="firebrick2")
    btn_read_decor.grid(row=1, column=6, padx=5, pady=5, sticky=W)
    btn_read_decor.bind("<Button>", read_cabina)

    # Delete
    def update_drop_down_delete():
        global list_delete_decor
        global dictionar_decor

        list_decor = list()
        dictionar_decor = dict()
        curs = conn.cursor()
        curs.execute("SELECT id_decor, nume FROM decor ")
        res = curs.fetchall()
        for row in res:
            string_decor = " "+str(row[0]) + " " + str(row[1])
            dictionar_decor[string_decor] = int(row[0])
            list_decor.append(string_decor)

        global variable_delete_decor
        variable_delete_decor = StringVar(root)
        variable_delete_decor.set(list_decor[0])

        linie_decor = 11
        paint_label("Delete Cabina with ID", linie_decor, 5)
        list_delete_decor = OptionMenu(root, variable_delete_decor, *list_decor)
        list_delete_decor.grid(row=linie_decor, column=6, padx=5, pady=5)

        linie_decor += 1

    global btn_delete_decor
    update_drop_down_delete()

    def delete_decor(event):
        curs = conn.cursor()
        try:
            print(dictionar_decor[variable_delete_decor.get()])
            querry = "DELETE FROM decor where id_decor = '%s'" % (dictionar_decor[variable_delete_decor.get()])
            curs.execute(querry)
            curs.execute('commit')
            field.insert(END, "[SUCCES]: Decor a fost stears!\n")
        except Exception as e:
            print(e)
            field.insert(END, "[EROARE]: Id-ul nu este corect. Verificati sa existe\n")
        list_delete_decor.destroy()
        update_drop_down_delete()

    btn_delete_decor = Button(root, text="Delete decor", bg="firebrick2")
    btn_delete_decor.grid(row=line_delete_button, column=column_delete_button, padx=5, pady=5, sticky=W)
    btn_delete_decor.bind("<Button>", delete_decor)
    linie_decor += 1

def paint_piesa():
    global entry_piesa_nume, entry_piesa_autor, entry_piesa_gen, entry_piesa_durata, entry_piesa_varsta_minima
    global entry_piesa_id_decor

    #Create
    linie_piesa = 1
    paint_label("Create", linie_piesa, 0)
    linie_piesa += 1
    paint_label("Nume", linie_piesa, 0)
    entry_piesa_nume = Entry(root)
    entry_piesa_nume.grid(row=linie_piesa, column=1, padx=5, pady=5)
    linie_piesa +=1
    paint_label("Autor", linie_piesa, 0)
    entry_piesa_autor = Entry(root)
    entry_piesa_autor.grid(row=linie_piesa, column=1, padx=5, pady=5)
    linie_piesa +=1
    paint_label("Gen", linie_piesa, 0)
    entry_piesa_gen = Entry(root)
    entry_piesa_gen.grid(row=linie_piesa, column=1, padx=5, pady=5)
    linie_piesa += 1
    paint_label("Durata", linie_piesa, 0)
    entry_piesa_durata = Entry(root)
    entry_piesa_durata.grid(row=linie_piesa, column=1, padx=5, pady=5)
    linie_piesa += 1
    paint_label("Varsta Min", linie_piesa, 0)
    entry_piesa_varsta_minima = Entry(root)
    entry_piesa_varsta_minima.grid(row=linie_piesa, column=1, padx=5, pady=5)
    linie_piesa += 1
    paint_label("Id Decor", linie_piesa, 0)
    entry_piesa_id_decor = Entry(root)
    entry_piesa_id_decor.grid(row=linie_piesa, column=1, padx=5, pady=5)
    linie_piesa += 1

    def create_piesa(event):
        curs = conn.cursor()
        string_querry = "INSERT INTO piesa VALUES(NULL"
        nume = entry_piesa_nume.get()
        if nume == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{nume}'"
        autor = entry_piesa_autor.get()
        if autor == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{autor}'"
        gen = entry_piesa_gen.get()
        if gen == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{gen}'"
        durata = entry_piesa_durata.get()
        if durata == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{durata}'"
        varsta_minima = entry_piesa_varsta_minima.get()
        if gen == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{varsta_minima}'"
        id_decor = entry_piesa_id_decor.get()
        if id_decor == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{id_decor}'"
        string_querry += ")"
        try:
            print(string_querry)
            curs.execute(string_querry)
            curs.execute('commit')
            field.insert(END, "[SUCCES]: Piesa a fost inserata!\n")
            list_delete_piesa.destroy()
            update_drop_down_delete()
        except Exception as e:
            print(e)
            field.insert(END, "[EROARE]: Informatii piesa incorecte: Verificati datele din nou\n")
        curs.close()

    btn_inserare_piesa = Button(root, text="Inserare piesa", bg="firebrick2")
    btn_inserare_piesa.grid(row=linie_piesa, column=1, padx=5, pady=5, sticky=W)
    btn_inserare_piesa.bind("<Button>", create_piesa)
    linie_piesa += 1

    # Update
    global entry_piesa_u_id_piesa, entry_piesa_u_nume, entry_piesa_u_autor, entry_piesa_u_gen, entry_piesa_u_durata
    global entry_piesa_u_varsta_minima, entry_piesa_u_id_decor
    global btn_update_piesa


    linie_piesa = 1
    paint_label("Update", linie_piesa, 2)
    linie_piesa += 1

    paint_label("Id piesa", linie_piesa, 2)
    entry_piesa_u_id_piesa = Entry(root)
    entry_piesa_u_id_piesa.grid(row=linie_piesa, column=3, padx=5, pady=5)
    linie_piesa += 1

    paint_label("Nume", linie_piesa, 2)
    entry_piesa_u_nume = Entry(root)
    entry_piesa_u_nume.grid(row=linie_piesa, column=3, padx=5, pady=5)
    linie_piesa += 1

    paint_label("Autor", linie_piesa, 2)
    entry_piesa_u_autor = Entry(root)
    entry_piesa_u_autor.grid(row=linie_piesa, column=3, padx=5, pady=5)
    linie_piesa += 1

    paint_label("Gen", linie_piesa, 2)
    entry_piesa_u_gen= Entry(root)
    entry_piesa_u_gen.grid(row=linie_piesa, column=3, padx=5, pady=5)
    linie_piesa += 1

    paint_label("Durata", linie_piesa, 2)
    entry_piesa_u_durata = Entry(root)
    entry_piesa_u_durata.grid(row=linie_piesa, column=3, padx=5, pady=5)
    linie_piesa += 1

    paint_label("Varsta Min", linie_piesa, 2)
    entry_piesa_u_varsta_minima = Entry(root)
    entry_piesa_u_varsta_minima.grid(row=linie_piesa, column=3, padx=5, pady=5)
    linie_piesa += 1

    paint_label("Id Decor", linie_piesa, 2)
    entry_piesa_u_id_decor = Entry(root)
    entry_piesa_u_id_decor.grid(row=linie_piesa, column=3, padx=5, pady=5)
    linie_piesa += 1

    def update_piesa(event):
        minim_un_element = 0
        curs = conn.cursor()
        string_querry = "UPDATE piesa SET "
        nume = entry_piesa_u_nume.get()
        if nume != "":
            string_querry += f" nume= '{nume}' "
            minim_un_element=1
        autor = entry_piesa_u_autor.get()
        if autor != "":
            if (minim_un_element == 1):
                string_querry += f" , autor= '{autor}' "
            else:
                string_querry += f" autor= '{autor}' "
                minim_un_element = 1
        gen = entry_piesa_u_gen.get()
        if gen != "":
            if (minim_un_element == 1):
                string_querry += f" , gen= '{gen}' "
            else:
                string_querry += f" gen= '{gen}' "
                minim_un_element = 1
        durata = entry_piesa_u_durata.get()
        if durata != "":
            if (minim_un_element == 1):
                string_querry += f" , durata= '{durata}' "
            else:
                string_querry += f" gen= '{durata}' "
                minim_un_element = 1
        varsta_minima = entry_piesa_u_varsta_minima.get()
        if varsta_minima!= "":
            if (minim_un_element == 1):
                string_querry += f" , varsta_minima= '{varsta_minima}' "
            else:
                string_querry += f" varsta_minima= '{varsta_minima}' "
                minim_un_element = 1
        id_decor = entry_piesa_u_id_decor .get()
        if id_decor  != "":
            if (minim_un_element == 1):
                string_querry += f" , id_decor = '{id_decor }' "
            else:
                string_querry += f" id_decor = '{id_decor }' "
        id_piesa = entry_piesa_u_id_piesa.get()
        string_querry += f" WHERE id_piesa = '{id_piesa}'"
        print(string_querry)
        try:
            print(string_querry)
            curs.execute(string_querry)
            curs.execute('commit')
            field.insert(END, "[SUCCES]: Datele piesei au fost actualizate cu succes!\n")
            list_delete_piesa.destroy()
            update_drop_down_delete()
        except Exception as e:
            print(e)
            field.insert(END, "[EROARE]: Informatii piesa incorecte: Verificati datele din nou\n")
        curs.close()

    btn_update_piesa= Button(root, text="Update piesa", bg="firebrick2")
    btn_update_piesa.grid(row=linie_piesa, column=3, padx=5, pady=5, sticky=W)
    btn_update_piesa.bind("<Button>", update_piesa)
    linie_piesa += 1

    # Read
    label_terminal = Label(root, text="Read", bg='lightsalmon', font='Corbel 20')
    label_terminal.grid(row=1, column=5, padx=5, pady=5)
    global btn_read_piesa

    def read_piesa(event):
        cur = conn.cursor()
        field.insert(END, "\n\t\t\tPiesa\n\n")
        cur.execute(
            "SELECT id_piesa, nume, autor, gen, durata, varsta_minima, id_decor FROM piesa")
        res = cur.fetchall()
        for row in res:
            field.insert(END, "ID: " + str(row[0]) + ", Nume: " + str(row[1])  + ", Autor: "+ str(row[2])+
                         ", Gen: "+ str(row[3]) + ", Durata: "+ str(row[4]) + ", Varsta_minima: "+ str(row[5])
                         + ", Id_Decor: " + str(row[6])+ "\n\n")
        cur.close()

    btn_read_piesa = Button(root, text="Read piesa", bg="firebrick2")
    btn_read_piesa.grid(row=1, column=6, padx=5, pady=5, sticky=W)
    btn_read_piesa.bind("<Button>", read_piesa)

    # Delete
    def update_drop_down_delete():
        global list_delete_piesa
        global dictionar_piesa

        list_piesa = list()
        dictionar_piesa = dict()
        curs = conn.cursor()
        curs.execute("SELECT id_piesa, nume, autor, gen, durata, varsta_minima, id_decor FROM piesa ")
        res = curs.fetchall()
        for row in res:
            string_piesa = " "+str(row[0]) + " " + str(row[1])
            dictionar_piesa[string_piesa] = int(row[0])
            list_piesa.append(string_piesa)

        global variable_delete_piesa
        variable_delete_piesa = StringVar(root)
        variable_delete_piesa.set(list_piesa[0])

        linie_piesa = 11
        paint_label("Delete Piesa with ID", linie_piesa, 5)
        list_delete_piesa = OptionMenu(root, variable_delete_piesa, *list_piesa)
        list_delete_piesa.grid(row=linie_piesa, column=6, padx=5, pady=5)

        linie_piesa += 1

    global btn_delete_piesa
    update_drop_down_delete()

    def delete_piesa(event):
        curs = conn.cursor()
        try:
            print(dictionar_piesa[variable_delete_piesa.get()])
            querry = "DELETE FROM piesa where id_piesa  = '%s'" % (dictionar_piesa[variable_delete_piesa.get()])
            curs.execute(querry)
            curs.execute('commit')
            field.insert(END, "[SUCCES]: Piesa a fost stearsa!\n")
        except Exception as e:
            print(e)
            field.insert(END, "[EROARE]: Id-ul nu este corect. Verificati sa existe\n")
        list_delete_piesa.destroy()
        update_drop_down_delete()

    btn_delete_piesa = Button(root, text="Delete piesa", bg="firebrick2")
    btn_delete_piesa.grid(row=line_delete_button, column=column_delete_button, padx=5, pady=5, sticky=W)
    btn_delete_piesa.bind("<Button>", delete_piesa)
    linie_piesa += 1

def paint_recuzita():
    global entry_recuzita_nume, entry_recuzita_material

    #Create
    linie_recuzita = 1
    paint_label("Create", linie_recuzita, 0)
    linie_recuzita += 1
    paint_label("Nume", linie_recuzita, 0)
    entry_recuzita_nume = Entry(root)
    entry_recuzita_nume.grid(row=linie_recuzita, column=1, padx=5, pady=5)
    linie_recuzita +=1
    paint_label("Material", linie_recuzita, 0)
    entry_recuzita_material = Entry(root)
    entry_recuzita_material.grid(row=linie_recuzita, column=1, padx=5, pady=5)
    linie_recuzita +=1

    def create_recuzita(event):
        curs = conn.cursor()
        string_querry = "INSERT INTO recuzita VALUES(NULL"
        nume = entry_recuzita_nume.get()
        if nume == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{nume}'"
        material = entry_recuzita_material.get()
        if material == "":
            string_querry += ", NULL"
        else:
            string_querry += f", '{material}'"
        string_querry += ")"
        try:
            print(string_querry)
            curs.execute(string_querry)
            curs.execute('commit')
            field.insert(END, "[SUCCES]: Recuzita a fost inserata!\n")
            list_delete_recuzita.destroy()
            update_drop_down_delete()
        except Exception as e:
            print(e)
            field.insert(END, "[EROARE]: Informatii recuzita incorecte: Verificati datele din nou\n")
        curs.close()

    btn_inserare_recuzita = Button(root, text="Inserare recuzita", bg="firebrick2")
    btn_inserare_recuzita.grid(row=linie_recuzita, column=1, padx=5, pady=5, sticky=W)
    btn_inserare_recuzita.bind("<Button>", create_recuzita)
    linie_recuzita += 1

    # Update
    global entry_recuzita_u_id_recuzita, entry_recuzita_u_nume, entry_recuzita_u_material
    global btn_update_recuzita


    linie_recuzita = 1
    paint_label("Update", linie_recuzita, 2)
    linie_recuzita += 1

    paint_label("Id recuzita", linie_recuzita, 2)
    entry_recuzita_u_id_recuzita = Entry(root)
    entry_recuzita_u_id_recuzita.grid(row=linie_recuzita, column=3, padx=5, pady=5)
    linie_recuzita += 1

    paint_label("Nume", linie_recuzita, 2)
    entry_recuzita_u_nume = Entry(root)
    entry_recuzita_u_nume.grid(row=linie_recuzita, column=3, padx=5, pady=5)
    linie_recuzita += 1

    paint_label("Material", linie_recuzita, 2)
    entry_recuzita_u_material = Entry(root)
    entry_recuzita_u_material.grid(row=linie_recuzita, column=3, padx=5, pady=5)
    linie_recuzita += 1

    def update_recuzita(event):
        minim_un_element = 0
        curs = conn.cursor()
        string_querry = "UPDATE recuzita SET "
        nume = entry_recuzita_u_nume.get()
        if nume != "":
            string_querry += f" nume= '{nume}' "
            minim_un_element=1
        material = entry_recuzita_u_material.get()
        if material != "":
            if (minim_un_element == 1):
                string_querry += f" , material= '{material}' "
            else:
                string_querry += f" material= '{material}' "
        id_recuzita = entry_recuzita_u_id_recuzita.get()
        string_querry += f" WHERE id_recuzita = '{id_recuzita}'"
        print(string_querry)
        try:
            print(string_querry)
            curs.execute(string_querry)
            curs.execute('commit')
            field.insert(END, "[SUCCES]: Datele recuzitei au fost actualizate cu succes!\n")
            list_delete_recuzita.destroy()
            update_drop_down_delete()
        except Exception as e:
            print(e)
            field.insert(END, "[EROARE]: Informatii recuzita incorecte: Verificati datele din nou\n")
        curs.close()

    btn_update_recuzita= Button(root, text="Update recuzita", bg="firebrick2")
    btn_update_recuzita.grid(row=linie_recuzita, column=3, padx=5, pady=5, sticky=W)
    btn_update_recuzita.bind("<Button>", update_recuzita)
    linie_recuzita += 1

    # Read
    label_terminal = Label(root, text="Read", bg='lightsalmon', font='Corbel 20')
    label_terminal.grid(row=1, column=5, padx=5, pady=5)
    global btn_read_recuzita

    def read_recuzita(event):
        curs = conn.cursor()
        field.insert(END, "\n\t\t\tRecuzita\n\n")
        curs.execute(
            "SELECT id_recuzita, nume, material FROM recuzita")
        res = curs.fetchall()
        for row in res:
            field.insert(END, "ID: " + str(row[0]) + ", Nume: " + str(row[1])  + ", Material: "+ str(row[2])+ "\n\n")
        curs.close()

    btn_read_recuzita = Button(root, text="Read recuzita", bg="firebrick2")
    btn_read_recuzita.grid(row=1, column=6, padx=5, pady=5, sticky=W)
    btn_read_recuzita.bind("<Button>", read_recuzita)

    # Delete
    def update_drop_down_delete():
        global list_delete_recuzita
        global dictionar_recuzita

        list_recuzita = list()
        dictionar_recuzita = dict()
        curs = conn.cursor()
        curs.execute("SELECT id_recuzita, nume, material FROM recuzita ")
        res = curs.fetchall()
        for row in res:
            string_decor = " "+str(row[0]) + " " + str(row[1])
            dictionar_recuzita[string_decor] = int(row[0])
            list_recuzita.append(string_decor)

        global variable_delete_recuzita
        variable_delete_recuzita = StringVar(root)
        variable_delete_recuzita.set(list_recuzita[0])

        linie_recuzita = 11
        paint_label("Delete Recuzita with ID", linie_recuzita, 5)
        list_delete_recuzita = OptionMenu(root, variable_delete_recuzita, *list_recuzita)
        list_delete_recuzita.grid(row=linie_recuzita, column=6, padx=5, pady=5)

        linie_recuzita += 1

    global btn_delete_recuzita
    update_drop_down_delete()

    def delete_recuzita(event):
        curs = conn.cursor()
        try:
            print(dictionar_recuzita[variable_delete_recuzita.get()])
            querry = "DELETE FROM recuzita where id_recuzita  = '%s'" % (dictionar_recuzita[variable_delete_recuzita.get()])
            curs.execute(querry)
            curs.execute('commit')
            field.insert(END, "[SUCCES]: Recuzita a fost stearsa!\n")
        except Exception as e:
            print(e)
            field.insert(END, "[EROARE]: Id-ul nu este corect. Verificati sa existe\n")
        list_delete_recuzita.destroy()
        update_drop_down_delete()

    btn_delete_recuzita = Button(root, text="Delete recuzita", bg="firebrick2")
    btn_delete_recuzita.grid(row=line_delete_button, column=column_delete_button, padx=5, pady=5, sticky=W)
    btn_delete_recuzita.bind("<Button>", delete_recuzita)
    linie_recuzita += 1

def paint_label(text, row, column):
    generic_label = Label(root, text=str(text), bg='lightsalmon', font='Corbel 20')
    generic_label.grid(row=row, column=column, padx=5, pady=5, sticky=W)



if __name__ == '__main__':
    cx_Oracle.init_oracle_client(lib_dir=r"C:\InstaClientOracle\instantclient_21_8")
    print("hello")
    conn = cx_Oracle.connect(user, pw, dsn)
    print(conn.version)
    init_app()
    conn.close()