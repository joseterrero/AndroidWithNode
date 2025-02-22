'use strict'

const UserService = require('../services/user')
const bcrypt    = require('bcryptjs');
const passport  = require('passport');
const jwt       = require('jsonwebtoken');
const error_types = require('./error_types');

let controller = {
    
    register: (req, res, next) => {
        let resultado = UserService.findUser({username : req.username});
        if (resultado != undefined) {
            next(new error_types.InfoError("user already exists"));
        } else if (req.body.password != req.body.password2) {
            next(new error_types.SamePassError("Las contraseñas no coinciden"));
        }else if (req.body.password.length <= 6) {
            next(new error_types.PassLengthError("La contraseña es muy corta"));
        } else {
            let hash = bcrypt.hashSync(req.body.password, parseInt(process.env.BCRYPT_ROUNDS));
            let inserted = UserService.insertUser({
                email: req.body.email,
                username: req.body.username,
                password: hash,
                password2: hash,
                avatar: req.file.filename
            })
            res.status(201).json(inserted)
        }
    }
    ,
    login: (req, res, next) => {
        passport.authenticate("local", {session: false}, (error, user) => {
            if (error || !user) {
                next(new error_types.Error404("username or password not correct."))
            } else {
                const payload = {
                    sub: user.id,
                    exp: Date.now() + parseInt(process.env.JWT_LIFETIME),
                    username: user.username
                };
                const token = jwt.sign(JSON.stringify(payload), process.env.JWT_SECRET, {algorithm: process.env.JWT_ALGORITHM});
                res.json({ 
                    username: user.username,
                    token: token,
                    id: user.id
                });

            }

        })(req, res)
    }


}

module.exports = controller;