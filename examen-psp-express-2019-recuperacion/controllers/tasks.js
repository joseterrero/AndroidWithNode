'use strict'

const TaskService = require('../services/tasks')
const error_types = require('./error_types');


module.exports = {

    nuevaTarea: (req, res) => {
        return res.status(201).json(
            TaskService.newTask({
                title: req.body.title,
                body: req.body.body,
                created_by: req.user.id,
                realized_by: req.body.realized_by
            })
        );
    },
    getTodas: (req, res) => {
        return res.status(200).json(TaskService.findAll());
    }


}