package com.macv.sprint1.repository;

import com.macv.sprint1.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class PersonRepository <T extends Person> {

    private List<T> people=new ArrayList<>();

    protected void add(T person){
        people.add(person);
    }

    public List<T> getAll(){return people;}

    public boolean existsWithId(int id){
        return getById(id).isPresent();
    }

    public Optional<T> getById(int id){
        return people.stream().filter(s-> s.getUserId()==id).findFirst();
    }
}
