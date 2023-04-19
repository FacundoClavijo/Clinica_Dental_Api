let form = document.querySelector('#put_turno');
form.addEventListener('submit', function(event) {
    event.preventDefault();
    const url = window.location.href;
    const id = url.substring(url.lastIndexOf('/') + 1);


    const data = {
       paciente : document.querySelector('#paciente').value,
       odontologo : document.querySelector('#odontologo').value,
       fecha: document.querySelector('#fecha').value,
       hora: document.querySelector('#hora').value
    };

    const setting = {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    }

    fetch('/turnos/actualizar-turno/' + id, setting)
        .then(response => response.json())
        .then(dataResponse => {
            console.log(dataResponse.status)
        })
        .catch(error => console.log(error))
});