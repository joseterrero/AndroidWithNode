
'use strict'

const _ = require('lodash')
const bcrypt = require('bcryptjs')

const users = [
    {
        id: 1,
        username: "luismi",
        email: "luismi.lopez@salesianos.edu",
        fullname: "Luis Miguel López Magaña",
        roles: [ "USER" ],
        password: bcrypt.hashSync("12345", parseInt(process.env.BCRYPT_ROUNDS))
    },
    {
        id: 2,
        username: "miguel",
        email: "miguel.campos@salesianos.edu",
        fullname: "Miguel Campos Rivera",
        roles: [ "USER" ],
        password: bcrypt.hashSync("67890", parseInt(process.env.BCRYPT_ROUNDS))   
    },
    {
        id: 3,
        username: "angel",
        email: "angel.naranjo@salesianos.edu",
        roles: ["USER", "ADMIN"],
        password: bcrypt.hashSync("lasnavaspuntocom", parseInt(process.env.BCRYPT_ROUNDS))   
    },
    {
        id: 4,
        username: "rafa",
        email: "rafael.villar@salesianos.edu",
        fullname: "Rafael Villar Liñán",
        roles: [ "USER" ],
        password: bcrypt.hashSync("pataitaporbuleria", parseInt(process.env.BCRYPT_ROUNDS))   
    },
    {
        id: 5,
        username: "jesus",
        email: "jesus.casanova@salesianos.edu",
        fullname: "Jesús Casanova Domínguez",
        roles: ["USER"],
        password: bcrypt.hashSync("vivalavirgendelvalle", parseInt(process.env.BCRYPT_ROUNDS))   
    }


]


let service = {
    findUser: (user) => {
        return _.find(users, u => (u.username == user.username) || (u.email == user.email));
    },
    findById: (id) => {
        let result =  _.find(users, u => u.id == id);
        //delete result.password;
        return {
            id: result.id,
            username: result.username,
            email: result.email,
            fullname: result.fullname,
            roles: result.roles
        };
    },
    findByRol: (rol) => {
        let list =  _.filter(users, u => _.includes(u.roles, rol));
        return list.map((result) => { return {
            id: result.id,
            username: result.username,
            email: result.email,
            fullname: result.fullname,
            roles: result.roles,
            avatar: result.avatar
        }});
    },
    insertUser : (user) => {
        users.push({
            id: users.length+1,
            email: user.email,
            username: user.username,
            password: user.password,
            roles: ["USER"],
            avatar: user.avatar
        });
        let result =  users[users.length-1];
        return {
            id: result.id,
            username: result.username,
            email: result.email,
            fullname: result.fullname,
            roles: result.roles,
            avatar: result.avatar
        }
    }
}

module.exports = service