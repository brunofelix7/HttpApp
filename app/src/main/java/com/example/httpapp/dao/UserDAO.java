package com.example.httpapp.dao;

import com.example.httpapp.model.User;

public class UserDAO {

    public void salvar(User user){
        user.save();
    }

    public void atualizar(User user, Long id){
        user = user.findById(User.class, id);
        user.save();
    }

    public void deletar(User user, Long id){
        user = user.findById(User.class, id);
        user.delete();
    }

    public void findOne(){

    }

    public void findAll(){

    }


}
