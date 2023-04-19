
let form = document.querySelector('#add_new_paciente');
console.log("Estoy aquí")
form.addEventListener('submit' , function(event){
    event.preventDefault();
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
    console.log("Esta es mi data" + data)
    const setting = {
    method : 'POST',
    headers: {
        'Content-Type': 'application/json'},
    body : JSON.stringify(data)
    }

    fetch( '/pacientes/registrar-paciente', setting )
    .then(response => response.json())
    .then( dataResponse => {
                             console.log(response.status)
                             console.log(dataResponse.status)})
    .catch(error => console.log(error))
})

