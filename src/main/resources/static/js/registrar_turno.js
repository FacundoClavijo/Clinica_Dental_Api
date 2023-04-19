let form = document.querySelector('#add_new_turno');

form.addEventListener('submit' , function(event){
    event.preventDefault();
    const data = {
        odontologo : document.querySelector('#odontologo').value,
        paciente : document.querySelector('#paciente').value,
        fecha : document.querySelector('#fecha').value,
        hora : document.querySelector('#hora').value
    };

    const setting = {
    method : 'POST',
    headers: {
        'Content-Type': 'application/json'},
    body : JSON.stringify(data)
    }

    fetch( '/turnos/registrar-turno', setting )
    .then(response => response.json())
    .then( dataResponse => {
                             console.log(response.status)
                             console.log(dataResponse.status)})
    .catch(error => console.log(error))
})