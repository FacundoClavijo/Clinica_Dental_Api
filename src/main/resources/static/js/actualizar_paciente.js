let form = document.querySelector('#put_paciente');
form.addEventListener('submit', function(event) {
    event.preventDefault();
    const url = window.location.href;
    const id = url.substring(url.lastIndexOf('/') + 1);


    const data = {

       nombre : document.querySelector('#nombre').value,
       apellido : document.querySelector('#apellido').value,
       dni: document.querySelector('#dni').value,
       fechaAlta: document.querySelector('#fechaAlta').value,
       domicilio:{
                   calle: document.querySelector('#calle').value,
                   numero: document.querySelector('#numero').value,
                   localidad: document.querySelector('#localidad').value,
                   provincia: document.querySelector('#provincia').value
    }};

    const setting = {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    }

    fetch('/pacientes/actualizar-paciente/' + id, setting)
        .then(response => response.json())
        .then(dataResponse => {
            console.log(dataResponse.status)
        })
        .catch(error => console.log(error))
});