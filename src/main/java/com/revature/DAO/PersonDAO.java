package com.revature.DAO;

import com.revature.model.Person;

import java.sql.*;

public class PersonDAO implements PersonDAOInterface {

    Connection con;
    public PersonDAO(Connection con){
        this.con = con;
    }



    public Person insertPerson(Person person) throws SQLException {

        PreparedStatement ps = con.prepareStatement("insert into person(first_name,last_name) values(?,?)");
        ps.setString(1,person.getFirst_name());
        ps.setString(2, person.getLast_name());
        ps.executeUpdate();

//        //Used for obtaining generated primary key, and returning with the json
//        ResultSet keys = ps.getGeneratedKeys();
//        person.setPerson_id_pk(keys.getInt(1));
        return person;
    }

    public Person getPersonById(int id) throws SQLException {
        PreparedStatement ps = con.prepareStatement("select * from person where person_id_pk = ?");
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        Person person = null;
        while(rs.next()){
            person = new Person(
                rs.getInt("person_id_pk"),
                rs.getString("first_name"),
                rs.getString("last_name")
            );
        }
        return person;

    }

    @Override
    public Person updatePerson(Person person) throws SQLException {
        PreparedStatement ps;

        if(person.getFirst_name() != null) {
            ps = con.prepareStatement("update person set first_name = ? where person_id_pk = ?");
            ps.setInt(2,person.getPerson_id_pk());
            ps.setString(1,person.getFirst_name());
            ps.executeUpdate();
        }
        if(person.getLast_name() != null) {
            ps = con.prepareStatement("update person set last_name = ? where person_id_pk = ?");
            ps.setInt(2,person.getPerson_id_pk());
            ps.setString(1,person.getLast_name());
            ps.executeUpdate();
        }

        return getPersonById(person.getPerson_id_pk());



    }

}
