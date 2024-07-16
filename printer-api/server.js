const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const { exec } = require('child_process');

app.use(bodyParser.text());

app.post('/print', (req, res) => {
    const printData = req.body;
    // Lógica para enviar los datos a la impresora, por ejemplo, usando el comando 'print' de Windows
    exec(`echo "${printData}" > LPT1`, (error, stdout, stderr) => {
        if (error) {
            console.error(`Error: ${stderr}`);
            res.status(500).send(`Error: ${stderr}`);
        } else {
            console.log(`Printed: ${stdout}`);
            res.send('Printed successfully');
        }
    });
});

app.listen(3000, () => {
    console.log('Printer server listening on port 3000');
});
