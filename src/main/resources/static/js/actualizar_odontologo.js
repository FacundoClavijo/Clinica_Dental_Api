let form = document.querySelector('#put_odontologo');
form.addEventListener('submit', function(event) {
    event.preventDefault();
    const url = window.location.href;
    const id = url.substring(url.lastIndexOf('/') + 1);


    const data = {
       nombre : document.querySelector('#nombre').value,
       apellido : document.querySelector('#apellido').value,
       nroMatricula: document.querySelector('#nroMatricula').value
    };

    const setting = {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    }

    fetch('/odontologos/actualizar-odontologo/' + id, setting)
        .then(response => response.json())
        .then(dataResponse => {
            console.log(dataResponse.status)
        })
        .catch(error => console.log(error))
});