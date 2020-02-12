
'use strict'

const _ = require('lodash')
const UserService = require('./user')


const tasks = [
    {
        id: 1,
        created_at: new Date(2020, 1, 11, 10, 0),
        title: "Preparar exámenes recuperación",
        body: "Preparar todos los exámenes de recuperación de estos días. Ver calendario",
        created_by: 1,
        realized_by: 1
    },
    {
        id: 2,
        created_at: new Date(2020, 1, 13, 8, 0),
        title: "Montaje playbacks",
        body: "Comenzar con el montaje de los playbacks. Cuidado porque este año el vídeo va sobre HDMI",
        created_by: 4,
        realized_by: 4
    },
    {
        id: 3,
        created_at: new Date(2020, 1, 14, 10, 30),
        title: "Explicación del informe",
        body: "Acudir a la reunión de Equipo Directivo para poder explicar el informe",
        created_by: 3,
        realized_by: 1
    },
    {
        id: 4,
        created_at: new Date(2020, 1, 15, 8, 0),
        title: "Buenos días",
        body: "Me toca dar los buenos días",
        created_by: 3,
        realized_by: 3
    },
    {
        id: 5,
        created_at: new Date(2020, 1, 28, 8, 0),
        title: "Ensayo de cantos",
        body: "Realizar un ensayo de cantos para preparar la Fiesta de Don Bosco",
        created_by: 3,
        realized_by: 5
    },
]


let service = {    
    findAll: () => {
        return tasks.map((t) => { return {
            id: t.id,
            created_at: t.created_at,
            title: t.title,
            body: t.body,
            created_by: UserService.findById(t.created_by),
            realized_by: UserService.findById(t.realized_by)
        }});
    },
    newTask : (t) => {
        let nuevoelemento = {
            id: tasks.length+1,
            created_at: new Date(),
            title: t.title,
            body: t.body,
            created_by: t.created_by,
            realized_by: t.realized_by
        }
        tasks.push(nuevoelemento);
        nuevoelemento.created_by = UserService.findById(nuevoelemento.created_by);
        nuevoelemento.realized_by = UserService.findById(nuevoelemento.realized_by);
        return nuevoelemento;
    }
}

module.exports = service