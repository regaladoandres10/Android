//Permite crear un servidor web facilmente
const express = require("express");
//Hace que Node.js se conecte a Firebase y envie notificaciones
const admin = require("firebase-admin");
//Permite que una página web pueda comunicarse con tu servidor.
const cors = require("cors");

//Cargamos credenciales de Firebase

//Utiliza el siguiente archivo para verificar que el servidor tiene permiso para enviar mensajes
const serviceAccount = require("./serviceAccountKey.json");

//Conecta Node.js con Firebase

//Inicializar Firebase
admin.initializeApp({
    //Utilizamos las credenciales del archivo [serviceAccountKey.json] para enviar notificaciones
    credential:
    admin.credential.cert(serviceAccount)
});

//Crear el servidor
const app = express();

//Nos permite hacer peticiones HTTP
app.use(cors());
//Nos permite recibir JSON

/*
  {
    "title":"Hola",
    "body":"Mensaje"
    }
*/
app.use(express.json());

//Me deja acceder a LocalHosthttp://localhost:3000/
app.use(express.static("public"));

//Iniciamos el servidor
//Puerto 3000
app.listen(3000, () => {
    console.log("Servidor iniciado");
});

//Creamos Endpoint para enviar Push
//Creamos la ruta: POST http://localhost:3000/send
app.post("/send", async (req, res) => {
    try {
        //Leer los datos del JSON

        /*
            {
                "token":"abc123",
                "title":"Hola",
                "body":"Prueba"
            }

        */

        const {
            token,
            title,
            body
        } = req.body;

        //Creamos mensaje Firebase
        const message = {
            notification: {
                title,
                body
            },
            token
        };

        //Enviamos el mensaje
        const response =
            await admin.messaging()
                .send(message);

        //En caso de que todo salga bien (Responde Exito)
        res.json({
            success: true,
            response
        });

        //Manejo de errores
    } catch (error) {
        res.status(500).json({
            success: false,
            error: error.message
        });

    }
});

/*
    Recibe:

    {
        "token":"TOKEN_ANDROID",
        "title":"Hola",
        "body":"Mensaje de prueba"
    }
*/