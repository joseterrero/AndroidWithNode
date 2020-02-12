'use strict'

const express = require('express')
const router = express.Router()
const middleware = require('../middleware/index');
const TaskController = require('../controllers/tasks')


router.get('/', middleware.ensureAuthenticated, TaskController.getTodas);

router.post('/', middleware.ensureAuthenticated, TaskController.nuevaTarea);



module.exports = router